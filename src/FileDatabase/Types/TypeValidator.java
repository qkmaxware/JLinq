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
public interface TypeValidator{
    public boolean verifyString(String str);
    public boolean verifyObject(Object obj);
    public Object convertString(String str);
    public Object convertObject(Object obj);
    
    public boolean hasDefaultValue();
    public Object getDefaultValue();
    
    public int compare(Object a, Object b);
    
}
