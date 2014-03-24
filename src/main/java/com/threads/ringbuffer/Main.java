package com.threads.ringbuffer;

/**
 * Created by Yuliia_Valchuk
 */
public class Main {
    public static void main(String... args){
        int n = 10;
        RingBuffer<Integer> syncRingBuffer = new RingBufferSync<Integer>(n);
        RingBuffer<Integer> lockRingBuffer = new RingBufferLock<Integer>(n);
        Thread prods = (new Thread(new Producer(syncRingBuffer)));
        Thread conss = (new Thread(new Consumer(syncRingBuffer)));
        prods.start();
        conss.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            prods.join();
            conss.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread prodl = (new Thread(new Producer(lockRingBuffer)));
        Thread consl = (new Thread(new Consumer(lockRingBuffer)));
        prodl.start();
        consl.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            prodl.join();
            consl.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
