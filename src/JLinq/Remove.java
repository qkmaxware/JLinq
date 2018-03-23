/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLinq;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author halse
 */
public class Remove<T> {
    
    private Collection<T> set;
    private Matcher<T> matcher;
    
    private Remove(Collection<T> set){
        this.set = set;
    }

    public static<K> Remove<K> From(Collection<K> set){
        return new Remove<K>(set);
    }
    
    public void Where(Matcher<T> condition){
        if(set == null)
            return;
        
        if(matcher == null)
            return;
        
        Iterator<T> it = set.iterator();
        while(it.hasNext()){
            T next = it.next();
            if(matcher.Matches(next))
                it.remove();
        }
    }
    
    public void All(){
        if(set != null)
            set.clear();
    }
}
