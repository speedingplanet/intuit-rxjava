package demos;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BlockingExample {
  public static void main(String[] args) throws InterruptedException {
    Observable.intervalRange(1, 5, 0, 500, TimeUnit.MILLISECONDS)
              .take(5)
              .blockingSubscribe(v -> System.out.println("Blocking: " + v));
    intervalOne();
    intervalTwo();
    Thread.sleep(4000);

  }

  public static void intervalOne() {
    Observable<Long> o1 = Observable.interval(0, 200, TimeUnit.MILLISECONDS);
    o1.subscribe(v -> System.out.println("o1: " + v));
  }

  public static void intervalTwo() {
    Observable<Long> o2 = Observable.interval(20, 300, TimeUnit.MILLISECONDS);
    o2.subscribe(v -> System.out.println("o2: " + v));
  }
}
