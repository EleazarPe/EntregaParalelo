package org.example;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

public class Main {
    private static final int QUEUE_CAPACITY = 10;
    private static final int PRODUCER_COUNT = 2;
    private static final int CONSUMER_COUNT = 2;
    private static final int PRODUCE_COUNT = 100;

    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> deque = new ArrayBlockingQueue<>(PRODUCE_COUNT);
        for(int i =0; i< PRODUCE_COUNT;i++){
            deque.add((int) (Math.random() * 1000));
        }
        for (Integer d: deque) {
            System.out.println(d);
        }

    }
    static class Producer implements Runnable {
        private final BlockingDeque<Integer> deque;
        private ArrayList<Consumer> consumidores;
        Producer(BlockingDeque<Integer> deque, ArrayList<Consumer> consumidores) {
            this.deque = deque;
            this.consumidores = consumidores;
        }
        public void run() {
            try {
                for(int i =0; i< PRODUCE_COUNT;i++){
                    produce(deque.take());
                }
            } catch (InterruptedException e) { }
        }
        void produce(Integer x) {
            for (Consumer c: consumidores) {
                c.consume(x);
            }
        }

    }
    static class Consumer implements Runnable {
        private ArrayList<Integer> enteros;
        Consumer() {
            this.enteros = new ArrayList<>();
        }
        public void run() {
            
        }
        private int suma(){
            int sum = 0;
            for (Integer i: enteros) {
                sum += i;
            }
            return sum;
        }
        void consume(Integer x) {
            enteros.add(x);
        }
    }
}