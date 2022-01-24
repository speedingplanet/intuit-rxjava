package demos;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class ReactiveHelloWorld {

  public static void main(String[] args) {
    intervalObservable();
    // sleepingObservable();
    // completedObservable();
  }

  public static void completedObservable() {
    Observable<Integer> numbers = Observable.range(1, 5);
    numbers.subscribe(value -> System.out.printf("Value: %d%n", value),
                      error -> System.out.println("Something went wrong: " + error),
                      () -> System.out.println("All finished"));
    numbers.subscribe(value -> System.out.printf("Take 2: Value: %d%n", value),
                      error -> System.out.println("Take 2: Something went wrong: " + error),
                      () -> System.out.println("Take 2: All finished"));
  }

  public static void sleepingObservable() throws InterruptedException {
    Observable<Long> counter = Observable.intervalRange(1, 10, 25, 100, TimeUnit.MILLISECONDS);
    counter.subscribe(value -> System.out.printf("Value: %d%n", value),
                              error -> System.out.println("Something went wrong: " + error),
                              () -> System.out.println("All finished"));
    Thread.sleep(500);
  }

  public static void intervalObservable() {
    Observable<Long> counter = Observable.intervalRange(1, 10, 0, 10, TimeUnit.MILLISECONDS);

    System.out.println("Before subscribe");
    // counter.subscribe(value -> System.out.printf("Value: %d%n", value),
    counter.blockingSubscribe(value -> System.out.printf("Value: %d%n", value),
                      error -> System.out.println("Something went wrong: " + error),
                      () -> System.out.println("All finished"));
    System.out.println("After subscribe");
  }
}
