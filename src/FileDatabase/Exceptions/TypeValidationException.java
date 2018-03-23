/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase.Exceptions;

import FileDatabase.Types.TypeValidator;

/**
 *
 * @author halse
 */
public class TypeValidationException extends RuntimeException implements WrappedException{
    
    private TypeValidator tester;
    private Object value;
    private Exception inner;
    
    public TypeValidationException(TypeValidator validator, Object value, Exception inner){
        super(String.valueOf(value) + " failed to validate against "+String.valueOf(validator));
        this.tester = validator;
        this.value = value;
        this.inner = inner;
    }

    @Override
    public Exception inner() {
        return this.inner;
    }
    
}
