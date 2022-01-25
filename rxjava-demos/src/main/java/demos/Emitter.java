package demos;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;

public class Emitter implements ObservableSource<String> {

  Observer<? super String> observer;

  public void fireEvent(String name) {
    System.out.printf("Emitter: Fired a %s event%n", name);
    observer.onNext(name);
  }

  @Override
  public void subscribe(@NonNull Observer<? super String> observer) {
    this.observer = observer;
  }
}
