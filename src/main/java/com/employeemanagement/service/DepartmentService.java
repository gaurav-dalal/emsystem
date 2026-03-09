package com.employeemanagement.service;

import com.employeemanagement.dto.request.DepartmentRequest;
import com.employeemanagement.dto.response.DepartmentResponse;
import com.employeemanagement.entity.Department;
import com.employeemanagement.exception.ResourceNotFoundException;
import com.employeemanagement.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAll() {
        log.info("Fetching all departments");
        List<DepartmentResponse> departments = departmentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.info("Found {} departments", departments.size());
        return departments;
    }

    @Transactional(readOnly = true)
    public DepartmentResponse findById(Long id) {
        log.info("Fetching department with id: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
        return toResponse(department);
    }

    @Transactional
    public DepartmentResponse create(DepartmentRequest request) {
        log.info("Creating department with name: {}", request.getName());
        Department department = Department.builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();
        DepartmentResponse response = toResponse(departmentRepository.save(department));
        log.info("Created department with id: {}", response.getId());
        return response;
    }

    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest request) {
        log.info("Updating department with id: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
        department.setName(request.getName());
        department.setLocation(request.getLocation());
        DepartmentResponse response = toResponse(departmentRepository.save(department));
        log.info("Updated department with id: {}", id);
        return response;
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting department with id: {}", id);
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department", id);
        }
        departmentRepository.deleteById(id);
        log.info("Deleted department with id: {}", id);
    }

    private DepartmentResponse toResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .location(department.getLocation())
                .build();
    }
}
