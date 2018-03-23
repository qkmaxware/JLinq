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
public interface Constraint {
    
    public boolean violatesConstriant(Table table, int rowid, Object value);
    
}
