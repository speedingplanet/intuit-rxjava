package demos;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class App {
  public static void main(String[] args) {
    countToThree();
    iterateNames();
    ongoingCount();
  }

  public static void countToThree() {
    System.out.println("======================================================");
    System.out.println("= countToThree()");
    System.out.println("======================================================");

    // start, count (how many), initial delay, period, units
    Observable<Long> finite = Observable.intervalRange(1, 3, 1, 1, TimeUnit.SECONDS);
    finite.blockingSubscribe(v -> System.out.printf("%s%n", Math.toIntExact(v)),
                               e -> System.err.println("Something went wrong"),
                               () -> System.out.println("countToThree Finished"));
  }

  public static void iterateNames() {
    System.out.println("======================================================");
    System.out.println("= iterateNames()");
    System.out.println("======================================================");

    String[] names = { "John", "Dan", "Tim" };
    Observable<Long> namesPerSecond = Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS);
    namesPerSecond.blockingSubscribe(v -> System.out.printf("%s%n", names[Math.toIntExact(v)]),
                                     e -> System.err.println("Something went wrong"),
                                     () -> System.out.println("iterateNames Finished"));
  }

  public static void ongoingCount() {
    System.out.println("======================================================");
    System.out.println("= ongoingCount()");
    System.out.println("======================================================");

    Observable<Long> infinite = Observable.interval(1L, 1L, TimeUnit.SECONDS);
    infinite.blockingSubscribe(v -> System.out.printf("%s%n", Math.toIntExact(v)),
                               e -> System.err.println("Something went wrong"),
                               () -> System.out.println("Finished"));
  }

}
