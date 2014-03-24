package com.threads.ringbuffer;

/**
 * Created by Yuliia_Valchuk
 */
public interface RingBuffer<T> {
    public boolean push(T elem);
    public T pop();
    public boolean isEmpty();
    public boolean isFull();
}
