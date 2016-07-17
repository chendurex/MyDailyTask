package com.algorithms;

import java.util.*;

/**
 * Created by Administrator on 2016/7/17.
 */
public class MyLinkedList<E> implements List<E>, Deque<E> {
    public static void main(String[] args) {
        MyLinkedList<Integer> mll = new MyLinkedList<>();
        mll.add(1);
        mll.add(2);
        mll.add(3);
        mll.add(4);
        for (int i = 0, len = mll.size(); i < len; i++) {
            System.out.println(mll.poll());
        }

    }


    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public void addFirst(E e) {
        final Node<E> curHead = head;
        if (curHead == null) {
            Node<E> node = new Node<>(null, null, e);
            head = tail = node;
        } else {
            Node<E> node = new Node<>(null, curHead, e);
            curHead.pre = node;
            head = node;
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        add(e);
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        return add(e);
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E pollFirst() {
        return null;
    }

    @Override
    public E pollLast() {
        return null;
    }

    @Override
    public E getFirst() {
        return null;
    }

    @Override
    public E getLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        final Node<E> curHead = head;
        if (curHead != null) {
            final Node<E> nextNode = curHead.next;
            if (nextNode != null) {
                nextNode.pre = null;
                curHead.next = null;
            }
            head = nextNode;
            size--;
            return curHead.e;
        }
        return null;
    }

    @Override
    public E poll() {
        E e = remove();
        if (e == null) {
            throw new NoSuchElementException();
        } else {
            return e;
        }
    }

    @Override
    public E element() {
        E e = peek();
        if (e == null) {
            throw new NoSuchElementException();
        } else {
            return e;
        }
    }

    @Override
    public E peek() {
        return head == null ? null : head.e;
    }

    @Override
    public void push(E e) {
        add(e);
    }

    @Override
    public E pop() {
        final Node<E> curTail = tail;
        if (curTail != null) {
            final Node<E> preTail = curTail.pre;
            if (preTail != null) {
                preTail.next = null;
                curTail.pre = null;
            }
            tail = preTail;
            size --;
            return curTail.e;
        } else {
            return null;
        }
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        final Node<E> curTail = tail;
        final Node<E> node = new Node<>(curTail, null, e);
        if (tail == null) {
            head = node;
        } else {
            curTail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    private static class Node<E> {
        Node<E> pre;
        Node<E> next;
        E e;

        public Node(Node<E> pre, Node<E> next, E e) {
            this.pre = pre;
            this.next = next;
            this.e = e;
        }
    }
}
