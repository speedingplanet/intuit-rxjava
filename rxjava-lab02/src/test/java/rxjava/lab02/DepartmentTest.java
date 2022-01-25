package rxjava.lab02;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DepartmentTest {
  Employee emp1, emp2, emp3, emp4, emp5, emp6, emp7;
  Manager mgr1, mgr2;
  List<Employee> mgr1Team, mgr2Team;
  Department itDepartment;

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

    itDepartment = new Department("IT");
    itDepartment.addManager(mgr1);
    itDepartment.addManager(mgr2);
  }

  @Test
  public void testLab2Part2_1() {
    Observable<Manager> managers = itDepartment.getManagers();
    TestObserver<Manager> observer = managers.test();
    observer.assertValueCount(2);
    observer.assertValues(mgr1, mgr2);
  }

  @Test
  public void testLab2Part2_2() {
    Observable<Employee> employees = itDepartment.getEmployees();
    TestObserver<Employee> observer = employees.test();
    observer.assertValueCount(mgr1Team.size() + mgr2Team.size());
    observer.assertResult(emp1, emp2, emp3, emp4, emp5, emp6);
  }
}
