package com.employeemanagement.service;

import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.dto.response.EmployeeResponse;
import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .hireDate(employee.getHireDate())
                .departmentId(employee.getDepartment().getId())
                .departmentName(employee.getDepartment().getName())
                .build();
    }

    public Employee toEntity(EmployeeRequest request, Department department) {

        return Employee.builder()
                .name(request.getName())
                .email(request.getEmail())
                .salary(request.getSalary())
                .hireDate(request.getHireDate())
                .department(department)
                .build();
    }
}