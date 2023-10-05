package rxjava.lab01;

import java.util.Objects;

public class EmployeeChange {
  private Employee emp;
  private EmployeeChangeType change;

  public EmployeeChange(Employee emp, EmployeeChangeType change) {
    this.emp = emp;
    this.change = change;
  }

  public Employee getEmployee() {
    return emp;
  }

  public EmployeeChangeType getChange() {
    return change;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeChange that = (EmployeeChange) o;
    return emp.equals(that.emp) && getChange() == that.getChange();
  }

  @Override
  public int hashCode() {
    return Objects.hash(emp, getChange());
  }
}
