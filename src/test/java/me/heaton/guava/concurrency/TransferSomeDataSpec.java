package me.heaton.guava.concurrency;

import com.google.common.util.concurrent.FutureCallback;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class TransferSomeDataSpec {

  private final static int NO_WAIT = 0;
  private PrintStream logMock;
  private Gson gson;

  @Before
  public void given_a_json_parser_and_a_log_mock() {
    logMock = mock(PrintStream.class);
    doNothing().when(logMock).println(anyString());
    gson = new Gson();
  }

  @Test
  public void should_get_a_result_when_the_json_string_is_well_formatted() throws Exception {
    String data = "{id: 1, message: \"hi\"}";
    final Result result = runCall(data);
    Result expected = new Result(1, "hi");
    assertThat(result, is(expected));
  }

  @Test(expected = JsonSyntaxException.class)
  public void should_throw_an_exception_when_the_json_string_is_not_well_formatted() throws Exception {
    String data = "any wrong json";
    runCall(data);
    fail();
  }

  private Result runCall(String data) throws Exception {
    TransferSomeData instance = new TransferSomeData(data, null, gson, NO_WAIT, logMock);
    return instance.call();
  }

  @Test
  public void should_callback_to_the_given_address() throws Exception {
    final FutureCallback<Result> callback = getCallback("some address");
    callback.onSuccess(new Result(1, "hi"));
    verify(logMock, times(1)).println("send back to some address with the message of 1 hi");
  }

  @Test
  public void should_log_the_error_when_there_is_an_exception() {
    final FutureCallback<Result> callback = getCallback("some address");
    final Exception exception = mock(Exception.class);
    when(exception.getMessage()).thenReturn("this is wrong");
    callback.onFailure(exception);
    verify(logMock, times(1)).println("log the error that this is wrong");
    verify(exception, times(1)).printStackTrace();
  }

  private FutureCallback<Result> getCallback(String callback) {
    TransferSomeData instance = new TransferSomeData(null, callback, null, NO_WAIT, logMock);
    return instance.callback();

  }

}