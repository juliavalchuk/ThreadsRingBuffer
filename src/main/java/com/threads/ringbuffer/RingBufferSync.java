package com.threads.ringbuffer;


/**
 * Created by Yuliia_Valchuk
 */
public class RingBufferSync<T> implements RingBuffer<T> {
    private final int SIZE;
    private volatile int head;
    private volatile int tail;
    private volatile int currsize;
    private T[] elements;

    public RingBufferSync(int size) {
        SIZE = size;
        elements = (T[]) new Object[size];
        currsize = 0;
        // head = -1;
    }

    public boolean push(T elem)
    {
        while (isFull()) {}
        if(!isFull())
        {
            elements[tail] = elem;
            tail = increment(tail);
            currsize++;
            return true;
        }
        return false;
    }

    public T pop()
    {
        while (isEmpty()) {}
        if(!isEmpty())
        {
            T elem;
            elem = elements[head];
            head = increment(head);
            currsize--;
            return elem;
        }
        return null;
    }

    public boolean isEmpty()
    {
        return currsize == 0;
    }

    public boolean isFull()
    {
        return currsize == SIZE;
    }

    private int increment(int a)
    {
        return (a + 1) % SIZE;
    }

}
