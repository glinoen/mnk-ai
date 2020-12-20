/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructures;

/**
 *
 * @author julinden
 */
public class Pair<K, V> {
    private final K key;
    private V value;
    private Pair<K, V> next;
    
    public Pair(K key, V value, Pair<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Pair<K, V> getNext() {
        return next;
    }

    public void setNext(Pair<K, V> next) {
        this.next = next;
    }
    
    
}
