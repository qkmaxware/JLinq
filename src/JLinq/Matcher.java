/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLinq;

/**
 *
 * @author halse
 * @param <T>
 */
public interface Matcher<T> {
    public boolean Matches(T value);
}
