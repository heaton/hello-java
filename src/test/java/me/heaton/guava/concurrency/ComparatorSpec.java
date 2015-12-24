package me.heaton.guava.concurrency;

import com.google.common.base.Objects;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ComparatorSpec {

  @Test
  public void it_should_return_one_object_with_same_tag_when_the_two_lists_have_the_same_items() {
    final MapObject mapA = new MapObject(ImmutableMap.of(
        MapObject.KEY1, "key1 1",
        MapObject.KEY2, "key2 1",
        MapObject.NAME, "heaton"));
    final BigList listA = new BigList(ImmutableList.of(mapA));

    final MapObject mapB = new MapObject(ImmutableMap.of(
        MapObject.KEY1, "key1 1",
        MapObject.KEY2, "key2 1",
        MapObject.NAME, "xr"));
    final BigList listB = new BigList(ImmutableList.of(mapB));

    final List<Map<String, String>> expect = ImmutableList.of(
        ImmutableMap.<String, String>builder()
            .putAll(mapA.map)
            .put(MapObject.TAG, MapObject.SAME)
            .build());
    assertThat(listA.merge(listB), equalTo(expect));
  }

  @Test
  public void it_should_return_all_the_objects_when_the_two_lists_do_not_have_same_items() {
    final MapObject mapA = new MapObject(ImmutableMap.of(
        MapObject.KEY1, "key1 1",
        MapObject.KEY2, "key2 1",
        MapObject.NAME, "heaton"));
    final BigList listA = new BigList(ImmutableList.of(mapA));

    final MapObject mapB = new MapObject(ImmutableMap.of(
        MapObject.KEY1, "key1 1",
        MapObject.KEY2, "key2 2",
        MapObject.NAME, "xr"));
    final BigList listB = new BigList(ImmutableList.of(mapB));

    final List<Map<String, String>> expect = ImmutableList.of(
        ImmutableMap.<String, String>builder()
            .putAll(mapA.map)
            .put(MapObject.TAG, MapObject.IN_A)
            .build(),
        ImmutableMap.<String, String>builder()
            .putAll(mapB.map)
            .put(MapObject.TAG, MapObject.IN_B)
            .build()
        );
    assertThat(listA.merge(listB), equalTo(expect));
  }

  @Test
  public void try_with_5000() {
    final BigList listA = getRandomList(5000);
    final BigList listB = getRandomList(5000);
    long start = System.currentTimeMillis();
    final List<Map<String, String>> result = listA.merge(listB);
    long end = System.currentTimeMillis();
    System.out.println("result size is " + result.size());
    System.out.println("using " + (end-start) + "ms");
  }

  private final Random random = new Random();

  private BigList getRandomList(int size) {
    return new BigList(ContiguousSet.create(Range.closed(1, size), DiscreteDomain.integers()).parallelStream().map(i ->
        new MapObject(ImmutableMap.of(
            MapObject.KEY1, randomKey(),
            MapObject.KEY2, randomKey(),
            MapObject.NAME, String.valueOf(i)
        ))
    ).collect(Collectors.toList()));

  }

  private String randomKey() {
    return String.valueOf(random.nextInt(1000));
  }

}

class BigList {

  private final ImmutableSet<MapObject> list;

  BigList(List<MapObject> list) {
    this.list = ImmutableSet.copyOf(list);
  }

  public List<Map<String, String>> merge(BigList other) {
    return Sets.union(list, other.list).parallelStream().<Map<String, String>>map(mapObject -> {
      boolean isInA = this.list.contains(mapObject);
      boolean isInB = other.list.contains(mapObject);
      if(isInA && isInB) {
        mapObject.tag(MapObject.SAME);
      } else if(isInA) {
        mapObject.tag(MapObject.IN_A);
      } else {
        mapObject.tag(MapObject.IN_B);
      }
      return mapObject.map;
        }
    ).collect(Collectors.toList());
  }
}

class MapObject {
  public static final String KEY1 = "connectionString";
  public static final String KEY2 = "driver";
  public static final String NAME = "name";

  public static final String TAG = "tag";
  public static final String SAME = "same";
  public static final String IN_A = "in_a";
  public static final String IN_B = "in_b";


  final Map<String, String> map;

  MapObject(Map<String, String> map) {
    this.map = new HashMap<>(map);
  }

  public boolean equalTo(MapObject other) {
    return Objects.equal(this.map.get(KEY1), other.map.get(KEY1))
        && Objects.equal(this.map.get(KEY2), other.map.get(KEY2));
  }

  public void tag(String tag) {
    map.put(TAG, tag);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MapObject other = (MapObject) o;
    return this.equalTo(other);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.map.get(KEY1), this.map.get(KEY2));
  }
}

