/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase;

import FileDatabase.Constraints.Constraint;
import FileDatabase.Types.TypeValidator;

/**
 *
 * @author halse
 */
public class ColumnModel {
 
    private ColumnDefinition[] columns;
    
    public ColumnModel(ColumnDefinition ... definitions){
        this.columns = definitions;
    }
    
    public int size(){
        return columns.length;
    }
    
    public ColumnDefinition getColumnDefinition(int i){
        return columns[i];
    }
    
    public int countConstraints(int i){
        return columns[i].constraints();
    }
    
    public Constraint getConstraint(int i, int c){
        return columns[i].constraint(c);
    }
    
    public String getColumnName(int i){
        return columns[i].getName();
    }
    
    public TypeValidator getColumnTypeValidator(int i){
        return columns[i].getTypeValidator();
    }
}
