package com.employeemanagement.service;

import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.dto.response.EmployeeResponse;
import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exception.ResourceNotFoundException;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper mapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Department department;
    private Employee employee;
    private EmployeeRequest employeeRequest;
    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp() {

        department = Department.builder()
                .id(1L)
                .name("Engineering")
                .location("New York")
                .build();

        employee = Employee.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@company.com")
                .salary(BigDecimal.valueOf(75000))
                .hireDate(LocalDate.of(2020, 1, 15))
                .department(department)
                .build();

        employeeRequest = EmployeeRequest.builder()
                .name("John Doe")
                .email("john.doe@company.com")
                .salary(BigDecimal.valueOf(75000))
                .hireDate(LocalDate.of(2020, 1, 15))
                .departmentId(1L)
                .build();

        employeeResponse = EmployeeResponse.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@company.com")
                .salary(BigDecimal.valueOf(75000))
                .hireDate(LocalDate.of(2020, 1, 15))
                .departmentId(1L)
                .departmentName("Engineering")
                .build();
    }

    @Test
    void findAll_shouldReturnAllEmployeesWithPagination() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> employeePage = new PageImpl<>(List.of(employee));

        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);
        when(mapper.toResponse(employee)).thenReturn(employeeResponse);

        Page<EmployeeResponse> result = employeeService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("John Doe", result.getContent().get(0).getName());

        verify(employeeRepository).findAll(pageable);
    }

    @Test
    void findById_whenEmployeeExists_shouldReturnEmployee() {

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(mapper.toResponse(employee)).thenReturn(employeeResponse);

        EmployeeResponse result = employeeService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Engineering", result.getDepartmentName());

        verify(employeeRepository).findById(1L);
    }

    @Test
    void findById_whenEmployeeNotFound_shouldThrowException() {

        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.findById(999L));

        verify(employeeRepository).findById(999L);
    }

    @Test
    void create_shouldSaveEmployee() {

        when(employeeRepository.findByEmail(employeeRequest.getEmail()))
                .thenReturn(Optional.empty());

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(mapper.toEntity(employeeRequest, department))
                .thenReturn(employee);

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        when(mapper.toResponse(employee))
                .thenReturn(employeeResponse);

        EmployeeResponse result = employeeService.create(employeeRequest);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());

        verify(employeeRepository).save(employee);
    }

    @Test
    void create_whenEmailExists_shouldThrowException() {

        when(employeeRepository.findByEmail(employeeRequest.getEmail()))
                .thenReturn(Optional.of(employee));

        assertThrows(IllegalArgumentException.class,
                () -> employeeService.create(employeeRequest));

        verify(employeeRepository, never()).save(any());
    }

    @Test
    void update_whenEmployeeNotFound_shouldThrowException() {

        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.update(999L, employeeRequest));

        verify(employeeRepository, never()).save(any());
    }

    @Test
    void delete_shouldDeleteEmployee() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        employeeService.delete(1L);

        verify(employeeRepository).delete(employee);
    }

    @Test
    void delete_whenEmployeeNotFound_shouldThrowException() {

        when(employeeRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.delete(999L));

        verify(employeeRepository, never()).delete(any());
    }
}