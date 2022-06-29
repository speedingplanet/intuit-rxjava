package rxjava.lab01.solutions;

import io.reactivex.rxjava3.core.Observable;

import java.util.Objects;
import java.util.StringJoiner;

public class SolutionEmployee {
  private final String firstName;
  private final String lastName;
  private final Integer salary;

  public SolutionEmployee(String firstName, String lastName, Integer salary) {
    Objects.requireNonNull(firstName, "First Name is null");
    Objects.requireNonNull(lastName, "Last Name is null");
    Objects.requireNonNull(salary, "Salary is null");
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
  }

  // public String getFirstName() {
  //   return firstName;
  // }

  public Observable<String> getFirstName() {
    return Observable.just(firstName);
  }

  public  Observable<String> getLastName() {
    return Observable.just(lastName);
  }

  public  Observable<Integer> getSalary() {
    return Observable.just(salary);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SolutionEmployee employee = (SolutionEmployee) o;
    return firstName.equals(employee.firstName) &&
      lastName.equals(employee.lastName) &&
      salary.equals(employee.salary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, salary);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SolutionEmployee.class.getSimpleName() + "[", "]")
      .add("firstName='" + firstName + "'")
      .add("lastName='" + lastName + "'")
      .add("salary=" + salary)
      .toString();
  }
}

