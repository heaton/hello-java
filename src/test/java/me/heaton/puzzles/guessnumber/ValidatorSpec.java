package me.heaton.puzzles.guessnumber;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidatorSpec {

  private Validator validator;

  @Before
  public void given_a_number_validator() {
    validator = new Validator();
  }

  @Test
  public void when_input_is_1234_then_it_should_return_true() {
    assertThat(validator.isWrong("1234"), is(false));
  }

  @Test
  public void when_input_is_1134_then_it_should_return_false() {
    assertThat(validator.isWrong("1134"), is(true));
  }

  @Test
  public void when_input_is_12_then_it_should_return_false() {
    assertThat(validator.isWrong("12"), is(true));
  }

}

