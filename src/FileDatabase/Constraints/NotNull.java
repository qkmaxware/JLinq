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
public class NotNull implements Constraint {
    @Override
    public boolean violatesConstriant(Table srctable, int columnid, Object value){
        return value == null;
    }
}