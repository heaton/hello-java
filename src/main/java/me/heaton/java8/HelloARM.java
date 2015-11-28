package me.heaton.java8;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * automatic resource management
 * http://www.oracle.com/technetwork/articles/java/trywithresources-401775.html
 */
public class HelloARM {

  private void writingWithARM() throws IOException {
    try (
        // can be multiple lines
        DataOutputStream out = new DataOutputStream(new FileOutputStream("data"))
    ) {
      out.writeInt(666);
      out.writeUTF("Hello");
    }
  }

  public String tryAutoClose() {
    try (AutoClose autoClose = new AutoClose()) {
      autoClose.work();
    } catch (MyException e) {
      return e.getMessage();
    }
    return "";
  }

  final class AutoClose implements AutoCloseable {

    @Override
    public void close() {
      System.out.println(">>> close()");
      throw new RuntimeException("Exception in close()");
    }

    public void work() throws MyException {
      System.out.println(">>> work()");
      throw new MyException("Exception in work()");
    }

  }

  class MyException extends Exception {

    public MyException(String message) {
      super(message);
    }

  }

}
