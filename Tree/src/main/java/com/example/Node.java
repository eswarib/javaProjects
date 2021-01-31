package com.example;

public class Node {
    public int value;
    public Node rightNode;
    public Node leftNode;
    public int height;

    public Node(int val){ this.value = val;
        setHeight(1);}

    //utility function to return the value
    public int getValue(){ return this.value; }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

