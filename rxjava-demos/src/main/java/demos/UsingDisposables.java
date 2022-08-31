package demos;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class UsingDisposables {

  public static void main(String[] args) throws InterruptedException {
    Observable<Long> oNumbers = Observable.interval(0, 500, TimeUnit.MILLISECONDS);
    AtomicInteger count = new AtomicInteger(1);
    Disposable disposableNumbers = oNumbers.subscribe(
      v -> System.out.printf("Integer number %d: %d%n", count.getAndIncrement(), v),
      System.err::println,
      () -> System.out.println("Counter complete.")
    );
    System.out.println("Sleeping...");
    Thread.sleep(2000);
    System.out.println("We don't care about the counter anymore");
    disposableNumbers.dispose();
    Thread.sleep(2000);
    System.out.println("All done");
  }
}
