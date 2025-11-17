package com.example.employeemanagement;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.EmployeeManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeManagementService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee() {
        // Given
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("John");
        dto.setEmail("john@example.com");
        dto.setPhone("9666710934");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setEmail("john@example.com");
        employee.setPhone("9666710934");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // When
        Employee saved = employeeService.createEmployee(dto);

        // Then
        assertNotNull(saved);
        assertEquals("John", saved.getFirstName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testGetEmployeeByEmail() {
        Employee employee = new Employee();
        employee.setFirstName("Alice");
        employee.setEmail("alice@example.com");

        when(employeeRepository.findByEmail("alice@example.com"))
                .thenReturn(Optional.of(employee));

        Optional<Employee> found = employeeService.getEmployeeByEmail("alice@example.com");

        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getFirstName());
        verify(employeeRepository, times(1)).findByEmail("alice@example.com");
    }

    @Test
    void testUpdatePhone() {
        Employee existing = new Employee();
        existing.setId(1L);
        existing.setFirstName("John");
        existing.setPhone("9666710934");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existing);

        Employee updated = employeeService.updatePhone(1L, "9998887777");

        assertEquals("9998887777", updated.getPhone());
        verify(employeeRepository, times(1)).save(existing);
    }
}
