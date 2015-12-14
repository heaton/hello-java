package me.heaton.puzzles.guessnumber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@ComponentScan(basePackages = "me.heaton.puzzles.guessnumber")
public class SpringAppConfig {

  @Bean
  public Random random() {
    return new Random();
  }

}
