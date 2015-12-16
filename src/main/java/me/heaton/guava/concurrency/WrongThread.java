package me.heaton.guava.concurrency;

public class WrongThread {

  public static void main(String[] args) {
    final MyThread thread = new MyThread();
    thread.start();
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    thread.setStop();
  }

}

class MyThread extends Thread {

  private boolean stop = false;

  public void run() {
    final long startTime = System.currentTimeMillis();
    while(!stop) {
      System.out.println("I'm alive!");
    }
    System.out.println("spent " + (System.currentTimeMillis() - startTime));
  }

  // a field written by one thread is not guaranteed to be seen by a different thread
  public void setStop() {
    this.stop = true;
  }

}
