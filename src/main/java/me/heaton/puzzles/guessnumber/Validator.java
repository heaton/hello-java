package me.heaton.puzzles.guessnumber;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

import static me.heaton.puzzles.guessnumber.GuessNumber.LENGTH_OF_NUMBER;

@Component
public class Validator {

  public boolean isWrong(String input) {
    return wrongLength(input) || notUnique(input);
  }

  private boolean notUnique(String in) {
    return new HashSet<>(Arrays.asList(in.split(""))).size() < in.length();
  }

  private boolean wrongLength(String in) {
    return in.length() != LENGTH_OF_NUMBER;
  }

}
