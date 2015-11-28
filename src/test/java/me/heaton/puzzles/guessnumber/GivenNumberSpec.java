package me.heaton.puzzles.guessnumber;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GivenNumberSpec {

  private GivenNumber instance;

  @Before
  public void given_the_special_number_1234() {
    instance = new GivenNumber("1234");
  }

  @Test
  public void when_input_is_5678_then_it_should_return_0A0B() {
    assertThat(instance.check("5678"), is("0A0B"));
  }

  @Test
  public void when_input_is_1678_then_it_should_return_1A0B() {
    assertThat(instance.check("1678"), is("1A0B"));
  }

  @Test
  public void when_input_is_5671_then_it_should_return_0A1B() {
    assertThat(instance.check("5671"), is("0A1B"));
  }

  @Test
  public void it_should_still_work_when_the_length_of_the_given_number_is_not_4() {
    instance = new GivenNumber("1");
    assertThat(instance.check("1"), is("1A0B"));
  }

}