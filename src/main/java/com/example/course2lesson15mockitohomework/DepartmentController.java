package com.example.course2lesson15mockitohomework;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    DepartmentService departmentService = new DepartmentService(10);

    @GetMapping()
    public String welcome() {
        return departmentService.welcome();
    }

    @GetMapping(path = "/exampleHiring")
    public HashMap<String, Employee> hiringImaginedPeople() {
        departmentService.exampleHiring();
        return departmentService.getEmployeeMap();
    }

    @GetMapping(path = "/{deptId}/add")
    public Employee add(@RequestParam(required = false, name = "firstName") String firstName,
                        @RequestParam(required = false, name = "lastName") String lastName,
                        @RequestParam(required = false, name = "salary") Integer salary,
                        @PathVariable Integer deptId) {
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
    public Map<Integer, List<Employee>> listByDepts() {
        return departmentService.getListByDepts();
    }

    @GetMapping(path = "/{deptId}/employees")
    public List<Employee> getDeptCrew(@PathVariable Integer deptId) {
        return departmentService.getDeptCrew(deptId);
    }

    @GetMapping(value = "/{deptId}/salary/sum")
    public Integer salarySum(@PathVariable Integer deptId) {
        return departmentService.deptSalarySum(deptId);
    }

    @GetMapping(value = "/{deptId}/salary/max")
    public Integer salaryMax(@PathVariable Integer deptId) {
        return departmentService.deptSalaryMax(deptId);
    }

    @GetMapping(value = "/{deptId}/salary/min")
    public Integer salaryMin(@PathVariable Integer deptId) {
        return departmentService.deptSalaryMin(deptId);
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

//    @GetMapping(path = "/employee")
//    public String employee() {
//        return departmentService.welcome();
//    }

    @GetMapping(path = "/departments")
    public String depts() {
        return departmentService.departments();
    }

}
