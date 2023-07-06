package com.example.course2lesson15mockitohomework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    EmployeeService employeeServiceMock;

    @Test
    public void DepartmentServiceTest() {
        System.out.println("employeeServiceMock.getEmployeeList() = " + employeeServiceMock.getEmployeeList());
        return;
    }
}
