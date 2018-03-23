/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase;

import FileDatabase.Constraints.Constraint;
import FileDatabase.Types.TypeValidator;
import java.util.LinkedList;

/**
 *
 * @author halse
 */
public class ColumnDefinition {
    
    private String name;
    private TypeValidator validator;
    private Constraint[] constraints = new Constraint[0];
    
    private ColumnDefinition(){}
    
    public ColumnDefinition(String name, TypeValidator validator, Constraint ... constraints){
        this.name = name;
        this.validator = validator;
        this.constraints = constraints;
    }
    
    public ColumnDefinition (String name, String validatorClass, String... constraints) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        this.name = name;
        this.validator = (TypeValidator)Class.forName(validatorClass.trim()).newInstance();
        
        LinkedList<Constraint> con = new LinkedList<Constraint>();
        for(String c : constraints){
            if(!c.isEmpty())
                con.add((Constraint)Class.forName(c.trim()).newInstance());
        }
        this.constraints = new Constraint[con.size()];
        this.constraints = con.toArray(this.constraints);
    }
    
    public int constraints(){
        return this.constraints.length;
    }
    
    public Constraint constraint(int i){
        return this.constraints[i];
    }
    
    public String getName(){
        return name;
    }
    
    public TypeValidator getTypeValidator(){
        return this.validator;
    }
    
    public Class[] getConstrantTypes(){
        Class[] classes = new Class[this.constraints.length];
        for(int i = 0; i < classes.length; i++){
            classes[i] = this.constraints[i].getClass();
        }
        return classes;
    }
    
}
