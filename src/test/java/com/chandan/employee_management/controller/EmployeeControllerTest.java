package com.chandan.employee_management.controller;

import com.chandan.employee_management.model.Employee;
import com.chandan.employee_management.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(
                new Employee(1L, "John Doe", "Developer", 50000),
                new Employee(2L, "Jane Smith", "Manager", 70000)
        );

        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employees.size()))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void testAddEmployee() throws Exception {
        Employee employee = new Employee(1L, "Alice", "Tester", 45000);

        Mockito.when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;

        Mockito.doNothing().when(employeeService).deleteEmployee(employeeId);

        mockMvc.perform(delete("/employees/{id}", employeeId))
                .andExpect(status().isOk());
    }
}
