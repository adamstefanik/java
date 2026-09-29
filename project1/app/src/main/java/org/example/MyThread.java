package org.example;

public class MyThread extends Thread {

    public MyThread(String name) {
        super(name); // Posunie meno priamo do triedy Thread
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; ++i) {
            try {
                Thread.sleep(100); // 100 milisekúnd (namiesto 10 sekúnd)
            } catch (InterruptedException e) {
                System.err.println(getName() + " bolo prerušené");
                return;
            }
            System.out.println(getName() + " > " + i);
        }
    }
}