package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.service.EmployeeManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller-level test for EmployeeManagementController.
 * Uses MockMvc + MockBean (works in Spring Boot 3.5.7)
 */
@WebMvcTest(EmployeeManagementController.class)
class EmployeeManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeManagementService service;

    private Employee employee;

    @BeforeEach
    void setup() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Gnani");
        employee.setLastName("Gude");
        employee.setEmail("Gnani@gmail.com");
        employee.setPhone("9666710934");
        employee.setAddress("Bhimavaram,kovada");
    }

    // Test: Create Employee
    @Test
    void testCreateEmployee() throws Exception {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("Gnani");
        dto.setLastName("Gude");
        dto.setEmail("Gnani@gmail.com");
        dto.setPhone("9666710934");
        dto.setAddress("Bhimavaram,kovada");

        Mockito.when(service.createEmployee(any(EmployeeDTO.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Employee added successfully."));
    }

    // Test: Get All Employees
    @Test
    void testGetAllEmployees() throws Exception {
        Mockito.when(service.getAllEmployees()).thenReturn(List.of(employee));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data[0].email").value("Gnani@gmail.com"));
    }

    // Test: Get by Email
    @Test
    void testGetByEmail() throws Exception {
        Mockito.when(service.getEmployeeByEmail("Gnani@gmail.com"))
                .thenReturn(Optional.of(employee));

        mockMvc.perform(get("/api/employees/by-email")
                        .param("email", "Gnani@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.email").value("Gnani@gmail.com"));
    }

    // Test: Email Not Found
    @Test
    void testGetByEmailNotFound() throws Exception {
        Mockito.when(service.getEmployeeByEmail("notfound@example.com"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/employees/by-email")
                        .param("email", "notfound@example.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message")
                        .value("Employee not found with email: notfound@example.com"));
    }
}
