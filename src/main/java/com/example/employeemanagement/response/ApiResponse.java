package com.example.employeemanagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;  // "success" or "error"
    private Object message; // String or List<String>
    private T data;
}
