package com.threads.ringbuffer;

/**
 * Created by Yuliia_Valchuk
 */
public class Consumer implements Runnable {
    RingBuffer<Integer> ringBuffer;
    int N = 100;

    public Consumer(RingBuffer<Integer> buffer){
        ringBuffer = buffer;
    }

    @Override
    public void run() {
        for(Integer i = ringBuffer.pop(); i == null || i < N; i = ringBuffer.pop()){
            System.out.println(i + " ");
        }
    }
}
