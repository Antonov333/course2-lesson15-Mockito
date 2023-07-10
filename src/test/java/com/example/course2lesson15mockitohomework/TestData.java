package com.example.course2lesson15mockitohomework;

import org.junit.jupiter.params.provider.Arguments;

import java.util.*;
import java.util.stream.Stream;

public class TestData {
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


}
