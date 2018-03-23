/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase.Constraints;

import FileDatabase.Table;

/**
 *
 * @author halse
 */
public class PrimaryKey implements Constraint{
    
    private static NotNull notnull = new NotNull();
    private static Unique unique = new Unique();

    @Override
    public boolean violatesConstriant(Table table, int rowid, Object value) {
        boolean violatesNull = notnull.violatesConstriant(table, rowid, value);
        if(violatesNull)
            return true;
        boolean violatesUnique = unique.violatesConstriant(table, rowid, value);
        return violatesUnique;
    }
 
}
