/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase.Types;

import java.math.BigDecimal;

/**
 *
 * @author halse
 */
public class RealType implements TypeValidator{

    @Override
    public boolean verifyString(String str) {
        try{
            BigDecimal b = new BigDecimal(str.trim());
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean verifyObject(Object obj) {
        if(obj instanceof BigDecimal)
            return true;
        return verifyString(obj.toString());
    }

    @Override
    public Object convertString(String str) {
        return new BigDecimal(str.trim());
    }

    @Override
    public Object convertObject(Object obj) {
        if(obj instanceof BigDecimal)
            return obj;
        return convertString(obj.toString());
    }

    @Override
    public boolean hasDefaultValue() {
        return true;
    }

    @Override
    public Object getDefaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public int compare(Object a, Object b) {
        BigDecimal ab = (BigDecimal)convertObject(a);
        BigDecimal bb = (BigDecimal)convertObject(b);
        return ab.compareTo(bb);
    }
    
}
