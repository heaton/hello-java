package me.heaton.guava.concurrency;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.common.util.concurrent.Service;
import org.joda.time.DateTime;

import static java.util.concurrent.TimeUnit.*;

public class HelloService {

  public static void main(String[] args) {

    final Service service = new AbstractScheduledService() {
      @Override
      protected void runOneIteration() throws Exception {
        System.out.println(DateTime.now().toString("hh:mm:ss"));
      }

      @Override
      protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(0, 5, SECONDS);
      }
    };

    service.startAsync();

    try {
      Thread.sleep(30 * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    service.stopAsync();
  }

}
