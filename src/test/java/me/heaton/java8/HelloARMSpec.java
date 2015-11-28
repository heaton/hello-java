package me.heaton.java8;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class HelloARMSpec {

  @Test
  public void try_arm() {
    final HelloARM instance = new HelloARM();
    assertThat(instance.tryAutoClose(), is("Exception in work()"));
  }

}
