/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLinq;

/**
 *
 * @author halse
 */
public class OrderClause<T> {
    public static enum Order {
        ASC, DESC
    }
    
    public Order order;
    public Extractor<T> extractor;
    
    public OrderClause(Order order, Extractor<T> extractor){
        this.order = order;
        this.extractor = extractor;
    }
}
