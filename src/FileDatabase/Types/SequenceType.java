/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase.Types;

import java.math.BigInteger;

/**
 *
 * @author halse
 */
public class SequenceType extends IntegerType {

    private BigInteger next = BigInteger.ONE;
    
    public SequenceType(){}
    
    public SequenceType(int startAt){
        next = new BigInteger(""+startAt);
    }
    
    @Override
    public boolean hasDefaultValue() {
        return true;
    }

    @Override
    public Object getDefaultValue() {
        BigInteger id = next;
        next = next.add(BigInteger.ONE);
        return id;
    }

}
