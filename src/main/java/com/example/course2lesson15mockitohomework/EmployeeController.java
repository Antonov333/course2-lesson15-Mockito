package com.example.course2lesson15mockitohomework;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    EmployeeService employeeService = new EmployeeService(3);

    @GetMapping()
    public String welcome() {
        return employeeService.welcome();
    }

    @GetMapping(path = "/add")
    public Employee add(@RequestParam(required = false, name = "firstName") String f,
                        @RequestParam(required = false, name = "lastName") String l) {
        try {
            return employeeService.addEmployee(f, l);
        } catch (EmployeeAlreadyAddedException alreadyAdded) {
            return new Employee(f, l, alreadyAdded.getMessage());
        } catch (EmployeeStorageIsFullException arrayIsFull) {
            return new Employee(f, l, arrayIsFull.getMessage());
        } catch (WrongNameFormatException wrongNameFormat) {
            return new Employee(f, l, wrongNameFormat.getMessage());
        } catch (Exception e) {
            return new Employee(f, l, e.getMessage());
        }

    }

    @GetMapping(path = "/remove")
    public Employee remove(
            @RequestParam(required = false, name = "firstName") String firstName,
            @RequestParam(required = false, name = "lastName") String lastName
    ) {
        try {
            return employeeService.removeEmployee(firstName, lastName);
        } catch (EmployeeNotFoundException employeeNotFoundException) {
            return new Employee(firstName, lastName, employeeNotFoundException.getMessage());
        } catch (EmployeeStorageIsFullException arrayIsFull) {
            return new Employee(firstName, lastName, arrayIsFull.getMessage());
        } catch (WrongNameFormatException wrongNameFormat) {
            return new Employee(firstName, lastName, wrongNameFormat.getMessage());
        }
    }


    @GetMapping(path = "/list")
    public HashMap<String, Employee> list() {
        return employeeService.getEmployeeList();
    }

    @GetMapping(path = "/find")
    public Employee find(
            @RequestParam(required = false, name = "firstName") String first,
            @RequestParam(required = false, name = "lastName") String last) {
        try {
            return employeeService.findEmployee(first, last);
        } catch (WrongNameFormatException wrongNameFormatException) {
            return new Employee(first, last, wrongNameFormatException.getMessage());
        } catch (EmployeeNotFoundException employeeNotFoundException) {
            return new Employee(first, last, employeeNotFoundException.getMessage());
        }
    }

    @GetMapping(path = "/employee")
    public String employee() {
        return employeeService.welcome();
    }

    @GetMapping(path = "/departments")
    public String depts() {
        return employeeService.departments();
    }

}
