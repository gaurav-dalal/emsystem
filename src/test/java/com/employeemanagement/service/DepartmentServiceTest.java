package com.employeemanagement.service;

import com.employeemanagement.dto.request.DepartmentRequest;
import com.employeemanagement.dto.response.DepartmentResponse;
import com.employeemanagement.entity.Department;
import com.employeemanagement.exception.ResourceNotFoundException;
import com.employeemanagement.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper mapper;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;
    private DepartmentRequest departmentRequest;
    private DepartmentResponse departmentResponse;

    @BeforeEach
    void setUp() {

        department = Department.builder()
                .id(1L)
                .name("Engineering")
                .location("New York")
                .build();

        departmentRequest = DepartmentRequest.builder()
                .departmentName("Engineering")
                .departmentLocation("New York")
                .build();

        departmentResponse = DepartmentResponse.builder()
                .id(1L)
                .name("Engineering")
                .location("New York")
                .build();
    }

    @Test
    void findAll_shouldReturnAllDepartments() {

        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(mapper.toResponse(department)).thenReturn(departmentResponse);

        List<DepartmentResponse> result = departmentService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Engineering", result.get(0).getName());

        verify(departmentRepository).findAll();
    }

    @Test
    void findById_whenDepartmentExists_shouldReturnDepartment() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(mapper.toResponse(department)).thenReturn(departmentResponse);

        DepartmentResponse result = departmentService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Engineering", result.getName());

        verify(departmentRepository).findById(1L);
    }

    @Test
    void findById_whenDepartmentNotFound_shouldThrowResourceNotFoundException() {

        when(departmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.findById(999L));
    }

    @Test
    void create_shouldSaveAndReturnDepartment() {

        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(mapper.toResponse(department)).thenReturn(departmentResponse);

        DepartmentResponse result = departmentService.create(departmentRequest);

        assertNotNull(result);
        assertEquals("Engineering", result.getName());

        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    void update_shouldUpdateAndReturnDepartment() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(mapper.toResponse(department)).thenReturn(departmentResponse);

        DepartmentResponse result = departmentService.update(1L, departmentRequest);

        assertNotNull(result);
        assertEquals("Engineering", result.getName());

        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    void delete_shouldDeleteDepartment() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        departmentService.delete(1L);

        verify(departmentRepository).delete(department);
    }

    @Test
    void delete_whenDepartmentNotFound_shouldThrowResourceNotFoundException() {

        when(departmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.delete(999L));

        verify(departmentRepository, never()).delete(any());
    }
}