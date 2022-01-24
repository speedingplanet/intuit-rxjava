package rxjava.lab01;

import org.junit.Before;

public class EmployeeTest {
  Employee emp1, emp2, emp3;

  @Before
  public void setUp() {
    emp1 = new Employee("John", "Paxton", 50000);
    emp2 = new Employee("Angela", "Cortez", 65000);
    emp3 = new Employee("Jack", "Hawksmoor", 60000);
  }

}
