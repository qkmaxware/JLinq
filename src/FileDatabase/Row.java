/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase;

import FileDatabase.Types.TypeValidator;
import FileDatabase.Exceptions.TypeValidationException;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author halse
 */
public class Row {
    private Object[] values;

    private Row(){}
    
    public boolean MatchesModel(ColumnModel model){
        return values.length == model.size();
    }
    
    public Object getValue(int columnid){
        return values[columnid];
    }
    
    public Class getType(int columnid){
        return values[columnid].getClass();
    }
    
    public static Row FromData(Object ... values){
        Row r = new Row();
        r.values = values;
        return r;
    }
    
    public static Row FromModel(ColumnModel model, Map<String,Object> template) throws TypeValidationException{
        Row row = new Row();
        row.values = new Object[model.size()];
        for(int i = 0; i < model.size(); i++){
           String column = model.getColumnName(i);
           TypeValidator validTest = model.getColumnTypeValidator(i);

           //If template has a value, and the value is verified
           if(template.containsKey(column)){
               Object o = template.get(column);
               if(validTest.verifyObject(o)){
                   row.values[i] = validTest.convertObject(o);
                   continue;
               }
           }

            //Nothing in the template...Null this 
            if(validTest.hasDefaultValue()){
               row.values[i] = validTest.getDefaultValue();
            }else{
                row.values[i] = null;
            }
        }

        return row;
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        
        builder.append("[");
        for(int i = 0; i < values.length; i++){
            builder.append(StringUtils.unEscapeString(this.values[i].toString()));
        }
        builder.append("]");
        
        return Arrays.toString(this.values);
    }
}