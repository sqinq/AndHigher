package com.shirleyqin.andhigher.Model;

/**
 * Created by shirleyqin on 2017-10-21.
 */

public class QueueNode<T> {
    private T value;
    private QueueNode next;

    public QueueNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setNext(QueueNode next) {
        this.next = next;
    }

    public QueueNode getNext() {
        return next;
    }

    public boolean hasNext() {
        return next!=null;
    }
}
