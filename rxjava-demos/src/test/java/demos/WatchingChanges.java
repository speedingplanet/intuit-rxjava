package demos;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.junit.Test;

public class WatchingChanges {

  @Test
  public void createSubject() {
    System.out.println("======================================================");
    System.out.println("= Creating a Subject");
    System.out.println("======================================================");
    Car honda = new Car("Honda", "Civic");
    honda.subscribe(speed -> System.out.printf("Speed is now %d%n", speed),
                    error -> System.out.printf("Something went wrong%n"),
                    () -> System.out.println("Finished."));
    honda.accelerate(55);
    honda.brake(10);
    honda.accelerate(20);
    honda.brake(60);
  }

  private class Car {
    private String make;
    private String model;
    private int speed = 0;
    private PublishSubject<Integer> speedTracker;

    Car(String make, String model) {
      this.make = make;
      this.model = model;
      speedTracker = PublishSubject.create();
    }

    public void accelerate(int speed) {
      this.speed += speed;
      speedTracker.onNext(this.speed);
    }

    public void brake(int speed) {
      this.speed -= speed;
      speedTracker.onNext(this.speed);
    }

    public void subscribe(@NonNull Consumer<? super Integer> onNext, @NonNull Consumer<?
      super Throwable> onError, @NonNull Action onComplete) {
      speedTracker.subscribe(onNext, onError, onComplete);
    }

    public String toString() {
      return make + " " + model;
    }
  }
}

