package com.shirleyqin.andhigher.Model;

/**
 * Created by shirleyqin on 2017-10-21.
 */

public class myLinkedList<T> {
    QueueNode<T> first;
    QueueNode<T> last;
    int length;

    public myLinkedList() {
        length = 0;
    }

    public void add(T value) {
        if (first == null) {
            first = new QueueNode<>(value);
            last = first;
            length = 1;
        } else {
            QueueNode newQueueNode = new QueueNode<>(value);
            last.setNext(newQueueNode);
            last = newQueueNode;
            ++ length;
        }
    }

    public void removeFirst() {
        QueueNode temp = first;
        first = temp.getNext();
        -- length;
    }

    public QueueNode getFirst() {
        return first;
    }

    public int size() {
        return length;
    }
}
