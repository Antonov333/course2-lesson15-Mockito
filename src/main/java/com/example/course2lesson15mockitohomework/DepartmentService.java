package com.example.course2lesson15mockitohomework;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DepartmentService {

    private final int maxPersonnelNumber;

    private final HashMap<String, Employee> employeeList;

    public DepartmentService() {
        employeeList = new HashMap<>();
        maxPersonnelNumber = 100;
    }

    public DepartmentService(int maxPersonnelNumber) {
        employeeList = new HashMap<>();
        this.maxPersonnelNumber = maxPersonnelNumber;
    }

    public int getMaxPersonnelNumber() {
        return maxPersonnelNumber;
    }

    public int getPersonnelNumber() {
        return employeeList.size();
    }

    public boolean findEmployeeBoolean(String firstname, String lastname) {
        if (employeeList.containsKey(Employee.createKey(firstname, lastname))) {
            return true;
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    public Employee findEmployee(String firstName, String lastName) {

        if (findEmployeeBoolean(firstName, lastName)) {
            return employeeList.get(Employee.createKey(firstName, lastName));
        } // findEmployeeBoolean never returns 'false' but throws NotFound exception instead

        return new Employee("-", "- ", " ... Looks like something went wrong ... ", -1, 0);

    }

    public Employee removeEmployee(String firstName, String lastname) {

        Employee employee = employeeList.remove(Employee.createKey(firstName, lastname));

        if (employee == null) {
            throw new EmployeeNotFoundException("... this person has is not hired yet");
        }
        return employee;
    }

    public String welcome() {
        return "<h2>Welcome to  Spring and Mockito homework for Course 2 Lesson 15 ))</h2><br><br>" +
                "<a href=\"http://localhost:8080/department/add/?firstName=John&lastName=Smith\"> Add employee John Smith </a> | " +
                "<a href=\"http://localhost:8080/department/remove/?firstName=John&lastName=Smith\"> Remove employee John Smith </a> | " +
                "<a href=\"http://localhost:8080/department/find/\"> Find employee </a> | " +
                "<a href=\"http://localhost:8080/department/employees\"> View employee list </a>";
    }

    public Employee addEmployee(String firstName, String lastName, Integer deptId, Integer salary) {

        CandidateCheck candidateCheck = new CandidateCheck(firstName, lastName, deptId, salary);

        if (candidateCheck.getCode() != 0) {
            throw new WrongNameFormatException(candidateCheck.getMessage());
        }

        if (getPersonnelNumber() >= getMaxPersonnelNumber()) {
            throw new EmployeeStorageIsFullException("Personnel array is full. No vacant position at the moment");
        }

        try {
            if (findEmployeeBoolean(firstName, lastName)) {
                throw new EmployeeAlreadyAddedException("already hired");
            }
        } catch (EmployeeNotFoundException employeeNotFound) {
            // it is OK if employee not found because we want to add him
        }

        Employee employee = new Employee(firstName, lastName, Status.hired, deptId, salary);

        employeeList.put(employee.getKey(), employee);

        return employee;
    }


    public HashMap<String, Employee> getEmployeeList() {
        return employeeList;
    }

    public String departments() {
        return "Under construction, sorry";
    }


}
