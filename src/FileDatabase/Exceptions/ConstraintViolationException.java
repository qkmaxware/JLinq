/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase.Exceptions;

import FileDatabase.Constraints.Constraint;

/**
 *
 * @author halse
 */
public class ConstraintViolationException extends RuntimeException{
    
    public ConstraintViolationException(Constraint c, Object o){
        super("Table constraint "+ String.valueOf(c) +" has been violated by "+String.valueOf(o));
    }
    
}
