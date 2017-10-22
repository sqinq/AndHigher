package com.shirleyqin.andhigher.Model;

import java.util.Iterator;

/**
 * Created by shirleyqin on 2017-10-21.
 */

public class Node<T> {
    private T value;
    private Node next;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public boolean hasNext() {
        return next!=null;
    }
}
