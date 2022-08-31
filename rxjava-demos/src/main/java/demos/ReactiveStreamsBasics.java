package demos;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

class IntegerPublisher implements Publisher<Integer> {
  Integer[] data = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

  List<Subscriber<? super Integer>> subs = new ArrayList<>();

  @Override
  public void subscribe(Subscriber<? super Integer> subscriber) {
    if (!subs.contains(subscriber)) {
      subs.add(subscriber);
    }
  }

  public void publish() {
    for (Subscriber<? super Integer> s : subs) {
      for (Integer d : data) {
        s.onNext(d);
      }
    }
  }

  public void publish(Integer i) {
    for (Subscriber<? super Integer> s : subs) {
      s.onNext(i);
    }
  }

  public void finish() {
    for (Subscriber<? super Integer> s : subs) {
      s.onComplete();
    }
  }

}

class IntegerSubscriber implements Subscriber<Integer> {

  @Override
  public void onSubscribe(Subscription subscription) {
    System.out.println("IntegerSubscriber subscribed");
  }

  @Override
  public void onNext(Integer integer) {
    System.out.println("New Integer: " + integer);
  }

  @Override
  public void onError(Throwable throwable) {
    System.err.println("Error with IntegerSubscriber: " + throwable);
  }

  @Override
  public void onComplete() {
    System.out.println("IntegerSubscriber complete");
  }
}

public class ReactiveStreamsBasics {

  public static void main(String[] args) {
    IntegerPublisher pub = new IntegerPublisher();
    IntegerSubscriber sub = new IntegerSubscriber();
    pub.subscribe(sub);
    pub.publish();
    pub.publish(10);
    pub.finish();
  }
}
