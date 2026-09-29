package org.example.app;

import org.example.MyThread;

public class App {
    public static void main (String[] args) throws InterruptedException {
      MyThread myThread1 = new MyThread ("Moje vlakno 1");
      MyThread myThread2 = new MyThread ("Moje vlakno 2");
      MyThread myThread3 = new MyThread ("Moje vlakno 3");

      myThread1.start();
      myThread2.start();
      myThread3.start();

      myThread1.join();
      myThread2.join();
      myThread3.join();

      System.out.println("KONEC");
    }
}