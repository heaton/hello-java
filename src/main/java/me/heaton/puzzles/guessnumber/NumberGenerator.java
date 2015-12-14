package me.heaton.puzzles.guessnumber;

import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class NumberGenerator {

  private final Random random;

  @Autowired
  public NumberGenerator(Random random) {
    this.random = random;
  }

  public GivenNumber unique(int count) {
    Set<String> set = new HashSet<>();
    while(set.size() < count) {
      set.add(String.valueOf(random.nextInt(10)));
    }
    return new GivenNumber(Joiner.on("").join(set));
  }

}
