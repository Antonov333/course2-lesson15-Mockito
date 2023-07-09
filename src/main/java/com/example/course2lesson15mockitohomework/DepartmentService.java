package com.example.course2lesson15mockitohomework;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private EmployeeService employeeService;


    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public int getMaxPersonnelNumber() { // covered by test
        return employeeService.getMaxPersonnelNumber();
    }

    public int getPersonnelNumber() { // covered by test
        return employeeService.getPersonnelNumber();
    }

    public boolean findEmployeeBoolean(String firstname, String lastname) { // covered by test

        if (employeeService.getEmployeeList().containsKey(Employee.createKey(firstname, lastname))) {
            return true;
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    public Employee findEmployee(String firstName, String lastName) {
        return employeeService.findEmployee(firstName, lastName);
    }

    public Employee removeEmployee(String firstName, String lastname) {
        return employeeService.removeEmployee(firstName, lastname);
    }

    public String welcome() {
        Random r = new Random();
        return "<h2>Welcome to  Spring and Mockito homework for Course 2 Lesson 15 ))</h2><br><br>" +
                "<a href=\"http://localhost:8080/department/1/add/?firstName=John&lastName=Smith&salary=" +
                r.nextInt(50000, 150000) + "\"> Add employee John Smith </a> | " +
                "<a href=\"http://localhost:8080/department/remove/?firstName=John&lastName=Smith\"> Remove employee John Smith </a> | " +
                "<a href=\"http://localhost:8080/department/find/\"> Find employee </a> | " +
                "<a href=\"http://localhost:8080/department/employees\"> View employee list </a>";
    }

    public Employee addEmployee(String firstName, String lastName, Integer deptId, Integer salary) {

        Employee employee = employeeService.addEmployee(firstName, lastName, deptId, salary);

        return employee;
    }


    public HashMap<String, Employee> getEmployeeMap() {
        HashMap<String, Employee> employeeMap = employeeService.getEmployeeList();
        return employeeMap;
    }

    public HashMap<String, Employee> exampleHiring() {
        Random r = new Random();
        addEmployee("John", "Smith", 5, r.nextInt(50000, 100000));

        addEmployee("Till", "Shweiger", 5, r.nextInt(50000, 100000));
        addEmployee("Ann", "Brown", 2, r.nextInt(50000, 100000));
        addEmployee("Ron", "Grownship", 2, r.nextInt(50000, 100000));
        addEmployee("James", "Alarmson", 2, r.nextInt(50000, 100000));
        addEmployee("Pamela", "Anderson", 5, r.nextInt(50000, 100000));
        addEmployee("Tommy", "Lee", 5, r.nextInt(50000, 100000));
        return getEmployeeMap();

    }

    public List<Employee> getDeptCrew(Integer dept) {
        HashMap<String, Employee> employeeMap = employeeService.getEmployeeList();
        List<Employee> allCrew = new ArrayList<Employee>(employeeMap.values());


        if (dept == null) {
            List<Employee> crewSortedByDept;
            crewSortedByDept = allCrew.stream().sorted().collect(Collectors.toList());
            return crewSortedByDept;
        }
        List<Employee> deptCrew = allCrew.stream().filter(employee -> employee.isDeptId(dept)).
                collect(Collectors.toList());
        return deptCrew;
    }

    public Integer deptSalarySum(Integer deptId) {
        List<Employee> deptCrew = getDeptCrew(deptId);
        Integer sum = 0;
        for (Employee e : deptCrew) {
            sum += e.getSalary();
        }
        return sum;
    }

    public Integer deptSalaryMax(Integer deptId) {
        List<Employee> deptCrew = getDeptCrew(deptId);
        if (deptCrew == null) {
            return 0;
        }
        deptCrew.sort(Comparator.comparingInt(employee -> employee.getSalary()));
        return deptCrew.get(deptCrew.size() - 1).getSalary();
    }

    public Integer deptSalaryMin(Integer deptId) {

        List<Employee> deptCrewResult = getDeptCrew(deptId);

        if (deptCrewResult == null) {
            return 0;
        }

        deptCrewResult.sort(Comparator.comparingInt(employee -> employee.getSalary()));

        return deptCrewResult.get(0).getSalary();

    }

    public Map<Integer, List<Employee>> getListByDepts() {
        HashMap<String, Employee> employeeMap = employeeService.getEmployeeList();
        List<Employee> crewAll = new ArrayList<Employee>(employeeMap.values());
        List<Integer> deptList = new ArrayList<>();
        Map<Integer, List<Employee>> crewByDepts = new HashMap<>();

        Set<Integer> deptSet = new HashSet<>();

        crewAll.forEach(employee -> deptSet.add(employee.getDeptId()));

        deptList.addAll(deptSet);

        Collections.sort(deptList);

        deptList.forEach(depId -> crewByDepts.put(depId, getDeptCrew(depId)));

        return crewByDepts;
    }

}
