package tests;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by carolinemead on 11/29/17.
 */

public class LinkedList<T> implements Iterable<T> {

    private int size;
    private ListNode<T> head;
    private ListNode<T> tail;

    private class ListNode<T> {
        private T element;
        private ListNode<T> next;

        public ListNode(T e, ListNode<T> n) {
            element = e;
            next = n;
        }
    }

    public LinkedList() {
        size = 0;
        head = tail = null;
    }

    public void addToFront(T e) {
        ListNode<T> newNode = new ListNode<T>(e, head);
        head = newNode;
        size++;
        if (size == 1) {
            tail = head;
        }
    }

    public void addToRear(T e) {
        if (isEmpty()) {
            addToFront(e);
        } else {
            ListNode<T> newNode = new ListNode<T>(e, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size <= 0);
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list");
        }

        T ret = head.element;
        head = head.next;
        size--;
        if (size <= 1) {
            tail = head;
        }
        return ret;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list");
        }

        T ret = tail.element;
        if (size == 1) {
            ret = removeFirst();
        } else {
            ListNode<T> newLast = head;
            while (newLast.next != tail) {
                newLast = newLast.next;
            }
            tail = newLast;
            tail.next = null;
            size--;
        }
        return ret;
    }

    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list");
        }
        return head.element;
    }

    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list");
        }
        return tail.element;
    }

    public String toString() {
        String ret = "head -> ";
        ListNode<T> current = head;
        while (current != null) {
            ret += current.element + " -> ";
            current = current.next;
        }
        return ret += "tail";
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<T>(head);
    }

    private class ListIterator<T> implements Iterator<T> {

        private ListNode<T> current;

        public ListIterator(ListNode<T> start) {
            current = start;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Empty list");
            }
            T ret = current.element;
            current = current.next;
            return ret;
        }

    }
}

