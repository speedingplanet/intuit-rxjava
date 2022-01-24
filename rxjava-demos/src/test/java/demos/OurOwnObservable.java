package demos;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class OurOwnObservable {

  @Test
  public void createObservableLambda() {
    System.out.println("======================================================");
    System.out.println("= Creating an Observable from Lambda functions");
    System.out.println("======================================================");

    Car honda = new Car("Honda", "Civic");
    honda.subscribe(speed -> System.out.printf("Speed changed from %d to %d%n", speed),
                    error -> System.err.println(error),
                    () -> System.out.println("Done"));
    honda.accelerate(55);
    honda.brake(10);
    honda.accelerate(20);
    honda.brake(60);
  }

  @Ignore
  @Test
  public void createObservableObserver() {
    System.out.println("======================================================");
    System.out.println("= Creating an Observable from an Observer");
    System.out.println("======================================================");
    Car honda = new Car("Honda", "Civic");
    honda.subscribe(new Observer<Integer>() {
      private int lastSpeed = 0;

      @Override
      public void onSubscribe(@NonNull Disposable d) {

      }

      @Override
      public void onNext(@NonNull Integer speed) {
        System.out.printf("Speed changed from %d to %d%n", lastSpeed, speed);
        // System.out.printf("Speed changed to %d%n", speed);
        lastSpeed = speed;
      }

      @Override
      public void onError(@NonNull Throwable e) {
        System.err.println("Error: " + e);
      }

      @Override
      public void onComplete() {
        System.out.println("Finished with speed updates");
      }
    });
    honda.accelerate(55);
    honda.brake(10);
    honda.accelerate(20);
    honda.brake(60);
  }

  private class Car implements ObservableSource<Integer> {
    private String make;
    private String model;
    private int speed = 0;
    private Observer<? super Integer> observer;

    Car(String make, String model) {
      this.make = make;
      this.model = model;
    }

    public void accelerate(int speed) {
      this.speed += speed;
      observer.onNext(this.speed);
    }

    public void brake(int speed) {
      this.speed -= speed;
      observer.onNext(this.speed);
    }

    public String toString() {
      return make + " " + model;
    }

    @Override
    public void subscribe(@NonNull Observer<? super Integer> observer) {
      this.observer = observer;
    }

    public void subscribe(@NonNull Consumer<? super Integer> onNextLambda,
                          @NonNull Consumer<? super Throwable> onErrorLambda,
                          @NonNull Action onCompleteLambda) {
      this.observer = new Observer<Integer>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull Integer integer) {
          try {
            onNextLambda.accept(integer);
          } catch (Throwable e) {
            e.printStackTrace();
          }
        }

        @Override
        public void onError(@NonNull Throwable e) {
          try {
            onErrorLambda.accept(e);
          } catch (Throwable ex) {
            ex.printStackTrace();
          }
        }

        @Override
        public void onComplete() {
          try {
            onCompleteLambda.run();
          } catch (Throwable e) {
            e.printStackTrace();
          }
        }
      };

    }

  }

}
