package demos;

import io.reactivex.rxjava3.core.Observable;

public class FirstObservable {
  // If you want to see a basic version of Reactive Streams' interfaces
  // Look at ReactiveStreamsBasics in this package

  public static void main(String[] args) {

    // Observables are Publishers in Reactive Java
    Observable<Integer> numbers = Observable.range(5, 10);

    // subscribe() assigns a Subscriber to the Publisher
    // Three methods: onNext, onError, onComplete
    // onError and onComplete are terminators (they run when the Publisher
    // finishes unsuccessfully or successfully)
    // Check out the overrides in the docs as well
    numbers.subscribe(i -> {
                        System.out.println("Received value: " + i);
                      },
                      error -> {
                        System.err.println("Something went wrong!");
                        System.err.println(error.getLocalizedMessage());
                      },
                      () -> {
                        System.out.println("Publisher has finished");
                      });

  }

}
