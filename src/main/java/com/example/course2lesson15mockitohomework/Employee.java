package com.example.course2lesson15mockitohomework;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class Employee implements Comparable<Employee> {
    private final String firstName;
    private final String lastName;
    private final int salary;
    private final int deptId;
    private String status;

    public String toString() {
        return firstName + " " + lastName + " " + status + " DeptNo: " + deptId + " Salary: " + salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee(String firstName, String lastName, String status, Integer deptId, Integer salary) {
        this.firstName = StringUtils.capitalize(firstName);
        this.lastName = StringUtils.capitalize(lastName);
        this.status = status;
        this.deptId = deptId;
        this.salary = salary;
    }

    public String getKey() {
        return createKey(firstName, lastName);
    }

    public int getSalary() {
        return salary;
    }

    public int getDeptId() {
        return deptId;
    }

    public boolean isDeptId(int deptId) {
        return (deptId == this.deptId);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!other.getClass().equals(this.getClass())) {
            return false;
        }
        return Objects.equals(firstName, ((Employee) other).getFirstName())
                & Objects.equals(lastName, ((Employee) other).getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public int compareTo(Employee o) {
        Integer thisDeptId = Integer.valueOf(deptId);
        Integer otherDeptId = Integer.valueOf(o.getDeptId());
        return thisDeptId.compareTo(otherDeptId);
    }

    public static String createKey(String firstName, String lastName) {
        return firstName + lastName;
    }
}
