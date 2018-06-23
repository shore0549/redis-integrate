package com.git.hui.demo.base.bean.timeline;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by yihui in 09:32 18/6/11.
 */
@SuppressWarnings("unchecked")
public class ElementList<T> implements Iterable<T> {
    // 链表头
    private ElementList.Node head;
    private ElementList.Node end;

    @Override
    public Iterator<T> iterator() {
        return new EleItr<T>(head);
    }

    protected static class Node<E> {
        E item;
        ElementList.Node<E> next;
        ElementList.Node<E> prev;

        Node(ElementList.Node<E> prev, E element, ElementList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public E getItem() {
            return item;
        }

        public String toString() {
            return String.valueOf(item);
        }
    }

    private static class EleItr<T> implements ListIterator {
        private Node<T> next;

        public EleItr(Node<T> node) {
            next = node;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Object next() {
            Node<T> ans = next;
            next = next.next;
            return ans;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Object previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(Object o) {

        }

        @Override
        public void add(Object o) {

        }
    }

    public ElementList() {
        head = new Node(null, null, null);
        end = new Node(head, null, null);
        head.next = end;
    }

    public Node<T> add(T element) {
        Node<T> node = new Node(end, element, null);
        end.next = node;
        end = node;
        return end;
    }

    public void remove(Node<T> element) {
        if (element == end) {
            end.item = null;
            return;
        }

        head.next = element.next;
        element.next.prev = head;

        element.prev = null;
        element.next = null;
        element.item = null;
    }

    public void removeBefore(Node<T> element) {
        if (element.prev != head) {
            element.prev.next = null;
        }

        head.next = element;
        element.prev = head;
    }

    public void clear() {
        end.item = null;
        end.prev = head;
        head.next = end;
    }

    public boolean isEmpty() {
        return head.next == end && end.getItem() == null;
    }

    public Node<T> getLast() {
        return end;
    }

    public void removeLast() {
        if (end.prev == head) {
            end.item = null;
        } else {
            end.prev.next = null;
            end = end.prev;
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Object o : this) {
            builder.append(o).append("#");
        }
        return builder.toString();
    }

}
