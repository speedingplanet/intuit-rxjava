package demos;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CombinationOperators {
  @Test
  public void startWithTest() {
    // Prefix an Observable to the original
    // Also startWithArray, startWithItem, and startWithIterable
    System.out.println("======================================================");
    System.out.println("= startWith operator");
    System.out.println("======================================================");
    Observable<Integer> countdown = Observable.just(3, 2, 1);
    Observable<String>  main = Observable.just("Liftoff!", "{}======%%%%0####--->");
    main.startWith(countdown.map(Object::toString))
        .subscribe(System.out::println);
  }

  @Test
  public void concatTest() {
    // Concatenates Observables into a single Observable without interleaving
    System.out.println("======================================================");
    System.out.println("= concat operator");
    System.out.println("======================================================");
    Observable<Integer>  i = Observable.just(1, 2, 3);
    Observable<Integer>  j = Observable.just(4, 5, 6);
    Observable<Integer>  numbers = Observable.concat(i, j);

    numbers.subscribe(System.out::println);
  }

  @Test
  public void timedConcatTest() {
    System.out.println("======================================================");
    System.out.println("= concat operator with timing");
    System.out.println("======================================================");
    Observable<Integer>  i =
        Observable.intervalRange(1, 3, 0, 1000, TimeUnit.MILLISECONDS).map(Long::intValue);
    Observable<Integer>  j = Observable.just(4, 5, 6);
    Observable<Integer>  numbers = Observable.concat(i, j);

    numbers.blockingSubscribe(System.out::println);
  }

  @Ignore
  @Test
  public void mergeTest() {
    System.out.println("======================================================");
    System.out.println("= merge and concat operators");
    System.out.println("======================================================");
    Observable<String> a =
      Observable.just("aardvark", "abscond", "alpha", "apples");
    Observable<String> b = Observable.just("banana", "bat", "beta", "bottle");

    // Merge can interleave results, but won't always (see next test)
    Observable<String> merged = Observable.merge(a, b);
    // Concat appends the results of b to a, requires a to complete first
    Observable<String> concatenated = Observable.concat(a, b);
    System.out.println("Merged: ");
    merged.subscribe(System.out::println, System.err::println);
    System.out.println("Concatenated: ");
    concatenated.subscribe(System.out::println, System.err::println);
  }

  @Ignore
  @Test
  public void mergeConcatTest() {
    System.out.println("======================================================");
    System.out.println("= merge and concat operators, async");
    System.out.println("======================================================");
    Observable<Long> range10 = Observable.intervalRange(0, 10, 0, 100, TimeUnit.MILLISECONDS);
    Observable<Long> range20 = Observable.intervalRange(0, 10, 0, 100, TimeUnit.MILLISECONDS);

    // Merge can interleave results, but won't always
    // Observable<Long> merged = Observable.merge(range10, range20);
    // Concat appends the results of range10 and range20, requires range10 to complete first
    Observable<Long> concatenated = Observable.concat(range20, range10);
    // System.out.print("Merged: ");
    // merged.blockingSubscribe(v -> System.out.printf("%d, ", v), System.err::println);
    System.out.println();
    long start, finish;
    start = System.currentTimeMillis();
    System.out.println("Concatenated starting at " + start);
    concatenated.blockingSubscribe(v -> System.out.printf("%d, ", v), System.err::println);
    finish = System.currentTimeMillis();
    System.out.println();
    System.out.println("Concatenated ending at " + finish);
    System.out.println("Difference: " + (finish - start));
  }

  @Test
  public void ambTest() throws InterruptedException {
    // Returns the observable that finishes first
    // Could be inconsistent, depending on how fast threads are running!
    System.out.println("===== amb =====");
    Observable<Integer> timer =
      Observable.intervalRange(0, 4, 50, 50, TimeUnit.MILLISECONDS,
                               Schedulers.newThread()).map(Math::toIntExact);
    Observable<Integer>  i = Observable.zip(Observable.just(1, 2, 3, 4), timer, (x, t) -> x);
    Observable<Integer>  j = Observable.zip(Observable.just(4, 5, 6), timer, (x, t) -> x);
    Observable<Integer>  k = Observable.zip(Observable.just(7, 8, 9, 10, 11), timer, (x, t) -> x);
    Observable.ambArray(i, j, k)
              .subscribeOn(Schedulers.computation())
              .subscribe(System.out::println);

    Thread.sleep(2000);
  }
}
