package demos;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class UsingDisposables {

  public static void main(String[] args) throws InterruptedException {
    Observable<Long> oNumbers = Observable.interval(0, 500, TimeUnit.MILLISECONDS);
    AtomicLong count = new AtomicLong(0);
    Disposable disposableNumbers = oNumbers.subscribe(
      v -> {
        System.out.printf("Long value: %d%n", v);
        count.set(v);
      },
      System.err::println,
      () -> System.out.println("Counter complete.")
    );
    System.out.println("Sleeping...");
    while (count.get() < 20) {
      Thread.sleep(500);
    }
    disposableNumbers.dispose();

    System.out.println("All done");
  }
}
