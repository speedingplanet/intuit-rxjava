package demos;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class FirstObservable {

  public static void counter() {
    // This would be what a Publisher wraps around
    int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    for (int x: numbers) {
      System.out.println("Received values: " + x);
    }
  }

  public static void counterAsPublisher(Subscriber<Integer> sub) {
    sub.onSubscribe((Subscription) sub);

    int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    for (int x: numbers) {
      sub.onNext(x);
    }

    sub.onComplete();

  }

  public static void subscribeToCounter() {
    class CustomSubscriber implements Subscriber<Integer> {

      @Override
      public void onSubscribe(Subscription subscription) {

      }

      @Override
      public void onNext(Integer integer) {
        System.out.println("Next value: " + integer);
      }

      @Override
      public void onError(Throwable throwable) {
        System.err.println("Something went wrong!");
      }

      @Override
      public void onComplete() {
        System.out.println("Finished!");
      }
    }

    counterAsPublisher(new CustomSubscriber());
  }

  public static void main(String[] args) {

    // Observables are Publishers in Reactive Java
    Observable<Integer> numbers = Observable.range(5, 10);

    // subscribe() assigns a Subscriber to the Publisher
    // Three methods: onNext, onError, onComplete
    // onError and onComplete are terminators (they run when the Publisher
    // finishes unsuccessfully or successfully)
    numbers.subscribe(i -> {
      System.out.println("Received value: " + i);
    }, error -> {
      System.err.println("Something went wrong!");
      System.err.println(error);
    }, () -> {
      System.out.println("Publisher has finished");
    });

    // We can write a Subscriber class if we'd like
    // CustomObserver co = new CustomObserver();
    // numbers.subscribe(co);
  }

  // Observers are Subscribers
  static class CustomObserver implements Observer<Integer> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
      System.out.println("Successfully subscribed");
    }

    @Override
    public void onNext(@NonNull Integer integer) {
      System.out.println("CustomObserver.onNext(): " + integer);
    }

    @Override
    public void onError(@NonNull Throwable e) {
      System.err.println("CustomObserver.onError(): " + e);

    }

    @Override
    public void onComplete() {
      System.out.println("CustomObserver.onComplete(): finished");
    }
  }
}
