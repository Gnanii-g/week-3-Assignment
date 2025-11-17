package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.dto.EmployeeUpdateDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeManagementService {

    @Autowired
    private EmployeeRepository repo;

    // Create Employee (from DTO)
    public Employee createEmployee(EmployeeDTO dto) {
        Employee emp = new Employee();
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setEmail(dto.getEmail());
        emp.setPhone(dto.getPhone());
        emp.setAddress(dto.getAddress());
        return repo.save(emp);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    // Get employee by email (JPA)
    public Optional<Employee> getEmployeeByEmail(String email) {
        return repo.findByEmail(email);
    }

    // Get employee by name (JPA)
    public List<Employee> getEmployeeByName(String name) {
        return repo.findByFirstNameContainingIgnoreCase(name);
    }

    // Get employee by email (HQL)
    public Optional<Employee> getEmployeeByEmailHql(String email) {
        return repo.findByEmailHql(email);
    }

    // Get employee by name (Native SQL)
    public List<Employee> getEmployeeByNameNative(String name) {
        return repo.findByNameNative(name);
    }

    // Dynamic search (JPA Specifications)
    public List<Employee> getEmployeeBySpecification(String email, String name) {
        Specification<Employee> spec = (root, query, cb) -> cb.conjunction();

        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("email"), email));
        }

        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("firstName")), "%" + name.toLowerCase() + "%"));
        }

        return repo.findAll(spec);
    }

    // Update Employee (last name, phone, address)
    public Employee updateEmployee(Long id, EmployeeUpdateDTO dto) {
        Employee emp = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        if (dto.getLastName() != null && !dto.getLastName().isBlank()) {
            emp.setLastName(dto.getLastName());
        }
        if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
            emp.setPhone(dto.getPhone());
        }
        if (dto.getAddress() != null && !dto.getAddress().isBlank()) {
            emp.setAddress(dto.getAddress());
        }

        return repo.save(emp);
    }

    // Update phone only
    public Employee updatePhone(Long id, String phone) {
        Employee emp = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        emp.setPhone(phone);
        return repo.save(emp);
    }

    // Delete by email
    public void deleteByEmail(String email) {
        Employee emp = repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + email));
        repo.delete(emp);
    }
}
