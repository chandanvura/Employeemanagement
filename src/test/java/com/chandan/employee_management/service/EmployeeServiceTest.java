package com.chandan.employee_management.service;

import com.chandan.employee_management.model.Employee;
import com.chandan.employee_management.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;



    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    public void setup() {
        employee1 = new Employee(1L, "John Doe", "Developer", 50000);
        employee2 = new Employee(2L, "Jane Smith", "Manager", 70000);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testAddEmployee() {
        when(employeeRepository.save(employee1)).thenReturn(employee1);

        Employee result = employeeService.addEmployee(employee1);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    public void testDeleteEmployee() {
        Long employeeId = 1L;

        doNothing().when(employeeRepository).deleteById(employeeId);

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
