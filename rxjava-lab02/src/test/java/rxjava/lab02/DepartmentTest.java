package rxjava.lab02;

import io.reactivex.rxjava3.core.Observable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DepartmentTest {
  Employee emp1, emp2, emp3, emp4, emp5, emp6, emp7;
  Manager mgr1, mgr2;
  List<Employee> mgr1Team, mgr2Team;
  Department itDept;

  @Before()
  public void setUp() {
    emp1 = new Employee("John", "Paxton", 50000);
    emp2 = new Employee("Angela", "Cortez", 65000);
    emp3 = new Employee("Jack", "Hawksmoor", 60000);
    emp4 = new Employee("Shen", "Li-Min", 70000);
    emp5 = new Employee("Jeroen", "Thornedike", 60000);
    emp6 = new Employee("Lucas", "Trent", 40000);
    emp7 = new Employee("Jenny", "Quantum", 80000);

    mgr1Team = new ArrayList<>(Arrays.asList(emp1, emp2, emp3));
    mgr2Team = new ArrayList<>(Arrays.asList(emp4, emp5, emp6));

    mgr1 = new Manager("Steve", "Topdog", 100000, mgr1Team);
    mgr2 = new Manager("Jenny", "Bigcheese", 100000, mgr2Team);

    itDept = new Department("IT");
    itDept.addManager(mgr1);
    itDept.addManager(mgr2);

  }

  @Test
  public void lab2Part3Item2() {
    Observable<Employee> empsWithErrors = errorOn60000();
    empsWithErrors.onErrorComplete()
                  .subscribe(System.out::println, err -> System.err.println(
                      "Shouldn't see this"), () -> System.out.println("Finished"));
  }

  @Test
  public void lab2Part3Item3() {
    Observable<Employee> empsWithErrors = errorOn60000();
    empsWithErrors.onErrorReturnItem(new Employee("Test", "Testington", 0))
                  .subscribe(System.out::println, err -> System.err.println(
                      "Shouldn't see this"), () -> System.out.println("Finished"));
  }

  @Test
  public void lab2Part3Item4() {
    errorAsObservable()
        .flatMap(o -> {
          return o.onErrorResumeNext((err) -> Observable.empty());
        })
        .subscribe(System.out::println, err -> System.err.println(
            "Shouldn't see this"), () -> System.out.println("Finished"));
  }

  private Observable<Employee> errorOn60000() {
    Observable<Employee> emps = itDept.getEmployees();
    Observable<Employee> empsWithErrors = emps.doOnNext(emp -> {
      if (emp.getSalary()
             .blockingFirst() > 60000) {
        throw new RuntimeException("Salary over 60000");
      }
    });

    return empsWithErrors;
  }

  private Observable<Observable<Employee>> errorAsObservable() {
    Observable<Employee> emps = itDept.getEmployees();
    Observable<Observable<Employee>> empsWithErrors = emps.map(emp -> {
      if (emp.getSalary()
             .blockingFirst() > 60000) {
        return Observable.error(new RuntimeException("Salary over 60000"));
      }
      return Observable.just(emp);
    });

    return empsWithErrors;
  }

  @Test
  public void testGetEmployees() {
    itDept.getEmployees()
          .test()
          .assertValueCount(6);
  }

  @Test
  public void testGetManagers() {
    itDept.getManagers()
          .test()
          .assertValues(mgr1, mgr2);
  }
}
