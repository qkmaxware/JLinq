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
public class VarcharType implements TypeValidator{

    @Override
    public boolean verifyString(String str) {
        return true;
    }

    @Override
    public boolean verifyObject(Object obj) {
        return true;
    }

    @Override
    public Object convertString(String str) {
        return str;
    }

    @Override
    public Object convertObject(Object obj) {
        return obj.toString();
    }

    @Override
    public boolean hasDefaultValue() {
        return false;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public int compare(Object a, Object b) {
        return a.toString().compareTo(b.toString());
    }
    
}
