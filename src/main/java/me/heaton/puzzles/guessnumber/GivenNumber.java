package me.heaton.puzzles.guessnumber;

import static me.heaton.puzzles.guessnumber.GuessNumber.*;

public class GivenNumber {

  private final String givenNumber;

  public GivenNumber(String givenNumber) {
    this.givenNumber = givenNumber;
  }

  public String check(String in) {
    int countOfA = countOfRightNumberAndRightPosition(in);
    int countOfB = countOfRightNumber(in) - countOfA;
    return countOfA + RIGHT_NUMBER_AND_POSITION
        + countOfB + RIGHT_NUMBER_WITH_WRONG_POSITION;
  }

  private int countOfRightNumber(String in) {
    return countOf((c, i) -> givenNumber.indexOf(c) > -1, in);
  }

  private int countOfRightNumberAndRightPosition(String in) {
    return countOf((c, i) -> c == givenNumber.charAt(i), in);
  }

  private int countOf(Condition condition, String in) {
    int count = 0;
    for (int i = 0; i < givenNumber.length(); i++) {
      count += condition.okWithChar(in.charAt(i), i) ? 1 : 0;
    }
    return count;
  }

}
