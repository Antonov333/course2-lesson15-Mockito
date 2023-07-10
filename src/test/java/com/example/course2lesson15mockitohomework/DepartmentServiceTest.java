package com.example.course2lesson15mockitohomework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    EmployeeService employeeServiceMock;

    @InjectMocks
    DepartmentService departmentService;

    public static HashMap<String, Employee> exampleCrewRandomDeptAndSalary() {

        HashMap<String, Employee> crew = new HashMap<>();
        List<PersonName> personNameList = getExampleNameList();
        Random r = new Random();
        String status = Status.hired;

        List<Employee> list = new ArrayList<>();

        personNameList.forEach(personName -> list.add(new Employee(personName.getFirstName(), personName.getLastName(), status,
                r.nextInt(1, 3), r.nextInt(50000, 150000))));

        list.forEach(employee -> crew.put(Employee.createKey(employee.getFirstName(), employee.getLastName()), employee));

        return crew;
    }

    public static HashMap<String, Employee> exampleCrew() {

        HashMap<String, Employee> crew = new HashMap<>();
        List<PersonName> nameList = getExampleNameList();
        String status = Status.hired;

        List<Employee> list = new ArrayList<Employee>(Arrays.asList(
                new Employee(nameList.get(0).getFirstName(), nameList.get(0).getLastName(), status, 1, 99999),
                new Employee(nameList.get(1).getFirstName(), nameList.get(1).getLastName(), status, 1, 99999),
                new Employee(nameList.get(2).getFirstName(), nameList.get(2).getLastName(), status, 1, 99999),
                new Employee(nameList.get(3).getFirstName(), nameList.get(3).getLastName(), status, 5, 99999),
                new Employee(nameList.get(4).getFirstName(), nameList.get(4).getLastName(), status, 5, 99999),
                new Employee(nameList.get(5).getFirstName(), nameList.get(5).getLastName(), status, 5, 99999),
                new Employee(nameList.get(6).getFirstName(), nameList.get(6).getLastName(), status, 3, 99999),
                new Employee(nameList.get(7).getFirstName(), nameList.get(7).getLastName(), status, 3, 99999)));

        list.forEach(employee -> crew.put(Employee.createKey(employee.getFirstName(), employee.getLastName()), employee));

        return crew;
    }

    static PersonName getStrangerName() {
        return new PersonName("Cesar", "Vialpando");
    }

    static PersonName getExampleName(int number) {
        return getExampleNameList().get(number);
    }

    static List<PersonName> getExampleNameList() {
        List<PersonName> personNames = new ArrayList<>(Arrays.asList(
                new PersonName("Albert", "Dean"),
                new PersonName("John", "Smith"),
                new PersonName("Till", "Shweiger"),
                new PersonName("Ann", "Brown"),
                new PersonName("Ron", "Grownship"),
                new PersonName("James", "Alarmson"),
                new PersonName("Pamela", "Anderson"),
                new PersonName("Tommy", "Lee")));

        return personNames;
    }

    private static Stream<Arguments> namesProvider() {

        List<PersonName> personNames = getExampleNameList();
        
        return Stream.of(
                Arguments.of(getExampleNameList().get(0).getFirstName(), getExampleNameList().get(0).getLastName()),
                Arguments.of(getExampleNameList().get(1).getFirstName(), getExampleNameList().get(1).getLastName()),
                Arguments.of(getExampleNameList().get(2).getFirstName(), getExampleNameList().get(2).getLastName()),
                Arguments.of(getExampleNameList().get(3).getFirstName(), getExampleNameList().get(3).getLastName()),
                Arguments.of(getExampleNameList().get(4).getFirstName(), getExampleNameList().get(4).getLastName()),
                Arguments.of(getExampleNameList().get(5).getFirstName(), getExampleNameList().get(5).getLastName()),
                Arguments.of(getExampleNameList().get(6).getFirstName(), getExampleNameList().get(6).getLastName()),
                Arguments.of(getExampleNameList().get(7).getFirstName(), getExampleNameList().get(7).getLastName())
        );


    }

    private static Stream<Arguments> namesProvider2() {
        List<PersonName> personNames = getExampleNameList();
        return personNames.stream().map(personName -> Arguments.of(personName));
    }

    @Test
    public void someTestClassCheck() {
        System.out.println("getStrangerName() = " + getStrangerName().toString());
        System.out.println("getExampleNameList() = " + getExampleNameList());
        System.out.println("exampleCrew() = " + exampleCrew());
    }

    @Test
    public void getMaxPersonnelNumberTest() {
        when(employeeServiceMock.getMaxPersonnelNumber()).thenReturn(15);
        assertEquals(15, departmentService.getMaxPersonnelNumber());
    }

    @Test
    public void getEmployeeMapTest() {
        when(employeeServiceMock.getEmployeeList()).thenReturn(exampleCrew());
        assertEquals(exampleCrew(), departmentService.getEmployeeMap()); // make sure list of employees is correct

        HashMap<String, Employee> emptyCrewMap = new HashMap<>();
        assertEquals(0, emptyCrewMap.size());

        when(employeeServiceMock.getEmployeeList()).thenReturn(null);
        assertNull(departmentService.getEmployeeMap()); // make sure it works when employee list is not even initialized
    }

    @Test
    public void getPersonnelNumberTest() {
        Random r = new Random();
        int expected = r.nextInt(100);
        when(employeeServiceMock.getPersonnelNumber()).thenReturn(expected);
        assertEquals(expected, departmentService.getPersonnelNumber());
    }

    @Test
    public void findEmployeeBooleanTest() {

    }

    @DisplayName("Parameterized test for findEmployeeBooleanTest(String firstName, String Lastname) method with stream")
    @ParameterizedTest(name = "{index} => firstName={0}, lastName={1}")
    @MethodSource("namesProvider")
    public void findEmployeeBooleanTest(String firstName, String lastName) {
        when(employeeServiceMock.getEmployeeList()).thenReturn(exampleCrew());
        assertTrue(departmentService.findEmployeeBoolean(firstName, lastName)); // each employee findable

        assertThrows(EmployeeNotFoundException.class, () -> departmentService.findEmployeeBoolean(
                getStrangerName().getFirstName(), getStrangerName().getLastName())); // absent person name causes exceptioin

    }

    @DisplayName("Parameterized test for findEmployeeBooleanTest(String firstName, String Lastname) method with stream")
    @ParameterizedTest(name = "personName")
    @MethodSource("namesProvider2")
    public void findEmployeeBooleanTest2(PersonName personName) {
        when(employeeServiceMock.getEmployeeList()).thenReturn(exampleCrew());
        assertTrue(departmentService.findEmployeeBoolean(personName.getFirstName(), personName.getLastName())); // each employee findable

        assertThrows(EmployeeNotFoundException.class, () -> departmentService.findEmployeeBoolean(
                getStrangerName().getFirstName(), getStrangerName().getLastName())); // absent person name causes exceptioin

    }

    @Test
    void addEmployeeTest() {
        String firstName = getStrangerName().getFirstName();
        String lastName = getStrangerName().getLastName();
        String key = Employee.createKey(firstName, lastName);
        HashMap<String, Employee> testCrew = exampleCrew();

        when(employeeServiceMock.getEmployeeList()).thenReturn(testCrew); // make sure new person is not found on list of employees
        assertThrows(RuntimeException.class, () -> departmentService.findEmployee(firstName, lastName));

        testCrew.put(key, new Employee(firstName, lastName, Status.hired, 3, 99555));
        assertEquals(new Employee(firstName, lastName, Status.hired, 3, 99500),
                departmentService.findEmployee(firstName, lastName));

    }


    @Test
    void addEmployeeTestNice() {
        String firstName = getStrangerName().getFirstName();
        String lastName = getStrangerName().getLastName();
        Integer dept = 3;
        Integer salary = 99500;
        when(employeeServiceMock.addEmployee(getStrangerName().getFirstName(),
                getStrangerName().getLastName(), 3, 99500)).
                thenReturn(new Employee(firstName, lastName, Status.hired, dept, salary));
        Employee expected = new Employee(firstName, lastName, Status.hired, dept, salary);
        assertEquals(expected, departmentService.addEmployee(firstName, lastName, dept, salary));

        String key = Employee.createKey("John", "Smith");
        HashMap<String, Employee> crew = exampleCrew();
        Employee alreadyHiredPerson = crew.get(key);

        when(employeeServiceMock.addEmployee(alreadyHiredPerson.getFirstName(),
                alreadyHiredPerson.getLastName(),
                alreadyHiredPerson.getDeptId(),
                alreadyHiredPerson.getSalary())).thenThrow(EmployeeAlreadyAddedException.class);

        assertThrows(EmployeeAlreadyAddedException.class, () -> departmentService.addEmployee(
                alreadyHiredPerson.getFirstName(),
                alreadyHiredPerson.getLastName(),
                alreadyHiredPerson.getDeptId(),
                alreadyHiredPerson.getSalary()));

        when(employeeServiceMock.addEmployee("Absolute", "New", dept, salary)).
                thenThrow(EmployeeStorageIsFullException.class);

        assertThrows(EmployeeStorageIsFullException.class, () -> departmentService.addEmployee(
                "Absolute", "New", dept, salary));
    }

    @Test
    void getDeptCrewTest() {

        when(employeeServiceMock.getEmployeeList()).thenReturn(exampleCrew());
        assertThrows(DeptIdInvalidException.class, () -> departmentService.getDeptCrew(null));
        assertThrows(DeptIdInvalidException.class, () -> departmentService.getDeptCrew(11));

        List<Employee> allCrew = exampleCrew().values().stream().toList();
        Set<Employee> expectedDept1Set = allCrew.stream().filter(employee -> employee.isDeptId(1)).collect(Collectors.toSet());
        Set<Employee> expectedDept3Set = allCrew.stream().filter(employee -> employee.isDeptId(3)).collect(Collectors.toSet());
        Set<Employee> expectedDept5Set = allCrew.stream().filter(employee -> employee.isDeptId(5)).collect(Collectors.toSet());

        Set<Employee> actualDept1Set = departmentService.getDeptCrew(1).stream().collect(Collectors.toSet());
        Set<Employee> actualDept5Set = departmentService.getDeptCrew(5).stream().collect(Collectors.toSet());
        Set<Employee> actualDept3Set = departmentService.getDeptCrew(3).stream().collect(Collectors.toSet());

        assertEquals(expectedDept1Set, actualDept1Set);
        assertEquals(expectedDept3Set, actualDept3Set);
        assertEquals(expectedDept5Set, actualDept5Set);
    }

    @Test
    void deptSalarySumTest() {
        HashMap<String, Employee> testCrew = exampleCrewRandomDeptAndSalary();
        Set<Integer> deptSet = testCrew.values().stream().map(employee -> employee.getDeptId()).collect(Collectors.toSet());

        when(employeeServiceMock.getEmployeeList()).thenReturn(testCrew);
        for (Integer i : deptSet
        ) {
            Integer expected = 0;
            List<Employee> deptCrew = departmentService.getDeptCrew(i);
            for (Employee e : deptCrew
            ) {
                expected += e.getSalary();
            }
            assertEquals(expected, departmentService.deptSalarySum(i));
        } // made sure all department salaries counted correctly

        assertThrows(DeptIdInvalidException.class, () -> departmentService.deptSalarySum(null));
        //made sure exception thrown in case of null deptId

        assertThrows(DeptIdInvalidException.class, () -> departmentService.deptSalarySum(0));
        // made sure exception thrown in case of non-existing department
    }

    @Test
    void deptSalaryMaxTest() {
        HashMap<String, Employee> testCrew = exampleCrewRandomDeptAndSalary();
        Set<Integer> deptSet = testCrew.values().stream().map(employee -> employee.getDeptId()).collect(Collectors.toSet());

        when(employeeServiceMock.getEmployeeList()).thenReturn(testCrew);
        for (Integer i : deptSet
        ) {
            Integer expected = 0;
            List<Employee> deptCrew = departmentService.getDeptCrew(i);
            deptCrew.sort(Comparator.comparingInt(employee -> employee.getSalary()));
            expected = deptCrew.get(deptCrew.size() - 1).getSalary();
            assertEquals(expected, departmentService.deptSalaryMax(i));
        } // made sure all department salaries counted correctly

        assertThrows(DeptIdInvalidException.class, () -> departmentService.deptSalaryMax(null));
        //made sure exception thrown in case of null deptId

        assertThrows(DeptIdInvalidException.class, () -> departmentService.deptSalaryMax(0));
        // made sure exception thrown in case of non-existing department
    }

    @Test
    void deptSalaryMinTest() {
        HashMap<String, Employee> testCrew = exampleCrewRandomDeptAndSalary();
        Set<Integer> deptSet = testCrew.values().stream().map(employee -> employee.getDeptId()).collect(Collectors.toSet());

        when(employeeServiceMock.getEmployeeList()).thenReturn(testCrew);
        for (Integer i : deptSet
        ) {
            Integer expected = 0;
            List<Employee> deptCrew = departmentService.getDeptCrew(i);
            deptCrew.sort(Comparator.comparingInt(employee -> employee.getSalary()));
            expected = deptCrew.get(0).getSalary();
            assertEquals(expected, departmentService.deptSalaryMin(i));
        } // made sure all department salaries counted correctly

        assertThrows(DeptIdInvalidException.class, () -> departmentService.deptSalaryMin(null));
        //made sure exception thrown in case of null deptId

        assertThrows(DeptIdInvalidException.class, () -> departmentService.deptSalaryMin(0));
        // made sure exception thrown in case of non-existing department
    }
}