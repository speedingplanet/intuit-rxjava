# Lab 02

## Part 1

Create an observable that prints only the first two employees under each of the managers.

Concatenate all the employees together into one observable by getting them from their respective managers.

Print employees while their salaries are less than 70000. 

Sort the employees by their salary, then repeat the above. You can sort the employees 
before returning them as an Observable (that is, in Manager) or after, your choice.

Using the unsorted Observable of employees, filter for employees making 70000 or more. 

## Part 2

Create a class Department, which has a name (String) and an Observable of Managers. The 
constructor takes only the name of the Department. Add an `addManager` method to add Managers to the Department.
Create `getManagers` to return the managers as an Observable. Add a `getEmployees` that uses 
`flatMap` to go over the Managers and return an Observable of all current Employees. 

Write two tests to ensure that you can get the complete lists of both Managers and Employees. 

## Part 3

Add code to `Manager.getEmployees` to complete the returned Observable if there are any errors 
fetching the Employees. Test it.

If the internal storage of Managers in Department is a Collection, would it be possible to 
recover from an error in `getEmployees` by moving on to the next Manager in the Collection?


