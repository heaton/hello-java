package me.heaton.puzzles.guessnumber;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class GuessNumberSpec {

  private GuessNumber guessNumber;
  private Validator validator;

  @Before
  public void given_the_special_number_1234() {
    NumberGenerator numberGenerator = mock(NumberGenerator.class);
    when(numberGenerator.unique(4)).thenReturn(new GivenNumber("1234"));
    validator = mock(Validator.class);

    guessNumber = new GuessNumber(numberGenerator, validator);
  }

  @Test
  public void when_input_is_5678_then_it_should_return_0A0B() {
    assertThat(guessNumber.guess("5678"), is("0A0B"));
  }

  @Test
  public void when_input_is_1234_then_it_should_return_4A0B_and_you_won() {
    assertThat(guessNumber.guess("1234"), is("4A0B\nyou won!"));
  }

  @Test
  public void when_input_6_times_without_right_answer_then_it_should_tell_you_lost() {
    input("5678", 5);
    assertThat(guessNumber.guess("5678"), containsString("0A0B\nyou lost!"));
  }

  @Test
  public void when_input_is_wrong_then_it_should_tell_your_input_is_wrong() {
    when(validator.isWrong(any())).thenReturn(true);
    assertThat(guessNumber.guess("1134"), is("your input is wrong!"));
  }

  @Test
  public void it_should_be_over_when_you_won() {
    guessNumber.guess("1234");
    assertThat(guessNumber.isOn(), is(false));
  }

  @Test
  public void it_should_be_on_when_you_have_not_won_or_lost() {
    guessNumber.guess("2345");
    assertThat(guessNumber.isOn(), is(true));
  }

  @Test
  public void it_should_be_end_when_you_lost() {
    input("5678", 6);
    assertThat(guessNumber.isOn(), is(false));
  }

  private void input(String text, int times) {
    for (int i = 0; i < times; i++) {
      guessNumber.guess(text);
    }
  }

  @Test
  public void it_should_start_normally_and_over_with_expect() {
    final InputStream in = new ByteArrayInputStream("1234".getBytes());
    final PrintStream out = mock(PrintStream.class);
    guessNumber.start(in, out);

    verify(out).println("4A0B\nyou won!");
  }

}

