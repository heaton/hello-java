package me.heaton.guava;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import java.util.*;

public class Person implements Comparable<Person>{

  final String name, nickname;
  final int age;
  final List<String> friends;
  final Map<String, String> scores;

  public Person(String name, String nickname, int age) {
    this.name = name;
    this.nickname = nickname;
    this.age = age;
    friends = new ArrayList<>();
    scores = new HashMap<>();
  }

  public String preferredName() {
    return MoreObjects.firstNonNull(nickname, name);
  }

  public String allFriends() {
    return Joiner.on(", ").skipNulls().join(friends);
  }

  public String scores() {
    return Joiner.on("; ").useForNull("NO_SCORE")
        .withKeyValueSeparator(": ").appendTo(new StringBuilder(), scores).toString();
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
    return Objects.hashCode(name, nickname, age);
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", name)
        .add("nickname", nickname)
        .add("age", age)
        .toString();
  }

  @Override
  public int compareTo(Person that) {
    return ComparisonChain.start()
        .compare(this.name, that.name)
        .compare(this.nickname, that.nickname)
        .compare(this.age, that.age)
        .result();
  }

}

