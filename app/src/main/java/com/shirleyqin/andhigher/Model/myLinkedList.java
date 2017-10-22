package com.shirleyqin.andhigher.Model;

import java.util.Iterator;
import java.util.Queue;

/**
 * Created by shirleyqin on 2017-10-21.
 */

public class myLinkedList<T> {
    Node<T> first;
    Node<T> last;
    int length;

    public myLinkedList() {
        length = 0;
    }

    public void add(T value) {
        if (first == null) {
            first = new Node<>(value);
            last = first;
            length = 1;
        } else {
            Node newNode = new Node<>(value);
            last.setNext(newNode);
            last = newNode;
            ++ length;
        }
    }

    public void removeFirst() {
        Node temp = first;
        first = temp.getNext();
        -- length;
    }

    public Node getFirst() {
        return first;
    }

    public int size() {
        return length;
    }
}
