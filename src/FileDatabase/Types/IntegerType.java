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
public class IntegerType implements TypeValidator{

    @Override
    public boolean verifyString(String str) {
        try{
            BigInteger b = new BigInteger(str.trim());
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean verifyObject(Object obj) {
        if(obj instanceof BigInteger)
            return true;
        return verifyString(obj.toString());
    }

    @Override
    public Object convertString(String str) {
        return new BigInteger(str.trim());
    }

    @Override
    public Object convertObject(Object obj) {
        if(obj instanceof BigInteger)
            return obj;
        return convertString(obj.toString());
    }

    @Override
    public boolean hasDefaultValue() {
        return true;
    }

    @Override
    public Object getDefaultValue() {
        return BigInteger.ZERO;
    }

    @Override
    public int compare(Object a, Object b) {
        BigInteger ab = (BigInteger)convertObject(a);
        BigInteger bb = (BigInteger)convertObject(b);
        return ab.compareTo(bb);
    }
    
}
