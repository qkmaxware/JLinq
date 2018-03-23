/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLinq;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author halse
 */
public class Select<T> {
    
    private Collection<T> set;
    private Matcher<T> matcher;
    private LinkedList<OrderClause> order = new LinkedList<OrderClause>();
    private Collection<T> result;
    
    private Select(Collection<T> set){
        this.set = set;
    }

    public static<K> Select<K> From(Collection<K> set){
        return new Select<K>(set);
    }
    
    public Select<T> Where(Matcher<T> condition){
        this.matcher = condition;
        return this;
    }
    
    public Select<T> Into(Collection<T> c){
        this.result = c;
        return this;
    }
    
    public Select<T> OrderByAscending(Extractor<T> toCompare){
        return OrderBy(OrderClause.Order.ASC, toCompare);
    }
    
    public Select<T> OrderByDescending(Extractor<T> toCompare){
        return OrderBy(OrderClause.Order.DESC, toCompare);
    }
    
    public Select<T> OrderBy(Extractor<T> toCompare){
        return OrderByAscending(toCompare);
    }
    
    public Select<T> OrderBy(OrderClause.Order order, Extractor<T> toCompare){
        this.order.add(new OrderClause<T>(order, toCompare));
        return this;
    }
    
    public Collection<T> All(){
        LinkedList<T> values = new LinkedList<T>();
        if(set == null)
            return values;
        
        Matcher<T> matcher = this.matcher;
        if(matcher == null)
            matcher = (x) -> {return true;}; 
        
        //Add matches to list
        for(T t : set){
            if(matcher.Matches(t)){
                values.add(t);
            }
        }
        
        //Sort
        if(order.size() > 0)
            Collections.sort(values, (x,y) -> {
                int cmp = 0;
                for(OrderClause<T> o : order){
                    cmp = o.extractor.GetComparable(x).compareTo(o.extractor.GetComparable(y));
                    if(cmp != 0){
                        return cmp * (o.order == OrderClause.Order.ASC ? 1 : -1);
                    }
                }
                return cmp;
            });
        
        if(result != null)
            result.addAll(values);
        
        return values;
    }
    
    public T First(){
        Collection<T> all = All();
        T value = all.size() > 0 ? all.iterator().next() : null;
        if(result != null && value != null)
            result.add(value);
        return value;
    }
}
