/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;

/**
 *
 * @author julinden
 */
public class Coordinate {
    public int x;
    public int y;
    
    public Coordinate(int x, int y) {
        this.x = y;
        this.y = x;
    }
    
    @Override
    public boolean equals(Object o) {
        Coordinate compare = (Coordinate) o;
        return (compare.x == this.x && compare.y == this.y);
    }
    
    @Override
    public int hashCode() {
        return this.x * 31 + this.y;
    }
}
