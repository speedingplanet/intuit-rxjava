package demos;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CombinationOperators {
  @Test
  public void startWithTest() {
    // Prefix an Observable
    System.out.println("===== startWith =====");
    Observable<Integer> countdown = Observable.just(3, 2, 1);
    Observable<String>  main = Observable.just("Liftoff!", "{}======%%%%0####--->");
    main.startWith(countdown.map(Object::toString))
        .subscribe(System.out::println);
  }

  @Test
  public void concatTest() {
    System.out.println("===== concat =====");
    Observable<Integer>  i = Observable.just(1, 2, 3);
    Observable<Integer>  j = Observable.just(4, 5, 6);
    Observable<Integer>  numbers = Observable.concat(i, j);

    numbers.subscribe(System.out::println);
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
