package com.employeemanagement.service;

import com.employeemanagement.dto.response.DepartmentResponse;
import com.employeemanagement.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentResponse toResponse(Department department) {

        if (department == null) {
            return null;
        }

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .location(department.getLocation())
                .build();
    }
}