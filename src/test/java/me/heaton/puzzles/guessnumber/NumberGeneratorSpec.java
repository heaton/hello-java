package me.heaton.puzzles.guessnumber;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NumberGeneratorSpec {

  @InjectMocks
  private NumberGenerator gameGenerator;

  @Mock
  private Random random;

  @Test
  public void generator_should_create_a_number_with_one_special_numbers_which_from_a_given_special_random() {
    when(random.nextInt(10)).thenReturn(1);
    assertThat(gameGenerator.unique(1).check("1"), is("1A0B"));

    verify(random, times(1)).nextInt(10);
  }

  @Test
  public void generator_should_create_a_unique_number_with_four_special_numbers_which_from_a_given_special_random() {
    when(random.nextInt(10)).thenReturn(1, 1, 2, 2, 3, 4);
    assertThat(gameGenerator.unique(4).check("1234"), is("4A0B"));

    verify(random, times(6)).nextInt(10);
  }

}
