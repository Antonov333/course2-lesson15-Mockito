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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    //    private final EmployeeService employeeServiceMock = mock(EmployeeService.class);
//    private DepartmentService departmentService = new DepartmentService(employeeServiceMock);
    @Mock
    EmployeeService employeeServiceMock;

    @InjectMocks
    DepartmentService departmentService;


    public static HashMap<String, Employee> exampleCrew() {

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

    private static Stream<Arguments> namesProvider() {

        List<PersonName> personNames = getExampleNameList();

//        Stream s  = personNames.stream(personName -> Arguments.of(personName));

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


//        personNames.forEach(personName -> Arguments.of(personName.getFirstName(),personName.getLastName()));

 /*       return Stream.of(
                Arguments.of(getExampleNameList().get(0)),
                Arguments.of(getExampleNameList().get(1)),
                Arguments.of(getExampleNameList().get(2)),
                Arguments.of(getExampleNameList().get(3)),
                Arguments.of(getExampleNameList().get(4)),
                Arguments.of(getExampleNameList().get(5)),
                Arguments.of(getExampleNameList().get(6)),
                Arguments.of(getExampleNameList().get(7))
        );

    }

    /*
     * Необходимо покрыть тестами все операции по работе с отделами из DepartmentService.
     * Нужно «замокать» EmployeeService, который возвращает список сотрудников, а уже затем покрыть
     * все методы сервиса тестами с максимальным количеством придуманных ситуаций.
     * Например, когда переданный отдел отсутствует или вообще не передан пользователем.
     *  */


    }
}
