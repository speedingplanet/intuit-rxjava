package demos;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class RxOperators {

  @Ignore
  @Test
  public void takeTest() {
    /*
    take(int count) -> take only a certain number of items from the source Observable
    takeLast(int count) -> take the last count items from the source
    takeWhile(Predicate fn) -> take while the Predicate returns true
    forEach(Consumer) -> process each thing passed to it, returning nothing
    */

    System.out.println("======================================================");
    System.out.println("= take(int count) operator");
    System.out.println("======================================================");

    Observable<Integer> o1 = Observable.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    System.out.print("Take the first 5: ");
    Observable<Integer> o2 = o1.take(5);
    // o2.forEach(v -> System.out.printf("forEach: %d%n", v));
    o2.subscribe(v -> System.out.printf("%d ", v));
    System.out.println();

    System.out.print("Take the last five items: ");
    o1.takeLast(5)
      .forEach(v -> System.out.printf("%d ", v));
    System.out.println();

    System.out.print("Taking items while their value is less than three: ");
    o1.takeWhile(v -> v < 3)
      .forEach(v -> System.out.printf("%d ", v));
  }

  @Test
  public void coldOperators() {
    Observable<Integer> o1 = Observable.range(1, 100);
    Observable<Integer> o2 = o1.take(50);
    Observable<Integer> o3 = o2.take(25);
    Observable<Integer> o4 = o3.take(12);
    Observable<Integer> o5 = o4.take(6);

    o5.subscribe(System.out::println);
  }

  @Ignore
  @Test
  public void filterTest() {
    System.out.println("======================================================");
    System.out.println("= filter(Predicate f) operator");
    System.out.println("======================================================");

    Observable<Integer> numbers = Observable.range(0, 9);
    Observable<Integer> evenNumbers = numbers.filter(x -> x % 2 == 0);
    evenNumbers.subscribe(e -> System.out.printf("Even number: %d%n", e));
  }

  @Ignore
  @Test
  public void filterTimingTest() throws InterruptedException {
    System.out.println("======================================================");
    System.out.println("= filter over time");
    System.out.println("======================================================");
    Observable<Long> numbers = Observable.intervalRange(0, 10, 0, 500, TimeUnit.MILLISECONDS);
    Observable<Long> evenNumbers = numbers.filter(x -> x % 2 == 0);
    evenNumbers.subscribe(e -> System.out.printf("Even number: %d%n", e));
    Thread.sleep(2500); // Should see the first 3 even numbers, and then exit
  }

  @Ignore
  @Test
  public void mapTest() {
    // Process each value in the observable, returning an updated observable
    System.out.println("======================================================");
    System.out.println("= map(Function f) operator");
    System.out.println("======================================================");
    Observable<Integer> numbers = Observable.range(2, 10);
    numbers.map(x -> x * 2)
           .subscribe(System.out::println);
  }

  @Ignore
  @Test
  public void mapThenFilter() {
    // Process everything, filter results
    System.out.println("======================================================");
    System.out.println("= map, then filter");
    System.out.println("======================================================");
    AtomicInteger executionCount = new AtomicInteger();
    Observable<Integer> numbers = Observable.range(2, 10);
    numbers.map(x -> {
             executionCount.getAndIncrement();
             return x % 2 == 0 ? x / 2 : 0;
           })
           .filter(x -> {
             executionCount.getAndIncrement();
             return x > 0;
           })
           .subscribe(System.out::println);
    System.out.printf("map, then filter execution count: %d%n", executionCount.get());
  }

  @Ignore
  @Test
  public void filterThenMap() {
    // Filter before processing
    // Which is more efficient?
    System.out.println("======================================================");
    System.out.println("= filter, then map");
    System.out.println("======================================================");
    AtomicInteger executionCount = new AtomicInteger();
    Observable<Integer> numbers = Observable.range(2, 10);
    numbers.doOnNext((v) -> {
             executionCount.getAndIncrement();
           })
           .filter(x -> {

             return x % 2 == 0;
           })
           .doOnNext((v) -> {
             executionCount.getAndIncrement();
           })
           .map(x -> {
             return x / 2;
           })
           .subscribe(System.out::println);
    System.out.printf("filter, then map execution count: %d%n", executionCount.get());
  }

  @Ignore
  @Test
  public void doOnNextTest() {
    // For side effects
    System.out.println("======================================================");
    System.out.println("= doOnNext operator");
    System.out.println("======================================================");
    Observable.range(0, 10)
              .doOnNext(i -> System.out.printf("Initial doOnNext: %d%n", i))
              .filter(i -> i % 2 == 0)
              .doOnNext(i -> System.out.printf("doOnNext after filter%n"))
              .map(i -> i * 2)
              .doOnNext(i -> System.out.printf("doOnNext after map%n"))
              .blockingSubscribe(System.out::println);

  }

  @Test
  public void sortedTest() {
    System.out.println("======================================================");
    System.out.println("= sorted operator");
    System.out.println("======================================================");

    Observable<String> o1 = Observable.just("John", "Dan", "Tim", "Andreina", "Ted", "Patrick");
    Observable<String> sortedO1 = o1.sorted(); // Or provide a custom Comparator
    sortedO1.forEach(System.out::println);
  }

  @Ignore
  @Test
  public void flatMapTest() {
    // Flattens the layers of Observables
    System.out.println("======================================================");
    System.out.println("= flatMap(Function f (and others)) operator");
    System.out.println("======================================================");
    Observable<String> a =
      Observable.just("aardvark", "abscond", "alpha", "apples");
    Observable<String> b = Observable.just("banana", "bat", "beta", "bottle");
    Observable<String> c =
      Observable.just("car", "catamaran", "center", "cozy");
    Map<String, Observable<String>> wordList = new HashMap<>();

    // wordList['A'] ---> Observable<String> ["aardvark", "abscond", "alpha", "apples"]
    wordList.put("A", a);
    wordList.put("B", b);
    wordList.put("C", c);

    Observable<String> keys = Observable.just("A", "B", "C");

    // Inconvenient
    // Observable<Observable<String>> results = keys.map(wordList::get);

    // Might interleave, where concatMap will guarantee ordering
    keys.flatMap(key -> {
          System.out.printf("*** %s ***%n", key);
          return wordList.get(key); // without flatMap, this would be an Observable of words
        }) //  ["aardvark", "abscond", "alpha", "apples"], and following
        .subscribe(System.out::println);

  }

  @Test
  public void observerAndObservableTest() {
    Observable<Integer> numbers = Observable.range(1, 100)
                                            .filter(x -> x % 2 == 0)
                                            .map(x -> x * 2);
    TestObserver<Integer> observer = TestObserver.create();
    numbers.subscribe(observer);

  }

  @Ignore
  @Test
  // Nothing preventing you from using flatMap instead of map
  public void flatMapInsteadOfMap() {
    Observable<Integer> numbers = Observable.just(1, 2, 3, 4, 5);
    numbers.flatMap(x -> Observable.just(x * 2))
           // numbers.map(x -> x * 2)
           .subscribe(System.out::println);
  }

  @Ignore
  @Test
  @SuppressWarnings("unchecked")
  public void flatMapIterableTest() {
    // The source Observable has values that map to Iterables in some other structure
    // Return a new observable which emits the Iterables
    System.out.println("======================================================");
    System.out.println("= flatMapIterable operator");
    System.out.println("======================================================");
    String[] a = { "aardvark", "abscond", "alpha", "apples" };
    String[] b = { "banana", "bat", "beta", "bottle" };
    String[] c = { "car", "catamaran", "center", "cozy" };

    Observable<String> letters = Observable.just("A", "B", "C");
    letters.flatMapIterable(letter -> {
             switch (letter) {
               case "A":
                 return Arrays.asList(a);
               case "B":
                 return Arrays.asList(b);
               case "C":
                 return Arrays.asList(c);
               default:
                 return Collections.EMPTY_LIST;
             }
           })
           .subscribe(System.out::println);
  }

  @Ignore
  @Test
  public void composeExample() {
    String[] a = { "aardvark", "abscond", "alpha", "apples" };
    ObservableTransformer<String, StringBuilder> reverser =
      strOuter -> strOuter.map(s -> new StringBuilder().append(s)
                                                       .reverse());

    Observable<String> words = Observable.fromArray(a);
    words.map(String::toUpperCase)
         .compose(reverser)
         .subscribe(System.out::println);
  }

  @Ignore
  @Test
  public void composeTest() {
    // Cheap and easy-ish way to add functionality to a chain
    System.out.println("======================================================");
    System.out.println("= compose(ObservableTransformer) operator");
    System.out.println("======================================================");
    ObservableTransformer<String, Integer> convertToInteger =
      i -> i.map(Integer::parseInt);
    Observable.just("1", "2", "3")
              // .map(Integer::parseInt)
              .compose(convertToInteger)
              .map(v -> v * 2)
              .subscribe(System.out::println);
  }

  @Ignore
  @Test
  public void zipTest() {
    // Zip together two or more observables, emit while there's a value for
    // each that can be zipped together
    System.out.println("======================================================");
    System.out.println("= compose(ObservableTransformer) operator");
    System.out.println("======================================================");

    // antenna doesn't map to anything in 'b'.
    Observable<String> a =
      Observable.just("aardvark", "abscond", "alpha", "apples", "antenna");
    Observable<String> b = Observable.just("banana", "bat", "beta", "bottle");

    Observable<String> zipped =
      Observable.zip(a, b, (a1, b1) -> String.format("%s / %s%n", a1, b1));

    zipped.subscribe(System.out::print, System.err::println);
  }
}
