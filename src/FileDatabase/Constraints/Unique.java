/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase.Constraints;

import FileDatabase.Table;
import JLinq.Loop;

/**
 *
 * @author halse
 */
public class Unique implements Constraint {
    
    private static class BooleanSwitch{
        private boolean value;
        public BooleanSwitch(boolean b){
            this.value = b;
        }
        public boolean getValue(){
            return value;
        }
        public void setValue(boolean b){
            this.value = b;
        }
    }
    
    @Override
    public boolean violatesConstriant(Table srctable, int columnid, Object value){
        BooleanSwitch isViolated = new BooleanSwitch(false);
        //Returning false means stop looking
        Loop.For(srctable).Until((row) -> {
            Object compareTo = row.getValue(columnid);
            if(value == null && compareTo == null){
                isViolated.setValue(true);
                return false;
            }else if(value != null && compareTo == null){
                return true;
            }else if(value != null && compareTo != null){
                //Doesnt work for Comparable<T>
                int compare = srctable.getModel().getColumnTypeValidator(columnid).compare(value, compareTo);
                boolean violation = compare == 0;
                isViolated.setValue(violation);
                return !violation;
            }
            return true;
        });
        return isViolated.getValue();
    }
}
