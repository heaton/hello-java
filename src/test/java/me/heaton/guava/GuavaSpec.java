package me.heaton.guava;

import com.google.common.base.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.math.BigIntegerMath;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import org.junit.Test;
import org.springframework.test.context.TestExecutionListeners;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class GuavaSpec {

  @Test
  public void try_splitter() {
    final Iterable<String> actual = Splitter.on(',').trimResults().omitEmptyStrings().split(" foo, ,bar, quux,");
    assertThat(actual, hasItems("foo", "bar", "quux"));
  }

  @Test
  public void try_strings() {
    final String s = Joiner.on(", ").skipNulls().join(ImmutableList.of("hello", "world!"));
    assertThat(s, is("hello, world!"));

    String noControl = CharMatcher.JAVA_ISO_CONTROL.removeFrom("hello"); // remove control characters
    assertThat(noControl, is("hello"));

    String theDigits = CharMatcher.DIGIT.retainFrom("abc123"); // only the digits
    assertThat(theDigits, is("123"));

    String spaced = CharMatcher.WHITESPACE.trimAndCollapseFrom("  I don't like too   much white  space!     ", ' ');
    // trim whitespace at ends, and replace/collapse whitespace into single spaces
    assertThat(spaced, is("I don't like too much white space!"));

    String noDigits = CharMatcher.JAVA_DIGIT.replaceFrom("into 123", "*"); // star out all digits
    assertThat(noDigits, is("into ***"));

    String lowerAndDigit = CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom("SOMETHING_BIG_only 123");
    // eliminate all characters that aren't digits or lowercase
    assertThat(lowerAndDigit, is("only123"));

    final String name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME");
    assertThat(name, is("constantName"));
  }

  @Test
  public void try_ordering() {
    Ordering<String> byLengthOrdering = new Ordering<String>() {
      public int compare(String left, String right) {
        return Ints.compare(left.length(), right.length());
      }
    };
    List<String> list = ImmutableList.of("I'm longest", "shorter");
    assertTrue(byLengthOrdering.reverse().isOrdered(list));
    assertThat(Ordering.natural().min(1, 2, 3, 4, 5), is(1));
  }

  @Test(expected = NullPointerException.class)
  public void try_preconditions() {
    new Movie().setState(null);
    fail();
  }

  @Test
  public void try_tree() throws ExecutionException {
    final LoadingCache<String, Integer> cache = CacheBuilder.newBuilder()
        .maximumSize(1000)
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .ticker(new Ticker() {
          @Override
          public long read() {
            return System.currentTimeMillis();
          }
        })
        .build(new CacheLoader<String, Integer>() {
          @Override
          public Integer load(String key) throws Exception {
            return key.length();
          }
        });
    assertThat(cache.get("abc"), is(3));
  }

  @Test
  public void try_hashing() {
    final HashFunction sha1 = Hashing.sha1();
    final String hash = sha1.newHasher().putString("Heaton's password", Charsets.UTF_8).hash().toString();
    assertThat(hash, is("10101f1c693e18833059cbf2e806f387c878f7cc"));
  }

  @Test
  public void try_math() {
    int logFloor = LongMath.log2(2, RoundingMode.FLOOR);
    assertThat(logFloor, is(1));

    int mustNotOverflow = IntMath.checkedMultiply(1000, 10000);
    assertThat(mustNotOverflow, is(10000000));

    long quotient = LongMath.divide(300, 3, RoundingMode.UNNECESSARY); // fail fast on non-multiple of 3
    assertThat(quotient, is(100l));

    BigInteger nearestInteger = DoubleMath.roundToBigInteger(12345.6789, RoundingMode.HALF_EVEN);
    assertThat(nearestInteger, is(BigInteger.valueOf(12346)));

    BigInteger sideLength = BigIntegerMath.sqrt(nearestInteger, RoundingMode.CEILING);
    assertThat(sideLength, is(BigInteger.valueOf(112)));
  }

}
