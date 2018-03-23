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
public class Loop<T> {
    
    private Collection<T> set;
    private Matcher<T> matcher;
    
    private Loop(Collection<T> set){
        this.set = set;
    }

    public static<K> Loop<K> For(Collection<K> set){
        return new Loop<K>(set);
    }
    
    public Loop<T> Where(Matcher<T> condition){
        this.matcher = condition;
        return this;
    }
    
    public void Each(Action<T> action){
        for(T t : this.set){
            if(matcher == null || matcher.Matches(t)){
                action.Act(t);
            }
        }
    }
    
    public void Until(Func<T, Boolean> action){
        for(T t : this.set){
            if(matcher == null || matcher.Matches(t)){
                if(!action.Act(t)){
                    break;
                }
            }
        }
    }
    
}
