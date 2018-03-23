/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase.Exceptions;

/**
 *
 * @author halse
 */
public class TableSerializationException extends Exception implements WrappedException{
 
    private Exception inner;
    
    public TableSerializationException(String message, Exception inner){
        super(message);
        this.inner = inner;
    }

    @Override
    public Exception inner() {
        return this.inner;
    }
    
}
