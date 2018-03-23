/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDatabase.Types;


/**
 *
 * @author halse
 */
public class BoolType implements TypeValidator{

    @Override
    public boolean verifyString(String str) {
        try{
            Boolean b = Boolean.parseBoolean(str);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean verifyObject(Object obj) {
        if(obj instanceof Boolean)
            return true;
        return verifyString(obj.toString());
    }

    @Override
    public Object convertString(String str) {
        return Boolean.parseBoolean(str);
    }

    @Override
    public Object convertObject(Object obj) {
        if(obj instanceof Boolean)
            return obj;
        return convertString(obj.toString());
    }

    @Override
    public boolean hasDefaultValue() {
        return true;
    }

    @Override
    public Object getDefaultValue() {
        return Boolean.FALSE;
    }

    @Override
    public int compare(Object a, Object b) {
        Boolean ab = (Boolean)convertObject(a);
        Boolean bb = (Boolean)convertObject(b);
        return ab.compareTo(bb);
    }
    
}
