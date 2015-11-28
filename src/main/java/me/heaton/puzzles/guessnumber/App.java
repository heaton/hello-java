package me.heaton.puzzles.guessnumber;

import java.util.Random;

public class App {

  public static void main(String[] args) {
    final Random random = new Random();
    final NumberGenerator numberGenerator = new NumberGenerator(random);
    final Validator validator = new Validator();
    final GuessNumber game = new GuessNumber(numberGenerator, validator);
    game.start(System.in, System.out);
  }

}
