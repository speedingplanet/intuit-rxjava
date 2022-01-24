package demos;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import org.junit.Test;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ObserveOnSubscribeOn {
  @Test
  public void observeOnTest() throws InterruptedException {
    System.out.println("======================================================");
    System.out.println("= Demonstrating Observable.fromCallable()");
    System.out.println("======================================================");
    Observable.just("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")
              .map(i -> {
                System.out.printf("\t[%s] %s %n",
                                  Thread.currentThread()
                                        .getName(),
                                  i);
                return i + "{main}";
              })
              // All future ops on the computation thread, including subscribe
              .observeOn(Schedulers.computation())
              // .observeOn(Schedulers.io())
              .map(i -> {
                System.out.printf("\t[%s] %s %n",
                                  Thread.currentThread()
                                        .getName(),
                                  i);
                // return i + "{computation}";
                return i + "{io}";
              })
              .map(i -> {
                System.out.printf("\t[%s] (still) %s %n",
                                  Thread.currentThread()
                                        .getName(),
                                  i);
                return i;
              })
              .subscribe(v -> System.out.printf("OUTPUT: [%s] %s%n",
                                                Thread.currentThread()
                                                      .getName(),
                                                v), System.err::println);

    Thread.sleep(3000);
  }

  @Test
  public void subscribeOnTest() throws InterruptedException {
    System.out.println("===== subscribeOn =====");
    Observable.just("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")
              // Only subscribe will happen on this thread
              .subscribeOn(Schedulers.io())
              .map(i -> {
                System.out.printf("\t[%s] %s %n",
                                  Thread.currentThread()
                                        .getName(),
                                  i);
                return i + "{main}";
              })
              .map(i -> {
                System.out.printf("\t[%s] %s %n",
                                  Thread.currentThread()
                                        .getName(),
                                  i);
                return i + "{second map}";
              })
              .map(i -> {
                System.out.printf("\t[%s] (still) %s %n",
                                  Thread.currentThread()
                                        .getName(),
                                  i);
                return i;
              })
              .subscribe(v -> System.out.printf("OUTPUT: [%s] %s%n",
                                                Thread.currentThread()
                                                      .getName(),
                                                v), System.err::println);

    Thread.sleep(2000);
  }

  @Test
  public void subscribeOnWithSubjectTest() throws InterruptedException {
    System.out.println("===== subscribeOn with Subject =====");
    Observable<String>  o1 = Observable.just("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")
                       // Only subscribe will happen on this thread
                       .map(i -> {
                         System.out.printf("\t[%s] %s %n",
                                           Thread.currentThread()
                                                 .getName(),
                                           i);
                         return i + "{main}";
                       })
                       .map(i -> {
                         System.out.printf("\t[%s] %s %n",
                                           Thread.currentThread()
                                                 .getName(),
                                           i);
                         return i + "{second map}";
                       })
                       .map(i -> {
                         System.out.printf("\t[%s] (still) %s %n",
                                           Thread.currentThread()
                                                 .getName(),
                                           i);
                         return i;
                       });
    ReplaySubject<String> subject = ReplaySubject.<String>create();
    o1.subscribe(subject);
    subject.subscribeOn(Schedulers.io())
           .subscribe(v -> System.out.printf("OUTPUT: [%s] %s%n",
                                             Thread.currentThread()
                                                   .getName(),
                                             v), System.err::println);

    Thread.sleep(2000);
  }
}
