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
public class Stack {
    int[][] stack;
    int top;
    
    public Stack(int m, int n) {
        this.stack = new int[m*n][2];
        top = -1;
    }
    
    public int getTop() {
        return this.top;
    }
    
    public int[] get(int i) {
        return this.stack[i];
    }
    
    public boolean empty() {
        return top < 0;
    }
    
    public void push(int[] coordinate) {
        this.top++;
        this.stack[top] = coordinate;
    }
    
    public int[] pop() {
        int[] coordinate = this.stack[this.top];
        this.top--;
        return coordinate;
    }
}
