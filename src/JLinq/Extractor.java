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
public interface Extractor<T> {
    public Comparable GetComparable(T value);
}
