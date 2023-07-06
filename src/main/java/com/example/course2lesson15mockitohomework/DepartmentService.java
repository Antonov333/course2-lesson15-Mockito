package com.example.course2lesson15mockitohomework;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final int maxPersonnelNumber;

    private final HashMap<String, Employee> employeeMap;

    public DepartmentService() {
        employeeMap = new HashMap<>();
        maxPersonnelNumber = 100;
    }

    public DepartmentService(int maxPersonnelNumber) {
        employeeMap = new HashMap<>();
        this.maxPersonnelNumber = maxPersonnelNumber;
    }

    public int getMaxPersonnelNumber() {
        return maxPersonnelNumber;
    }

    public int getPersonnelNumber() {
        return employeeMap.size();
    }

    public boolean findEmployeeBoolean(String firstname, String lastname) {
        if (employeeMap.containsKey(Employee.createKey(firstname, lastname))) {
            return true;
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    public Employee findEmployee(String firstName, String lastName) {

        if (findEmployeeBoolean(firstName, lastName)) {
            return employeeMap.get(Employee.createKey(firstName, lastName));
        } // findEmployeeBoolean never returns 'false' but throws NotFound exception instead

        return new Employee("-", "- ", " ... Looks like something went wrong ... ", -1, 0);

    }

    public Employee removeEmployee(String firstName, String lastname) {

        Employee employee = employeeMap.remove(Employee.createKey(firstName, lastname));

        if (employee == null) {
            throw new EmployeeNotFoundException("... this person has is not hired yet");
        }
        return employee;
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

        employeeMap.put(employee.getKey(), employee);

        return employee;
    }


    public HashMap<String, Employee> getEmployeeMap() {
        return employeeMap;
    }

    public String departments() {
        return "Under construction, sorry";
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
        return employeeMap;

    }

    public List<Employee> getDeptCrew(Integer dept) {
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
