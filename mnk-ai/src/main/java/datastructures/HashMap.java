/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructures;

import mnkgame.domain.Coordinate;

/**
 *
 * @author julinden
 */
public class HashMap<K, V> {
    private Pair<K, V>[] table;
    private int storedValues;
    
    public HashMap(int size) {
        this.table = new Pair[size];
        this.storedValues = 0;
    }
    
    public void add(K key, V value) {
        Pair<K, V> newPair = new Pair<>(key, value, null);
        int index = getIndex(key);
        this.storedValues++;
        if (table[index] == null) {
            table[index] = newPair;
        } else {
            if (table[index].getKey().equals(key)) {
                storedValues--;
            } else {
                Pair<K, V> last = table[index];
                while(last.getNext() != null) {
                    last = last.getNext();
                }
                last.setNext(newPair);
            }
        }
    }
    
    public V get(K key) {
        V value = null;
        int index = getIndex(key);
        Pair<K, V> pair = table[index];
        while (pair != null) {
            if(pair.getKey().equals(key)) {
                value = pair.getValue();
                return value;
            }
            pair = pair.getNext();
        }
        return value;
    }
    
    public Coordinate[] getCoordinates() {
        Coordinate[] keys = new Coordinate[this.storedValues];
        int returnedValues = 0;
        for (int i = 0; i< this.table.length; i++) {
            Pair<K, V> pair = table[i];
            while(pair != null) {
                keys[returnedValues] = (Coordinate) pair.getKey();
                returnedValues++;
                pair = pair.getNext();
            }
        }
        return keys;
                
    }
    
    public int getIndex(K key) {
        int hash = key.hashCode() % this.table.length;
        int index = (hash > 0) ? hash : -hash;
        return index;
    }
}
