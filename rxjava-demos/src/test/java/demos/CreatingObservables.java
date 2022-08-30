package demos;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

// Let's look at how to create Observables
@SuppressWarnings("NumericOverflow")
public class CreatingObservables {

  private Integer[] n;
  List<Integer> nList;

  @Before
  public void beforeAllTests() {
    n = new Integer[]{ 1, 2, 3, 4, 5 };
    nList = Arrays.asList(n);
  }

  @Ignore
  @Test
  public void testFromJust() {
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.just()");
    System.out.println("======================================================");
    String name = "John";
    Observable<String> o = Observable.just(name, "Dan", "Tim", "Andreina", "Hector", "Andres");

    o.subscribe(v -> System.out.printf("Value: %s%n", v),               // onNext
                e -> System.out.printf("Something went wrong!%n"),      // onError
                () -> System.out.printf("testFromJust() finished%n"));  // onComplete

    o.subscribe(v -> System.out.printf("Second subscription: %s%n", v),
                e -> System.out.printf("Something went wrong!%n"),
                () -> System.out.printf("Second subscription: testFromJust() finished%n"));

  }

  @Ignore
  @Test
  public void testGetOneValue() {
    System.out.println("======================================================");
    System.out.println("= Get just one value from an observable");
    System.out.println("======================================================");
    String name = "John";
    Observable<String> o = Observable.just(name, "Dan", "Tim", "Andreina", "Hector", "Andres");
    String firstName = o.blockingFirst();
    System.out.printf("First value: %s%n", firstName);
  }

  @Ignore
  @Test
  public void testFromRange() {
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.range(int start, int count)");
    System.out.println("======================================================");

    Observable<Integer> oRange = Observable.range(10, 35);
    oRange.subscribe(value -> System.out.printf("Value: %d%n", value),
                     error -> System.err.println("Something went wrong"),
                     () -> System.out.printf("Range completed"));
  }

  @Ignore
  @Test
  public void testFromArray() {
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.fromArray()");
    System.out.println("======================================================");
    Observable<Integer> numbers = Observable.fromArray(n);

    numbers.subscribe(value -> System.out.printf("value is %d%n", value),
                      error -> System.out.printf("Something went wrong!%n"),
                      () -> System.out.printf("testFromArray() finished%n"));
  }

  @Ignore
  @Test
  public void testFromIterable() {
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.fromIterable()");
    System.out.println("======================================================");

    Observable<Integer> oList = Observable.fromIterable(nList);
    oList.subscribe(value -> System.out.printf("value is %d%n", value),
                    error -> System.out.printf("Something went wrong!%n"),
                    () -> System.out.printf("testFromIterable() finished%n"));
  }

  @Ignore
  @Test
  public void testToIterable() {
    System.out.println("======================================================");
    System.out.println("= Get all the items in an observable as an iterable");
    System.out.println("======================================================");

    Observable<Integer> oList = Observable.just(1, 2, 3, 4, 5, 6, 7, 8);
    // Iterable<Integer> iter = oList.blockingIterable();
    Iterable<Integer> iter = oList.blockingIterable();
    for (Integer i : iter) {
      System.out.printf("Integer value: %d%n", i);
    }
  }

  @Ignore
  @Test
  public void testFromInterval() throws InterruptedException {
    System.out.println("======================================================");
    System.out.println("= An Observable that emits at intervals");
    System.out.println("======================================================");

    Observable<Long> infinite = Observable.interval(100, 250, TimeUnit.MILLISECONDS);

    // Plain subscribe() will exit because it runs on a daemon thread
    // infinite.subscribe(value -> System.out.printf("Current value: %d%n", value),
    infinite.subscribe(value -> System.out.printf("First sub, non-blocking: %d%n", value),
                               error -> System.err.println("Something went wrong! "),
                               () -> System.out.printf("onComplete will never run%n"));

    // Never gets here because a) previous code is blocking and b) never completes
    infinite.subscribe(value -> System.out.printf("Second sub, blocking: %d%n", value),
                               error -> System.err.println("Something went wrong! "),
                               () -> System.out.printf("onComplete will never run%n"));

    Thread.sleep(1500);
    System.out.println("About to exit, probably havent' seen any numbers at this point.");
  }

  @Ignore
  @Test
  public void testFromIntervalRange() throws InterruptedException {
    System.out.println("==============================================================");
    System.out.println("= An Observable that emits at intervals for a range of values");
    System.out.println("==============================================================");

    Observable<Long> slowTen = Observable.intervalRange(1, 10, 100, 250, TimeUnit.MILLISECONDS);
    System.out.println("Before first sleep");
    Thread.sleep(4000); // No effect because the observable is cold
    System.out.println("After first sleep");
    slowTen.subscribe(value -> System.out.printf("Current value: %d%n", value),
                      error -> System.err.println("Something went wrong! "),
                      () -> System.out.printf("Finished with all ten values"));
    System.out.println("Before last sleep");
    Thread.sleep(4000); // Gives subscribe() time to run
    System.out.println("After last sleep");

  }

  @Ignore
  @Test
  public void testObservableSingle() {
    // Singles emit one value or an error, and never completes
    System.out.println("=========================================================");
    System.out.println("= Testing a Single from an Observable");
    System.out.println("=========================================================");

    Observable<Integer> oList = Observable.fromIterable(nList);
    Single<Integer> first = oList.first(0); // requires a default item
    System.out.printf("Value as a one-liner: %d%n",
                      oList.first(0)
                           .blockingGet());
    assertEquals(first.blockingGet(), n[0]);

    // Literally does not take a third argument
    first.subscribe(value -> System.out.println("Value: " + value),
                    error -> System.out.println("Problem: " + error)
                   );
  }

  @Ignore
  @Test
  public void testObservableMaybe() {
    // Maybes emit a single value, an error, or if no value, just completes
    System.out.println("============================================================");
    System.out.println("= Testing a Maybe from an Observable");
    System.out.println("============================================================");
    Observable<Integer> oList = Observable.fromIterable(nList);
    Maybe<Integer> first = oList.firstElement();

    // isEmpty() returns a Single (not a boolean)
    System.out.printf("Is the Maybe empty? %b%n",
                      first.isEmpty()
                           .blockingGet());
    first.subscribe(v -> {
                      System.out.println("Maybe should print this once: " + v);
                    },
                    error -> {
                      System.err.println("Maybe could go here, if something goes wrong");
                    },
                    () -> {
                      System.out.println("Maybe prints this if there is no value");
                    });
  }

  @Ignore
  @Test
  public void testObservableMaybeEmpty() {
    // Maybes emit a single value, an error, or if no value, just completes
    System.out.println("============================================================");
    System.out.println("= Testing an empty Maybe from an Observable");
    System.out.println("============================================================");
    Observable<Integer> oEmpty = Observable.empty();
    Maybe<Integer> first = oEmpty.firstElement();

    // isEmpty() returns a Single (not a boolean)
    System.out.printf("Is the Maybe empty? %b%n",
                      first.isEmpty()
                           .blockingGet());
    first.subscribe(v -> {
                      System.out.println("Maybe should not print this: " + v);
                    },
                    error -> {
                      System.err.println("Maybe could go here, if something goes wrong");
                    },
                    () -> {
                      System.out.println("Maybe should print this since it's empty");
                    });
  }

  @Ignore
  @Test
  public void testSupplier() {
    /*
     * Critically, this is a RxJava Supplier http://reactivex.io/RxJava/3
     * .x/javadoc/io/reactivex/rxjava3/functions/Supplier.html
     * NOT a Java SE Supplier. RxJava Suppliers can throw Throwables
     */
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.fromSupplier()");
    System.out.println("======================================================");
    Observable<Long> o = Observable.fromSupplier(System::currentTimeMillis);
    o.subscribe(value -> System.out.printf("Value: %d%n", value),
                error -> System.out.printf("Something went wrong%n"),
                () -> System.out.println("testSupplier() finished"));
  }

  // RxJava Suppliers throw Throwables, more flexible than Exceptions, and are part of the
  // RxJava API. Do not confuse it with java.util.function.Supplier, which cannot
  // throw anything
  @SuppressWarnings("rawtypes")
  @Ignore
  @Test
  public void supplierGoneWrong() {
    System.out.println("=============================================================");
    System.out.println("= Demonstrating Observable.supplierGoneWrong(), with error");
    System.out.println("=============================================================");
    Observable<Long> o = Observable.fromSupplier(() -> {
      if (4 % 2 == 1) {
        return 1L;
      } else {
        throw new Error("Very bad at math");
      }
    });
    o.subscribe(value -> System.out.printf("Value: %d%n", value),
                error -> System.out.printf("As expected: something went wrong: %s%n", error),
                () -> System.out.println("testSupplier() finished"));

    java.util.function.Supplier supplierThatThrows = () -> {
      if (4 % 2 == 1) {
        return 1L;
      } else {
        throw new Error("Very bad at math");
      }
    };

    // This won't work, because supplierThatThrows is of the wrong type
    // Observable<Long> o2 = Observable.fromSupplier(supplierThatThrows);
  }

  @Ignore
  @Test
  public void testFromCallable() {
    /*
     * Callables are Java SE standard, but can only throw Exceptions, not Throwables
     */
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.fromCallable()");
    System.out.println("======================================================");
    Callable<Integer> getNumber = () -> 10;
    Observable<Integer> o = Observable.fromCallable(getNumber);
    o.subscribe(value -> System.out.printf("Value: %d%n", value),
                error -> System.out.printf("Something went wrong: %s%n", error),
                () -> System.out.println("testFromCallable() finished"));
  }

  // Callables throw Exceptions, and are part of java.util.concurrent, which implies
  // (but does not require) running something in a separate thread
  @SuppressWarnings("divzero")
  @Test
  @Ignore
  public void callableGoneWrong() {
    System.out.println("=============================================================");
    System.out.println("= Demonstrating Observable.fromCallable(), with an exception ");
    System.out.println("=============================================================");
    Observable<Integer> bad = Observable.fromCallable(() -> 10 / 0);

    bad.subscribe(v -> System.out.printf("Never happens%n"),
                  e -> System.out.printf("Exception, which should happen%n"),
                  () -> System.out.printf("Complete, never happens.%n"));
  }

  @Ignore
  @Test
  public void testFromCallableAsDeferred() {
    /*
     * Callables are great for deferring expensive calculations
     */
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.fromCallable()");
    System.out.println("======================================================");
    Callable<ArrayList<String>> deferred = () -> {
      System.out.println("Callable is running");
      ArrayList<String> names = new ArrayList<>();
      names.add("John");
      names.add("Dan");
      names.add("Tim");
      System.out.println("Callable finished");
      return names;
    };

    System.out.println("Creating the Observable");
    Observable<ArrayList<String>> cold = Observable.fromCallable(deferred);
    System.out.println("Observable created");

    System.out.println("Before subscribing");
    cold.subscribe(value -> System.out.printf("Value: %s%n", value),
                   error -> System.out.printf("Something went wrong: %s%n", error),
                   () -> System.out.printf("testFromCallableAsDeferred() Finished%n"));
    System.out.println("After subscribing");

  }

}
