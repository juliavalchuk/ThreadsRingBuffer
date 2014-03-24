package com.threads.ringbuffer;

/**
 * Created by Yuliia_Valchuk
 */
public class Producer implements Runnable {
    RingBuffer<Integer> ringBuffer;
    int N = 100;

    public Producer(RingBuffer<Integer> buffer){
        ringBuffer = buffer;
    }
    @Override
    public void run() {
        for(int i = 0; i < N; ++i){
            ringBuffer.push(i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
