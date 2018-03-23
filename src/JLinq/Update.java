/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLinq;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author halse
 */
public class Update<T> {
    
    private Collection<T> set;
    private Matcher<T> matcher;
    
    private Update(Collection<T> set){
        this.set = set;
    }

    public static<K> Update<K> From(Collection<K> set){
        return new Update<K>(set);
    }
    
    public Update<T> Where(Matcher<T> condition){
        this.matcher = condition;
        return this;
    }
    
    public void With(Manipulator<T> manipulator){
        if(set == null)
            return;
        if(manipulator == null)
            return;
        
        LinkedList<T> values = new LinkedList<T>(this.set);
        this.set.clear();
        for(T t : values){
            if(matcher == null || matcher.Matches(t)){
                this.set.add(manipulator.Modify(t));
            }else{
                this.set.add(t);
            }
        }
    }
    
    public void All(){
        this.matcher = null;
    }
}
