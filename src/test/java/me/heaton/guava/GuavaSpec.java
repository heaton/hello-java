package me.heaton.guava;

import com.google.common.base.Splitter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class GuavaSpec {

  @Test
  public void try_splitter() {
    final Iterable<String> actual = Splitter.on(',').trimResults().omitEmptyStrings().split(" foo, ,bar, quux,");
    assertThat(actual, hasItems("foo", "bar", "quux"));
  }

}
