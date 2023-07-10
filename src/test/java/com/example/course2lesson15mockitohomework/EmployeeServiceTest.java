package com.example.course2lesson15mockitohomework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    EmployeeService employeeService100 = new EmployeeService();

    @Test
    void getMaxPersonnelNumberTest() {
        assertEquals(100, employeeService100.getMaxPersonnelNumber());
    }

    @Test
    void getPersonnelNumberTest() {
        EmployeeService employeeService = new EmployeeService();
        assertEquals(0, employeeService.getPersonnelNumber());

        List<Employee> newEmployees = new ArrayList<>(TestData.exampleCrewRandomDeptAndSalary().values());

        newEmployees.stream().forEach(employee -> employeeService.addEmployee(employee.getFirstName(),
                employee.getLastName(), employee.getDeptId(), employee.getSalary()));

        System.out.println("employeeService.getEmployeeList() = " + employeeService.getEmployeeList());

        assertEquals(8, employeeService.getPersonnelNumber());

    }

    @Test
    void findEmployeeTest() {
        assertThrows(EmployeeNotFoundException.class, () -> employeeService100.findEmployeeBoolean(
                TestData.getStrangerName().getFirstName(),
                TestData.getStrangerName().getLastName()));

        employeeService100.addEmployee(TestData.getStrangerName().getFirstName(),
                TestData.getStrangerName().getLastName(), 3, 98765);


        assertEquals(new Employee(TestData.getStrangerName().getFirstName(),
                        TestData.getStrangerName().getLastName(),
                        Status.hired, 3, 98765),
                employeeService100.findEmployee(TestData.getStrangerName().getFirstName(),
                        TestData.getStrangerName().getLastName()));

        employeeService100.removeEmployee(TestData.getStrangerName().getFirstName(),
                TestData.getStrangerName().getLastName());

    }

    @Test
    void removeEmployeeTest() {
        EmployeeService es = new EmployeeService();
        assertThrows(EmployeeNotFoundException.class, () -> es.findEmployee(TestData.getStrangerName().getFirstName(),
                TestData.getStrangerName().getLastName()));

        PersonName n = TestData.getExampleName(0);
        es.addEmployee(n.getFirstName(), n.getLastName(), 3, 98765);
        Employee expected = new Employee(n.getFirstName(), n.getLastName(), Status.hired, 3, 98765);

        assertEquals(expected, es.findEmployee(n.getFirstName(), n.getLastName()));
    }

    @Test
    void addEmployeeTest() {
        EmployeeService es = new EmployeeService();

        PersonName n = TestData.getExampleName(0);
        es.addEmployee(n.getFirstName(), n.getLastName(), 3, 98765);

        assertTrue(es.findEmployeeBoolean(n.getFirstName(), n.getLastName()));

        assertThrows(EmployeeAlreadyAddedException.class, () -> es.addEmployee(
                n.getFirstName(), n.getLastName(), 3, 98765));

        es.setMaxPersonnelNumber(1);
        assertThrows(EmployeeStorageIsFullException.class, () -> es.addEmployee(
                TestData.getStrangerName().getFirstName(), TestData.getStrangerName().getLastName(),
                4, 56789)
        );

    }

    @Test
    void getEmployeeListTest() {
        HashMap<String, Employee> testCrew = TestData.exampleCrewRandomDeptAndSalary();

        EmployeeService es = new EmployeeService();

        assertEquals(0, es.getEmployeeList().size());

        List<Employee> testList = new ArrayList<>(testCrew.values());

        testList.stream().forEach(employee -> es.addEmployee(employee.getFirstName(),
                employee.getLastName(), employee.getDeptId(), employee.getSalary()));

        assertEquals(testCrew, es.getEmployeeList());

    }

    @DisplayName("Parameterized test for setMaxPersonnelNumber(String firstName, String Lastname) method with stream")
    @ParameterizedTest(name = "maxPersNum")
    @MethodSource("maxPersNumProvider")
    void setMaxPersonnelNumberTest(Integer maxPersNum) {
        EmployeeService es = new EmployeeService();
        es.setMaxPersonnelNumber(maxPersNum.intValue());
        assertEquals(maxPersNum.intValue(), es.getMaxPersonnelNumber());
    }


    static Stream<Arguments> maxPersNumProvider() {
        Random r = new Random();
        return Stream.of(
                Arguments.of(r.nextInt(5, 50)),
                Arguments.of(r.nextInt(5, 50)),
                Arguments.of(r.nextInt(5, 50)))
                ;
    }
}



