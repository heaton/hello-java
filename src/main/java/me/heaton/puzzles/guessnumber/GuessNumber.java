package me.heaton.puzzles.guessnumber;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class GuessNumber {

  public final static int LENGTH_OF_NUMBER = 4;
  public final static String RIGHT_NUMBER_AND_POSITION = "A";
  public final static String RIGHT_NUMBER_WITH_WRONG_POSITION = "B";
  private final static String ALL_RIGHT = LENGTH_OF_NUMBER + RIGHT_NUMBER_AND_POSITION;

  private final GivenNumber givenNumber;
  private final Validator validator;

  private int inputCount = 0;
  private boolean over = false;

  public GuessNumber(NumberGenerator numberGenerator, Validator validator) {
    this.givenNumber = numberGenerator.unique(LENGTH_OF_NUMBER);
    this.validator = validator;
  }

  public String guess(String input) {
    if(validator.isWrong(input)) {
      return "your input is wrong!";
    }
    inputCount++;
    String result = givenNumber.check(input);
    return result + conclusion(isWin(result));
  }

  private boolean isWin(String result) {
    return result.contains(ALL_RIGHT);
  }

  private String conclusion(boolean isWin) {
    over = isWin || isEnd();
    return isWin ? "\nyou won!" : isEnd() ? "\nyou lost!" : "";
  }

  private boolean isEnd() {
    return inputCount == 6;
  }

  public boolean isOn() {
    return !over;
  }

  public void start(InputStream in, PrintStream out) {
    final Scanner scanner = new Scanner(in);
    while (isOn()) {
      out.println(guess(scanner.nextLine()));
    }
  }

}
