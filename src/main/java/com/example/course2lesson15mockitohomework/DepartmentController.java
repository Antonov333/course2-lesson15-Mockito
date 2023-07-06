package com.example.course2lesson15mockitohomework;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    DepartmentService departmentService = new DepartmentService(3);

    @GetMapping()
    public String welcome() {
        return departmentService.welcome();
    }

    @GetMapping(value = "/{deptId}")
    public String myDeptId(@PathVariable int deptId) {
        return "deptId = " + deptId;
    }

    @GetMapping(value = "/{myId}/nextcommand")
    public String nextCommand(@PathVariable int myId) {
        return "nextCommand<br><br>myId =" + myId;
    }

    @GetMapping(path = "/add")
    public Employee add(@RequestParam(required = false, name = "firstName") String firstName,
                        @RequestParam(required = false, name = "lastName") String lastName,
                        @RequestParam(required = false, name = "salary") Integer salary,
                        @RequestParam(required = false, name = "deptId") Integer deptId) {
        try {
            return departmentService.addEmployee(firstName, lastName, salary, deptId);
        } catch (EmployeeAlreadyAddedException alreadyAdded) {
            return new Employee(firstName, lastName, alreadyAdded.getMessage(), null, null);
        } catch (EmployeeStorageIsFullException arrayIsFull) {
            return new Employee(firstName, lastName, arrayIsFull.getMessage(), null, null);
        } catch (WrongNameFormatException wrongNameFormat) {
            return new Employee(firstName, lastName, wrongNameFormat.getMessage(), null, null);
        } catch (Exception e) {
            return new Employee(firstName, lastName, e.getMessage(), null, null);
        }

    }

    @GetMapping(path = "/remove")
    public Employee remove(
            @RequestParam(required = false, name = "firstName") String firstName,
            @RequestParam(required = false, name = "lastName") String lastName
    ) {
        try {
            return departmentService.removeEmployee(firstName, lastName);
        } catch (EmployeeNotFoundException employeeNotFoundException) {
            return new Employee(firstName, lastName, employeeNotFoundException.getMessage(), null, null);
        } catch (EmployeeStorageIsFullException arrayIsFull) {
            return new Employee(firstName, lastName, arrayIsFull.getMessage(), null, null);
        } catch (WrongNameFormatException wrongNameFormat) {
            return new Employee(firstName, lastName, wrongNameFormat.getMessage(), null, null);
        }
    }


    @GetMapping(path = "/employees")
    public HashMap<String, Employee> list() {
        return departmentService.getEmployeeList();
    }

    @GetMapping(path = "/find")
    public Employee find(
            @RequestParam(required = false, name = "firstName") String first,
            @RequestParam(required = false, name = "lastName") String last) {
        try {
            return departmentService.findEmployee(first, last);
        } catch (WrongNameFormatException wrongNameFormatException) {
            return new Employee(first, last, wrongNameFormatException.getMessage(), null, null);
        } catch (EmployeeNotFoundException employeeNotFoundException) {
            return new Employee(first, last, employeeNotFoundException.getMessage(), null, null);
        }
    }

    @GetMapping(path = "/employee")
    public String employee() {
        return departmentService.welcome();
    }

    @GetMapping(path = "/departments")
    public String depts() {
        return departmentService.departments();
    }

}
