package demos;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import org.junit.Test;

public class WatchingChanges {

  @Test
  public void createSubject() {
    System.out.println("======================================================");
    System.out.println("= Creating a Subject");
    System.out.println("======================================================");
    Car honda = new Car("Honda", "Civic");
    honda.pubSubscribe(speed -> System.out.printf("Speed is now %d%n", speed),
                    error -> System.out.printf("Something went wrong%n"),
                    () -> System.out.println("Finished."));
    honda.accelerate(55);
    honda.brake(10);
    honda.accelerate(20);
    honda.brake(60);
  }

  @Test
  public void multipleSubscribers() {
    System.out.println("======================================================");
    System.out.println("= Multiple subscribers");
    System.out.println("======================================================");

    Car honda = new Car("Honda", "Civic");
    honda.replaySubscribe(speed -> System.out.printf("S1: Speed is now %d%n", speed),
                    error -> System.out.printf("S1: Something went wrong%n"),
                    () -> System.out.println("S1: Finished."));
    honda.accelerate(55);
    honda.brake(10);

    honda.replaySubscribe(speed -> System.out.printf("S2: Speed is now %d%n", speed),
                    error -> System.out.printf("S2: Something went wrong%n"),
                    () -> System.out.println("S2: Finished."));
    honda.accelerate(20);
    honda.brake(60);
  }

  @Test
  public void behaviorSubject() {
    System.out.println("======================================================");
    System.out.println("= Behavior subject");
    System.out.println("======================================================");

    Car honda = new Car("Honda", "Civic");
    honda.beSubscribe(speed -> System.out.printf("S1: Speed is now %d%n", speed),
                          error -> System.out.printf("S1: Something went wrong%n"),
                          () -> System.out.println("S1: Finished."));
    honda.accelerate(55);
    honda.brake(10);

    honda.beSubscribe(speed -> System.out.printf("S2: Speed is now %d%n", speed),
                          error -> System.out.printf("S2: Something went wrong%n"),
                          () -> System.out.println("S2: Finished."));
    honda.accelerate(20);
    honda.brake(60);
  }

  @Test
  public void asyncSubject() {
    System.out.println("======================================================");
    System.out.println("= Async subject");
    System.out.println("======================================================");

    Car honda = new Car("Honda", "Civic");
    honda.asyncSubscribe(speed -> System.out.printf("S1: Speed is now %d%n", speed),
                      error -> System.out.printf("S1: Something went wrong%n"),
                      () -> System.out.println("S1: Finished."));
    honda.accelerate(55);
    honda.brake(10);

    honda.asyncSubscribe(speed -> System.out.printf("S2: Speed is now %d%n", speed),
                      error -> System.out.printf("S2: Something went wrong%n"),
                      () -> System.out.println("S2: Finished."));
    honda.accelerate(20);
    honda.brake(60);

    honda.finish();
    System.out.println("Done");
  }


  private class Car {
    private String make;
    private String model;
    private int speed = 0;
    private PublishSubject<Integer> pubTracker;
    private ReplaySubject<Integer> replayTracker;
    private BehaviorSubject<Integer> beTracker;
    private AsyncSubject<Integer> asyncTracker;

    Car(String make, String model) {
      this.make = make;
      this.model = model;
      pubTracker = PublishSubject.create();
      replayTracker = ReplaySubject.create();
      beTracker = BehaviorSubject.create();
      asyncTracker = AsyncSubject.create();
    }

    private void updateSubjects(int speed) {
      pubTracker.onNext(speed);
      replayTracker.onNext(speed);
      beTracker.onNext(speed);
      asyncTracker.onNext(speed);
    }

    public void accelerate(int speed) {
      this.speed += speed;
      updateSubjects(this.speed);
    }

    public void brake(int speed) {
      this.speed -= speed;
      updateSubjects(this.speed);
    }

    public void finish() {
      asyncTracker.onComplete();
    }

    public void pubSubscribe(@NonNull Consumer<? super Integer> onNext, @NonNull Consumer<?
      super Throwable> onError, @NonNull Action onComplete) {
      pubTracker.subscribe(onNext, onError, onComplete);
    }

    public void replaySubscribe(@NonNull Consumer<? super Integer> onNext, @NonNull Consumer<?
      super Throwable> onError, @NonNull Action onComplete) {
      replayTracker.subscribe(onNext, onError, onComplete);
    }

    public void beSubscribe(@NonNull Consumer<? super Integer> onNext, @NonNull Consumer<?
      super Throwable> onError, @NonNull Action onComplete) {
      beTracker.subscribe(onNext, onError, onComplete);
    }

    public void asyncSubscribe(@NonNull Consumer<? super Integer> onNext, @NonNull Consumer<?
      super Throwable> onError, @NonNull Action onComplete) {
      asyncTracker.subscribe(onNext, onError, onComplete);
    }

    public String toString() {
      return make + " " + model;
    }
  }
}

