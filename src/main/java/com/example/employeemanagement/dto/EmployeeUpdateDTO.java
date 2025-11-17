package com.example.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmployeeUpdateDTO {

    private String lastName;

    @NotBlank(message = "Phone number is required. Please enter a 10-digit number.")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits.")
    private String phone;

    private String address;
}
