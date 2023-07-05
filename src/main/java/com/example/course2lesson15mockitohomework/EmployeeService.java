package com.example.course2lesson15mockitohomework;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EmployeeService {

    private final int maxPersonnelNumber;

    private final HashMap<String, Employee> employeeList;

    public EmployeeService() {
        employeeList = new HashMap<>();
        maxPersonnelNumber = 100;
    }

    public EmployeeService(int maxPersonnelNumber) {
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
        if (employeeList.containsKey(firstname + lastname)) {
            return true;
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    public Employee findEmployee(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName, "error");

        NameCheck nameCheck = new NameCheck(firstName, lastName);

        if (!nameCheck.isOk()) {
            throw new WrongNameFormatException(nameCheck.getMessage());
        }

        if (findEmployeeBoolean(firstName, lastName)) {
            return new Employee(firstName, lastName, "enrolled");
        } // findEmployeeBoolean never returns 'false' but throws NotFound exception instead

        return new Employee("-", "- ", "... Looks like something went wrong");

    }

    public Employee removeEmployee(String firstName, String lastname) {
        Employee employee = new Employee(firstName, lastname, "removed");
        NameCheck nameCheck = new NameCheck(firstName, lastname);

        if (nameCheck.getCode() != 0) {
            throw new WrongNameFormatException(nameCheck.getMessage());
        }

        if (employeeList.remove(employee.getKey(), employee)) {
            return employee;
        } else {
            throw new EmployeeNotFoundException("... this person has is not hired yet");
        }
    }

    public String welcome() {
        return "<h2>Welcome to homework Sets for Course 2 Lesson 8 ))</h2><br><br>" +
                "<a href=\"http://localhost:8080/employee/add/?firstName=John&lastName=Smith\"> Add employee John Smith </a> | " +
                "<a href=\"http://localhost:8080/employee/remove/?firstName=John&lastName=Smith\"> Remove employee John Smith </a> | " +
                "<a href=\"http://localhost:8080/employee/find/\"> Find employee </a> | " +
                "<a href=\"http://localhost:8080/employee/list\"> View employee list </a>";
    }

    public Employee addEmployee(String firstName, String lastName) {

        NameCheck nameCheck = new NameCheck(firstName, lastName);

        if (nameCheck.getCode() != 0) {
            throw new WrongNameFormatException(nameCheck.getMessage());
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

        Employee employee = new Employee(firstName, lastName, "enrolled");

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
