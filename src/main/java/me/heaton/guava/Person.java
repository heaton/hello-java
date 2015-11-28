package me.heaton.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {

  final String name, nickname;
  final List<String> friends;
  final Map<String, String> scores;

  public Person(String name, String nickname) {
    this.name = name;
    this.nickname = nickname;
    friends = new ArrayList<>();
    scores = new HashMap<>();
  }

  @Override public boolean equals(Object object) {
    if (object instanceof Person) {
      Person that = (Person) object;
      return Objects.equal(this.name, that.name)
          && Objects.equal(this.nickname, that.nickname);
    }
    return false;
  }

  @Override public int hashCode() {
    return Objects.hashCode(name, nickname);
  }

  @Override public String toString() {
    return Objects.toStringHelper(this)
        .add("name", name)
        .add("nickname", nickname)
        .toString();
  }

  public String preferredName() {
    return Objects.firstNonNull(nickname, name);
  }

  public String allFriends() {
    return Joiner.on(", ").skipNulls().join(friends);
  }

  public String scores() {
    return Joiner.on("; ").useForNull("NO_SCORE")
        .withKeyValueSeparator(": ").appendTo(new StringBuilder(), scores).toString();
  }

}

