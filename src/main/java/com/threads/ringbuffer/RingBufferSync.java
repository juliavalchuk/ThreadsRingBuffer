package com.threads.ringbuffer;


/**
 * Created by Yuliia_Valchuk
 */
public class RingBufferSync<T> implements RingBuffer<T> {
    private final int SIZE;
    private  int head;
    private  int tail;
    private  int currsize;
    private T[] elements;

    public RingBufferSync(int size) {
        SIZE = size;
        elements = (T[]) new Object[size];
        currsize = 0;
        // head = -1;
    }

    public synchronized boolean push(T elem)
    {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!isFull())
        {
            elements[tail] = elem;
            tail = increment(tail);
            currsize++;
            notifyAll();
            return true;
        }
        return false;
    }

    public synchronized T pop()
    {
        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!isEmpty())
        {
            T elem;
            elem = elements[head];
            head = increment(head);
            currsize--;
            notifyAll();
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
