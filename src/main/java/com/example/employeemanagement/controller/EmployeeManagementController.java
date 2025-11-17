package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.dto.EmployeeUpdateDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.response.ApiResponse;
import com.example.employeemanagement.service.EmployeeManagementService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employees")
public class EmployeeManagementController {

    @Autowired
    private EmployeeManagementService service;

    // CREATE EMPLOYEE
    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@Valid @RequestBody EmployeeDTO dto) {
        log.info("API Call: Create employee");
        Employee saved = service.createEmployee(dto);
        return new ResponseEntity<>(
                new ApiResponse<>("success", "Employee added successfully.", saved),
                HttpStatus.CREATED
        );
    }

    // GET ALL EMPLOYEES
    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
        log.info("API Call: Get all employees");
        List<Employee> employees = service.getAllEmployees();

        if (employees.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>("success", "No employees found.", employees));
        }
        return ResponseEntity.ok(new ApiResponse<>("success", "Employee list fetched successfully.", employees));
    }

    // FETCH BY EMAIL (Normal JPA)
    @GetMapping("/by-email")
    public ResponseEntity<?> getByEmail(@RequestParam(required = false) String email) {
        log.info("API Call: Get employee by email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("error", "Email parameter is required.", null));
        }

        return service.getEmployeeByEmail(email)
                .map(emp -> ResponseEntity.ok(
                        new ApiResponse<>("success", "Employee found successfully.", emp)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "Employee not found with email: " + email, null)));
    }

    // FETCH BY NAME (Normal JPA)
    @GetMapping("/by-name")
    public ResponseEntity<?> getByName(@RequestParam(required = false) String name) {
        log.info("API Call: Get employee by name");

        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("error", "Name parameter is required.", null));
        }

        List<Employee> employees = service.getEmployeeByName(name);
        if (employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("error", "No employee found with name: " + name, null));
        }

        return ResponseEntity.ok(new ApiResponse<>("success", "Employees fetched successfully.", employees));
    }

    // FETCH BY EMAIL USING HQL
    @GetMapping("/by-email-hql")
    public ResponseEntity<?> getByEmailHql(@RequestParam(required = false) String email) {
        log.info("API Call: Get employee by email using HQL");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("error", "Email parameter is required.", null));
        }

        return service.getEmployeeByEmailHql(email)
                .map(emp -> ResponseEntity.ok(
                        new ApiResponse<>("success", "Employee fetched successfully using HQL.", emp)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "No employee found with email: " + email, null)));
    }

    // FETCH BY NAME USING NATIVE SQL
    @GetMapping("/by-name-native")
    public ResponseEntity<?> getByNameNative(@RequestParam(required = false) String name) {
        log.info("API Call: Get employee by name using Native SQL");

        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("error", "Name parameter is required.", null));
        }

        List<Employee> employees = service.getEmployeeByNameNative(name);
        if (employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("error", "No employee found with name: " + name, null));
        }

        return ResponseEntity.ok(new ApiResponse<>("success", "Employees fetched successfully using Native SQL.", employees));
    }

    // SEARCH USING JPA SPECIFICATION
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String email,
                                    @RequestParam(required = false) String name) {
        log.info("API Call: Dynamic search");

        if ((email == null || email.isBlank()) && (name == null || name.isBlank())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("error", "Please provide at least one search parameter (email or name).", null));
        }

        List<Employee> results = service.getEmployeeBySpecification(email, name);

        if (results.isEmpty()) {
            String message;
            if (email != null && !email.isBlank() && name != null && !name.isBlank()) {
                message = "No employee found with email '" + email + "' and name '" + name + "'";
            } else if (email != null && !email.isBlank()) {
                message = "No employee found with email '" + email + "'";
            } else {
                message = "No employee found with name '" + name + "'";
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("error", message, null));
        }

        return ResponseEntity.ok(new ApiResponse<>("success", "Search successful.", results));
    }

    // UPDATE EMPLOYEE DETAILS (uses EmployeeUpdateDTO now)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable Long id,
                                                                @Valid @RequestBody EmployeeUpdateDTO dto) {
        log.info("API Call: Update employee details");
        Employee updated = service.updateEmployee(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("success", "Employee updated successfully.", updated));
    }

    // UPDATE PHONE ONLY
    @PatchMapping("/{id}/phone")
    public ResponseEntity<ApiResponse<Employee>> updatePhone(@PathVariable Long id,
                                                             @RequestParam String phone) {
        log.info("API Call: Update employee phone");
        Employee updated = service.updatePhone(id, phone);
        return ResponseEntity.ok(new ApiResponse<>("success", "Phone number updated successfully.", updated));
    }

    // DELETE BY EMAIL
    @DeleteMapping("/by-email")
    public ResponseEntity<ApiResponse<String>> deleteByEmail(@RequestParam(required = false) String email) {
        log.info("API Call: Delete employee by email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("error", "Email is required for deletion.", null));
        }

        service.deleteByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>("success", "Employee deleted successfully.", email));
    }
}
