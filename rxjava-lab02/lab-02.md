# Lab 02

## Part 1

All of the below in `Manager.java`.

1. Create an observable that prints only the first two employees under each of the managers. 
2. Concatenate all the employees together into one observable by getting them from their respective managers.
3. Print employees while their salaries are less than 70000. No sorting, as soon as you hit an employee with a
   salary >= 70000, stop
4. Sort the employees by their salary, then repeat the above. You can sort the employees 
before returning them as an Observable (that is, in Manager) or after, your choice.
5. Using the unsorted Observable of employees, filter for employees making 70000 or more. 

## Part 2

Create a class `Department`, which has a name (`String`) and an `Collection` (or subtype) of `Manager`s. The 
constructor takes only the name of the Department. Add an `addManager` method to add Managers to the Department.
Create `getManagers` to return the managers as an Observable. Add a `getEmployees` that uses 
`flatMap` to go over the Managers and return an Observable of all current Employees. 

1. In `DepartmentTest.java`, write a test to ensure that you can get the complete list of Managers
2. Still in `DepartmentTest.java`, write a test to ensure that you can get the complete list of Employees

## Part 3

1. Add a test to `ManagerTest.java` which fetches the employees. From the `Employee` `Observable`, 
   create a new Observable which throws an error if an Employee's salary is greater than 60000. 
2. Using the code from 1, above, have the new Observable complete if an error is thrown
3. Using the code from 1, above, have the Observable return a dummy employee instead of
   an Exception
4. Using the code from 1, above, have the Observable skip over the exception
   (Yes, filtering would be more efficient here, but we're testing errors!)

## Part 4

1. Write a `getTotalSalaries` method for `Manager`s. Test it.
2. Write a proxy/delegate `getTotalSalaries` for `Department`s. Test it.
3. Write a `getAverageSalary` method for `Department`. Test it.
4. Write a `getSalaryBands` method for `Department`. It should return the 
   different salary bands used in the Department. Test it.
5. Write a `groupEmployeesBySalary` method for `Department`. It should return
   `Employee`s grouped by their salary. Test it. 
