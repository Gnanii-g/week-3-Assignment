package com.example.employeemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmployeeDTO {

    @NotBlank(message = "First name is required. Please enter the employee's first name.")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is required. Please enter a valid email address.")
    @Email(message = "Please provide a properly formatted email address.")
    private String email;

    @NotBlank(message = "Phone number is required. Please enter a 10-digit number.")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits.")
    private String phone;

    private String address;
}
