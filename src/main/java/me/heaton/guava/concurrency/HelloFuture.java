package me.heaton.guava.concurrency;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.*;
import com.google.gson.Gson;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class HelloFuture {

  public static void main(String[] args) {
    ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(8));
    TaskManager taskManager = new TaskManager(service);
    Gson gson = new Gson();
    TransferSomeData task1 = new TransferSomeData("{id: 1, message: \"Hello\"}", "Heaton", gson, 1000, System.out);
    TransferSomeData task2 = new TransferSomeData("wrong json format", "Heaton", gson, 500, System.out);
    taskManager.run(task1, task1.callback());
    taskManager.run(task2, task1.callback());
  }

}

class TaskManager {
  private final ListeningExecutorService service;

  TaskManager(ListeningExecutorService service) {
    this.service = service;
  }

  public <T> void run(final Callable<T> task, final FutureCallback<T> callback) {
    ListenableFuture<T> future = service.submit(task);
    Futures.addCallback(future, callback);
  }

}

class TransferSomeData implements Callable<Result> {

  private final String data;
  private final String sendBack;
  private final Gson gson;
  private final int withSleep;
  private final PrintStream log;

  TransferSomeData(String data, String sendBack, Gson gson, int withSleep, PrintStream log) {
    this.data = data;
    this.sendBack = sendBack;
    this.gson = gson;
    this.withSleep = withSleep;
    this.log = log;
  }

  @Override
  public Result call() throws Exception {
    log.println("doing some transfer");
    Thread.sleep(withSleep);
    return gson.fromJson(data, Result.class);
  }

  public FutureCallback<Result> callback() {
    return new FutureCallback<Result>() {
      @Override
      public void onSuccess(Result result) {
        log.println(String.format("send back to %s with %s", sendBack, result));
      }

      @Override
      public void onFailure(Throwable t) {
        log.println(String.format("log the error that %s", t.getMessage()));
        t.printStackTrace();
      }
    };
  }

}

class Result {

  final int id;
  final String message;

  Result(int id, String message) {
    this.id = id;
    this.message = message;
  }

  @Override
  public String toString() {
    return "the message of " + id + " " + message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Result result = (Result) o;
    return id == result.id &&
        Objects.equal(message, result.message);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, message);
  }

}

