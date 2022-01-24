package rxjava.lab02;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EmployeeTest {
  Employee emp1, emp2, emp3;

  @Before
  public void setUp() {
    emp1 = new Employee("John", "Paxton", 50000);
    emp2 = new Employee("Angela", "Cortez", 65000);
    emp3 = new Employee("Jack", "Hawksmoor", 60000);
  }

  @Test
  public void testGetFirstName() {
    emp1.getFirstName().subscribe(firstName -> {
      assertEquals(firstName, "John");
    });
  }

  @Test
  public void betterTestGetFirstName() {
    List<String> data = new ArrayList<>();
    emp1.getFirstName().subscribe(data::add);
    assertEquals("John", data.get(0));
  }

}
