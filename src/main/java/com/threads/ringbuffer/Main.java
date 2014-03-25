package com.threads.ringbuffer;

/**
 * Created by Yuliia_Valchuk
 */
public class Main {
    public static void main(String... args){
        int n = 10;
        RingBuffer<Integer> syncRingBuffer = new RingBufferSync<Integer>(n);
        RingBuffer<Integer> lockRingBuffer = new RingBufferLock<Integer>(n);

        tryBuffer(syncRingBuffer);
        tryBuffer(lockRingBuffer);
    }

    public static void tryBuffer(RingBuffer buffer){
        int n = 1, m = 15;
        Thread[] produsers = new Thread[n];
        Thread[] consumers = new Thread[m];

        for(int i = 0; i < n; ++i){
            produsers[i] = new Thread(new Producer(buffer));
            produsers[i].start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < m; ++i){
            consumers[i] = new Thread(new Consumer(buffer));
            consumers[i].start();
        }

        for(int i = 0; i < n; ++i){
            try {
                produsers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < m; ++i){
            try {
                consumers[i].join(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
