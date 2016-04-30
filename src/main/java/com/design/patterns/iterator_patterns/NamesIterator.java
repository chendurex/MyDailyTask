package com.design.patterns.iterator_patterns;

import java.util.Arrays;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.iterator_patterns
 * @date 2016/4/5 9:20
 */
public class NamesIterator<T>{
    private int index;
    private Object[] element;
    private int size;
    public NamesIterator() {
        element = new Object[20];
    }
    public Object put(Object obj) {
        element[index] = obj;
        index++;
        size ++;
        return element[index];
    }

    public Object remove(Object obj) {
        Object origin = null;
        int removeIndex = -1;
        for (int i=0; i<size ;i++) {
            if (obj == element[i] || obj.equals(element[i])) {
                origin = element[i];
                element[i] = null;
                removeIndex = i;
                break;
                //size --;
            }
        }
        if (removeIndex != -1) {
            int numMoved = size - removeIndex - 1;
            if (numMoved > 0)
                System.arraycopy(element, removeIndex+1, element, removeIndex,
                        numMoved);
            element[--size] = null;
        }

        return origin;
    }

    public Iterator<T> iterator() {
        return new NamesIter<>();
    }

    private class NamesIter<T> implements Iterator<T>{
        private int cur;
        private int pre;
        private Object obj;
        @Override
        public T next() {
            if (hasNext()) {
                obj = NamesIterator.this.element[cur];
                pre = cur;
                cur++;
                return (T)obj;
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            return cur != size();
        }

        @Override
        public T remove() {
            cur --;
            return (T)NamesIterator.this.remove(NamesIterator.this.element[pre]);
        }
    }

    public int size() {
        return size;
    }

}
