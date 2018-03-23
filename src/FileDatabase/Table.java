/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase;

import FileDatabase.Constraints.Constraint;
import FileDatabase.Exceptions.ConstraintViolationException;
import FileDatabase.Exceptions.TableSerializationException;
import FileDatabase.Exceptions.TypeValidationException;
import FileDatabase.Types.TypeValidator;
import JLinq.Loop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author halse
 */
public class Table implements Collection<Row>{
    
    private ColumnModel model;
    private final LinkedList<Row> rows = new LinkedList<Row>();
    
    private File file;
    
    private boolean modified = false;
    
    /**
     * Create a table from a column model. Table is empty and doesn't have a reference file.
     * @param model 
     */
    public Table(ColumnModel model){
        this.model = model;
    }
    
    /**
     * Create a table from a reference file. If the reference file doesn't exist, table is blank
     * @param f
     * @throws TableSerializationException 
     */
    public Table(File f, boolean autoload) throws TableSerializationException{
        this.setFile(f);
        if(f.exists() && autoload)
            this.revert();
    }
    
    /**
     * Create a table from a reference file, with a column model. If the reference file exists, it is loaded and the column model may be overwritten to match the reference. If the file doesn't exist, this table is blank. 
     * @param model
     * @param f
     * @throws TableSerializationException 
     */
    public Table(ColumnModel model, File f, boolean autoload) throws TableSerializationException{
        this.setFile(f);
        if(f.exists() && autoload)
            this.revert();
    }
    
    public void setFile(File f){
        file = f;
    }
    
    public ColumnModel getModel(){
        return model;
    }
    
    public boolean isModified(){
        return modified;
    }
    
    public void markModified(boolean isModified){
        this.modified = isModified;
    }
    
    @Override
    public int size() {
        return rows.size();
    }

    @Override
    public boolean isEmpty() {
        return rows.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return rows.contains(o);
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    @Override
    public Object[] toArray() {
        return rows.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return rows.toArray(ts);
    }

    @Override
    public boolean add(Row e){
        //Test model match
        if(!e.MatchesModel(model))
            return false;
        
        //Test constraints
        Object[] proper = new Object[model.size()];
        for(int i = 0; i < this.model.size(); i++){
            for(int j = 0; j < this.model.countConstraints(i); j++){
                Constraint c = this.model.getConstraint(i, j);
                boolean violation = c.violatesConstriant(this, i, e.getValue(i));
                if(violation)
                   throw new ConstraintViolationException(c, e.getValue(i));
            }
            TypeValidator valid = this.model.getColumnTypeValidator(i);
            if(valid.verifyObject(e.getValue(i))){
                proper[i] = valid.convertObject(e.getValue(i));
            }else{
                throw new TypeValidationException(valid, e.getValue(i), new ClassCastException());
            }
        }
        
        
        //Add row
        boolean add = rows.add(Row.FromData(proper));
        
        //If added, return true
        if(add)
            modified = true;
        return add;
    }

    @Override
    public boolean remove(Object o) {
        boolean rem = rows.remove(o);
        if(rem)
            modified = true;
        return rem;
    }

    @Override
    public boolean containsAll(Collection<?> clctn) {
        return rows.containsAll(clctn);
    }

    @Override
    public boolean addAll(Collection<? extends Row> clctn) {
        boolean add = false;
        for(Row r : clctn){
            add |= this.add(r);
        }
        if(add)
            modified = true;
        return add;
    }

    @Override
    public boolean removeAll(Collection<?> clctn) {
        boolean rem = rows.removeAll(clctn);
        if(rem)
            modified = true;
        return rem;
    }

    @Override
    public boolean retainAll(Collection<?> clctn) {
        boolean rem = rows.retainAll(clctn);
        if(rem)
            modified = true;
        return rem;
    }

    @Override
    public void clear() {
        rows.clear();
    }
    
    public void revert() throws TableSerializationException {
        try{
            if(file == null){
                clear();
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder b = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                b.append(line);
            }
            String source = b.toString();

            //Extract header
            Pattern header = Pattern.compile("<header>(.*?)<\\/header>", Pattern.DOTALL);
            Matcher m = header.matcher(source);
            LinkedList<ColumnDefinition> defs = new LinkedList<ColumnDefinition>();
            if(m.find()){
                String contents = m.group(1);
                Pattern column = Pattern.compile("<column(.*?)\\/>", Pattern.DOTALL);
                Pattern name = Pattern.compile("(name=\"(?<name>(?:[^\"\\\\]|\\\\.)*)\")", Pattern.DOTALL);
                Pattern type = Pattern.compile("(type=\"(?<type>(?:[^\"\\\\]|\\\\.)*)\")", Pattern.DOTALL);
                Pattern constraint = Pattern.compile("(constraints=\"(?<constraints>(?:[^\"\\\\]|\\\\.)*)\")", Pattern.DOTALL);
                Matcher c = column.matcher(contents);

                while(c.find()){
                    String def = c.group(1);
                    String n = null;
                    String t = null;
                    String cs = "";

                    Matcher nm = name.matcher(def);
                    if(nm.find()){
                        n = nm.group("name");
                    }

                    Matcher nt = type.matcher(def);
                    if(nt.find()){
                        t = nt.group("type");
                    }

                    Matcher ncs = constraint.matcher(def);
                    if(ncs.find()){
                        cs = ncs.group("constraints");
                    }

                    if(n != null && t != null){
                        ColumnDefinition d = new ColumnDefinition(n, t, cs.split(","));
                        defs.add(d);
                    }
                }
            }else{
                throw new TableSerializationException("No header found in reference file", new IOException("File missing header"));
            }

            //Make model
            ColumnDefinition[] dargs = new ColumnDefinition[defs.size()];
            dargs = defs.toArray(dargs);
            ColumnModel model = new ColumnModel(dargs);

            this.model = model;

            //Extract rows
            Pattern rows = Pattern.compile("<rows>(.*?)<\\/rows>", Pattern.DOTALL);
            Matcher r = rows.matcher(source);
            this.rows.clear();
            if(r.find()){
                String contents = r.group(1);
                Pattern row = Pattern.compile("<row.*?(data=\\\"\\[(?<data>(?:[^\\\"\\\\\\\\]|\\\\\\\\.)*)\\]\\\").*?\\/>", Pattern.DOTALL);
                Matcher rm = row.matcher(contents);
                while(rm.find()){
                    String data = rm.group("data");
                    String[] values = data.split(",");
                    Row rv = Row.FromData(values);
                    this.add(rv);
                }
            }
        }catch(Exception e){
            throw new TableSerializationException("Table failed to be deserialized from reference file", e);
        }
    }
    
    public void commit() throws TableSerializationException{
        try{
            if(file == null)
                return;

            StringBuilder builder = new StringBuilder();

            builder.append("<header>\n");
            for(int i = 0; i < this.model.size(); i++){
                ColumnDefinition def = this.model.getColumnDefinition(i);
                builder.append("\t<column name=\"");
                builder.append(StringUtils.unEscapeString(def.getName()));
                builder.append("\" type=\"");
                builder.append(StringUtils.unEscapeString(def.getTypeValidator().getClass().getName()));
                builder.append("\" constraints=\"");
                for(int j = 0; j < def.constraints(); j++){
                    if(j != 0)
                        builder.append(",");
                    builder.append(def.constraint(j).getClass().getName());
                }
                builder.append("\"/>\n");
            }
            builder.append("</header>\n");

            builder.append("<rows>\n");
            Loop.For(this.rows).Each((x) -> {
                builder.append("\t<row data=\"");
                builder.append(x.toString());
                builder.append("\"/>\n");
            });
            builder.append("</rows>");

            String data = builder.toString();

            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.flush();
            writer.close();
        }catch(Exception e){
            throw new TableSerializationException("Table failed to be serialized to reference file", e);
        }
    }
    
}
