package me.heaton.guava;

import com.google.common.base.CharMatcher;

import static com.google.common.base.Preconditions.*;

public class Movie {

  enum State {
    PLAYABLE
  }

  private State state;
  private String id;

  public void play() {
    checkState(state == State.PLAYABLE, "Can't play movie; state is %s", state);
  }

  public void setState(State state) {
    this.state = checkNotNull(state);
  }

  public void setIt(String input) {
    this.id = CharMatcher.DIGIT.or(CharMatcher.is('-'))
        .retainFrom(input);
  }

}
