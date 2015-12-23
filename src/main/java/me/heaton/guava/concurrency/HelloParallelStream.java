package me.heaton.guava.concurrency;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class HelloParallelStream {

  public static void main(String[] args) {
    final ContiguousSet<Integer> integers = ContiguousSet.create(Range.closed(1, 100), DiscreteDomain.integers());
    final ImmutableList<Integer> ints = ImmutableList.copyOf(integers);
    final Stream<Integer> stream = ints.parallelStream();
    stream.forEach(new Consumer<Integer>() {
      @Override
      public void accept(Integer integer) {
        System.out.println(integer + " thread id: " + Thread.currentThread().getId());
      }
      @Override
      public Consumer<Integer> andThen(Consumer<? super Integer> after) {
        return null;
      }
    });

  }

}
