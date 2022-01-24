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

import static org.junit.Assert.assertEquals;

// Let's look at how to create Observables
@SuppressWarnings("NumericOverflow")
public class CreatingObservables {

  private Integer[] n;
  private List<Integer> nList;
  private Observable<Integer> oList;

  @Before
  public void beforeAllTests() {
    n = new Integer[]{ 1, 2, 3, 4, 5 };
    nList = Arrays.asList(n);
    oList = Observable.fromArray(n);
  }

  @Ignore
  @Test
  public void testFromJust() {
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.just()");
    System.out.println("======================================================");
    String name = "John";
    Observable<String> o = Observable.just(name, "Dan", "Tim", "Andreina", "Hector", "Andres");

    o.subscribe(v -> System.out.printf("Value: %s%n", v),  // onNext
                e -> System.out.printf("Ouch!%n"),          // onError
                () -> System.out.printf("Finished%n"));     // onComplete
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
                      () -> System.out.printf("testFromArray finished%n"));
  }

  @Ignore
  @Test
  public void testFromIterable() {
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.fromIterable()");
    System.out.println("======================================================");

    oList.subscribe(value -> System.out.printf("value is %d%n", value),
                    error -> System.out.printf("Something went wrong!%n"),
                    () -> System.out.printf("testFromIterable finished%n"));
  }

  @Ignore
  @Test
  public void testToIterable() {
    System.out.println("======================================================");
    System.out.println("= Get all the items in an observable as an iterable");
    System.out.println("======================================================");

    Iterable<Integer> iter = oList.blockingIterable();
    for (Integer i : iter) {
      System.out.printf("Integer value: %d%n", i);
    }
  }

  @Ignore
  @Test
  public void testObservableSingle() {
    // Singles emit one value or an error, and never complete
    System.out.println("=========================================================");
    System.out.println("= Testing a Single from an Observable");
    System.out.println("=========================================================");
    Single<Integer> first = oList.first(0); // requires a default item
    assertEquals(first.blockingGet(), n[0]);
    first.subscribe(value -> System.out.println("Value: " + value),
                    error -> System.out.println("Problem: " + error)
    );
  }

  @Ignore
  @Test
  public void testObservableMaybe() {
    // Maybes emit a single value and then complete, or an error
    System.out.println("============================================================");
    System.out.println("= Testing a Maybe from an Observable");
    System.out.println("============================================================");
    Maybe<Integer> first = oList.firstElement();
    assertEquals(first.blockingGet(), n[0]);
  }

  @Ignore
  @Test
  public void testSupplier() {
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.fromSupplier()");
    System.out.println("======================================================");
    Observable<Long> o = Observable.fromSupplier(System::currentTimeMillis);
    o.subscribe(value -> System.out.printf("Value: %d%n", value),
                error -> System.out.printf("Something went wrong%n"),
                () -> System.out.println("testSupplier finished"));
  }

  @Ignore
  @Test
  public void testFromCallable() {
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.fromCallable()");
    System.out.println("======================================================");
    System.out.println("1) Defining the Observable");
    Observable<ArrayList<String>> cold = Observable.fromCallable(() -> {
      System.out.println("2) Callable is running");
      ArrayList<String> names = new ArrayList<>();
      names.add("John");
      names.add("Dan");
      names.add("Tim");
      System.out.println("3) Callable finished");
      return names;
    });
    System.out.println("4) Observable created");


    System.out.println("5) Before subscribing");
    cold.subscribe(v -> System.out.printf("Value: %s%n", v),
                   e -> System.out.printf("Ouch!%n"),
                   () -> System.out.printf("Finished%n"));
    System.out.println("6) After subscribing");

  }

  // Suppliers throw Throwables, more flexible than Exceptions, and are part of the
  // RxJava API. Do not confuse it with java.util.function.Supplier, which cannot
  // throw anything
  @SuppressWarnings("divzero")
  @Ignore
  @Test
  public void supplierGoneWrong() {
    System.out.println("=============================================================");
    System.out.println("= Demonstrating Observable.supplierGoneWrong(), with error");
    System.out.println("=============================================================");
    Observable<Long> o = Observable.fromSupplier(() -> 10L / 0);
    o.subscribe(value -> System.out.printf("Value: %d%n", value),
                error -> System.out.printf("Something went wrong: %s%n", error),
                () -> System.out.println("testSupplier finished"));
  }

  // Callables throw Exceptions, and are part of java.util.concurrent, which implies
  // running something in a separate thread
  @SuppressWarnings("divzero")
  @Test
  @Ignore
  public void callableGoneWrong() {
    System.out.println("==========================================================");
    System.out.println("= Demonstrating Observable.fromCallable(), with an error ");
    System.out.println("==========================================================");
    Observable<Integer> bad = Observable.fromCallable(() -> 10 / 0);

    bad.subscribe(v -> System.out.printf("Never happens%n"),
                  e -> System.out.printf("Error should happen%n"),
                  () -> System.out.printf("Complete, never happens.%n"));
  }

}
