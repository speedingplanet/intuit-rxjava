package demos;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class FirstObservable {

  public static void main(String[] args) {

    // Observables are Publishers in Reactive Java
    Observable<Integer> numbers = Observable.range(1, 10);

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
