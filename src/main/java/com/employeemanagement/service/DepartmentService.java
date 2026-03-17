package com.employeemanagement.service;


import com.employeemanagement.dto.request.DepartmentRequest;
import com.employeemanagement.dto.response.DepartmentResponse;
import com.employeemanagement.entity.Department;
import com.employeemanagement.exception.ResourceNotFoundException;

import com.employeemanagement.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper mapper;

    @Transactional(readOnly = true)
    public Page<DepartmentResponse> findAll(Pageable pageable) {
        log.info(" inside FindAll with pagination ");
        return departmentRepository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Cacheable(value = "departments", key = "#id")
    @Transactional(readOnly = true)
    public DepartmentResponse findById(Long id) {
        Department department = getDepartmentOrThrow(id);
        return mapper.toResponse(department);
    }

    @Transactional
    public DepartmentResponse create(DepartmentRequest request) {
        Department department = Department.builder()
                .name(request.getDepartmentName())
                .location(request.getDepartmentLocation())
                .build();
        Department savedDepartment = departmentRepository.save(department);
        log.info("Department created with id {}", savedDepartment.getId());

        return mapper.toResponse(savedDepartment);
    }

    @CacheEvict(value = "departments", key = "#id")
    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department department = getDepartmentOrThrow(id);
        department.setName(request.getDepartmentName());
        department.setLocation(request.getDepartmentLocation());
        Department updatedDepartment = departmentRepository.save(department);
        log.info("Department {} updated", id);

        return mapper.toResponse(updatedDepartment);
    }
    @CacheEvict(value = "departments", key = "#id")
    @Transactional
    public void delete(Long id) {
        Department department = getDepartmentOrThrow(id);
        departmentRepository.delete(department);
        log.info("Department {} deleted", id);
    }

    private Department getDepartmentOrThrow(Long id) {

        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
    }
}