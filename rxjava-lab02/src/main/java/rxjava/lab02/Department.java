package rxjava.lab02;

import io.reactivex.rxjava3.core.Observable;

import java.util.HashSet;
import java.util.Set;

public class Department {
  private String name;
  private Set<Manager> managers;

  public Department(String name) {
    this.name = name;
    managers = new HashSet<>();
  }

  public boolean addManager(Manager manager) {
    return managers.add(manager);
  }

  public Observable<Manager> getManagers() {
    return Observable.fromIterable(managers);
  }

  public Observable<Employee> getEmployees() {
    return getManagers().flatMap(Manager::getTeam);
  }

  @Override
  public String toString() {
    return "Department{" +
      "name='" + name + '\'' +
      '}';
  }
}
