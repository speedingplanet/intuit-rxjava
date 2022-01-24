package demos;

import io.reactivex.rxjava3.core.Observable;
import org.junit.Test;

import java.util.ArrayList;

public class AggregateOperators {
  @Test
  public void reduceTest() {
    System.out.println("======================================================");
    System.out.println("= reduce operator");
    System.out.println("======================================================");
    Observable<Integer> numbers = Observable.range(0, 10);

    numbers.reduce((total, current) -> total + current)
           .subscribe(System.out::println);
  }

  @Test
  public void collectTest() {
    // Collect all the values from a source Observable, re-emit as a single-value Observable
    System.out.println("======================================================");
    System.out.println("= collect operator");
    System.out.println("======================================================");
    Observable<Integer>  numbers = Observable.range(0, 10);
    numbers.collect(() -> new ArrayList<Integer>(), ArrayList::add)
           .subscribe(list -> {
             for (int n : list) {
               System.out.println(n);
             }
           });
  }

  @Test
  public void scanTest() {
    // Reduce, but shows the intermediate steps
    System.out.println("======================================================");
    System.out.println("= scan operator");
    System.out.println("======================================================");
    Observable<Integer>  numbers = Observable.range(0, 10);
    numbers.scan((total, current) -> total + current)
      .subscribe(System.out::println);
  }
}
