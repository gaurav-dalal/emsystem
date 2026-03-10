package com.employeemanagement.service;

import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.dto.response.EmployeeResponse;
import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exception.ResourceNotFoundException;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper mapper;

    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll() {
        log.info(" inside FindALl ");
        return employeeRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        log.info(" inside find by id ------------- ");
        Employee employee = getEmployeeOrThrow(id);
        return mapper.toResponse(employee);
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        employeeRepository.findByEmail(request.getEmail())
                .ifPresent(e -> {
                    throw new IllegalArgumentException(
                            "Employee with email " + request.getEmail() + " already exists");
                });

        Department department = getDepartmentOrThrow(request.getDepartmentId());

        Employee employee = mapper.toEntity(request, department);

        Employee saved = employeeRepository.save(employee);

        log.info("Employee created with id {}", saved.getId());

        return mapper.toResponse(saved);
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {

        Employee employee = getEmployeeOrThrow(id);

        if (!employee.getEmail().equals(request.getEmail()) &&
                employeeRepository.existsByEmail(request.getEmail())) {

            throw new IllegalArgumentException(
                    "Employee with email " + request.getEmail() + " already exists");
        }

        Department department = getDepartmentOrThrow(request.getDepartmentId());

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setSalary(request.getSalary());
        employee.setHireDate(request.getHireDate());
        employee.setDepartment(department);

        Employee updatedEmployee = employeeRepository.save(employee);

        log.info("Employee {} updated", id);

        return mapper.toResponse(updatedEmployee);
    }

    @Transactional
    public void delete(Long id) {
        Employee employee = getEmployeeOrThrow(id);
        employeeRepository.delete(employee);
    }

    private Employee getEmployeeOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    private Department getDepartmentOrThrow(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", departmentId));
    }
}