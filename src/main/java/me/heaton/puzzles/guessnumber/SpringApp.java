package me.heaton.puzzles.guessnumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApp implements CommandLineRunner{

  @Autowired
  private GuessNumber game;

  @Override
  public void run(String... args) throws Exception {
    game.start(System.in, System.out);
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringApp.class, args);
  }


}
