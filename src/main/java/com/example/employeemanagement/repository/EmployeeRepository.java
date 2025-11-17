package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findByFirstNameContainingIgnoreCase(String name);

    // HQL
    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Optional<Employee> findByEmailHql(String email);

    // Native SQL
    @Query(value = "SELECT * FROM employee WHERE first_name LIKE %:name%", nativeQuery = true)
    List<Employee> findByNameNative(String name);
}
