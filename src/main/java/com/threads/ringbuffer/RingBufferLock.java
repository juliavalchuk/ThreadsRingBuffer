package com.threads.ringbuffer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Yuliia_Valchuk
 */
public class RingBufferLock<T> implements RingBuffer<T>{
    private final int SIZE;
    private volatile int head;
    private volatile int tail;
    private int currsize;
    private T[] elements;
    Lock lock;
    Condition notFull;
    Condition notEmpty;

    public RingBufferLock(int size) {
        SIZE = size;
        elements = (T[]) new Object[size];
        currsize = 0;

        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
        // head = -1;
    }

    public boolean push(T elem)
    {
        lock.lock();
        try {
            while (isFull()) {
                notFull.await();
            }
            if(!isFull())
            {
                currsize++;
                elements[tail] = elem;
                tail = increment(tail);
                notEmpty.signal();
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }    finally {
            lock.unlock();
        }
        return false;
    }

    public T pop()
    {
        lock.lock();
        try{
            while (isEmpty()){
                notEmpty.await();
            }
            if(!isEmpty()) {
                T elem;
                currsize--;
                elem = elements[head];
                head = increment(head);

                notFull.signal();
                return elem;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmpty()
    {
        //return tail - 1 - head % SIZE == 0;
        return currsize == 0;
    }

    public boolean isFull()
    {
        // return tail == head;
        return currsize == SIZE;
    }

    private int increment(int a)
    {
        return (a + 1) % SIZE;
    }

}
