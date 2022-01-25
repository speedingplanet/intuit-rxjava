package demos;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Watcher {
  public static void main(String[] args) {
    Emitter emitter = new Emitter();
    emitter.subscribe(new Observer<String>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        // We don't care
      }

      @Override
      public void onNext(@NonNull String s) {
        System.out.printf("Watcher: Fired a(n) %s event!%n", s);
      }

      @Override
      public void onError(@NonNull Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });

    emitter.fireEvent("input");
  }
}
