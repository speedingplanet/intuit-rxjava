package rxjava.lab02;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.List;

public class Department {
  private String name;
  private List<Manager> managers;

  public Department(String name) {
    this.name = name;
    this.managers = new ArrayList<>();
  }

  public boolean addManager(Manager manager) {
    if (! managers.contains(manager)) {
      managers.add(manager);
      return true;
    }
    return false;
  }

  public Observable<Manager> getManagers() {
    return Observable.fromIterable(managers);
  }

  public Observable<Employee> getEmployees() {
    return getManagers().flatMap(Manager::getTeam);
  }
}
