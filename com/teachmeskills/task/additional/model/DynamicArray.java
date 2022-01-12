package com.teachmeskills.task.additional.model;

import java.util.Arrays;
import java.util.Objects;

public class DynamicArray<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] array;
    private int size;

    public DynamicArray() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity > 0) {
            array = new Object[DEFAULT_CAPACITY];
            size = 0;
        } else {
            throw new IllegalArgumentException("Initial capacity can't be less than 1. (" + initialCapacity + ')');
        }
    }

    public void add(E element) {
        if (isArrayFull()) {
            increaseCapacity();
        }
        array[size++] = element;
    }

    private boolean isArrayFull() {
        return size == array.length;
    }

    private void increaseCapacity() {
        if (size > 0) {
            int capacity = array.length + (array.length >> 1);
            array = Arrays.copyOf(array, capacity);
        }
    }

    public boolean drop(E element) {
        if (!contains(element)) {
            return false;
        }
        for (int index = 0; index < size; index++) {
            if (element.equals((E) array[index])) {
                remove(index);
                return true;
            }
        }
        return false;
    }

    public void remove(int index) {
        Objects.checkIndex(index, size);
        System.arraycopy(array, index + 1, array, index, --size - index);
    }

    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) array[index];
    }

    public boolean contains(E element) {
        for (Object item : array) {
            if (element.equals((E) item)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int counter = 0; counter < size - 1; counter++) {
            builder.append((E) array[counter]);
            builder.append(',');
        }
        if (size > 0) {
            builder.append((E) array[size - 1]);
        }
        builder.append(']');
        return builder.toString();
    }
}