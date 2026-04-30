package com.pao.laboratory09.exercise3;

import java.util.LinkedList;
import java.util.Queue;

class Buffer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacitate = 5;

    public synchronized void adauga(int element) throws InterruptedException {
        while (queue.size() == capacitate) {
            wait();
        }
        queue.add(element);
        System.out.println("Producator a generat: " + element);
        notifyAll();
    }

    public synchronized int preia() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        int element = queue.poll();
        System.out.println("Consumator a procesat: " + element);
        notifyAll();
        return element;
    }
}

public class Main {
    private static volatile boolean ruleaza = true;

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();

        Thread producator = new Thread(() -> {
            int contor = 1;
            while (ruleaza && contor <= 10) {
                try {
                    buffer.adauga(contor++);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumator = new Thread(() -> {
            int consumate = 0;
            while (ruleaza && consumate < 10) {
                try {
                    buffer.preia();
                    consumate++;
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producator.start();
        consumator.start();

        producator.join();
        consumator.join();

        System.out.println("Procesare asincrona finalizata cu succes.");
    }
}