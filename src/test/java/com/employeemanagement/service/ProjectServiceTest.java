package com.employeemanagement.service;

import com.employeemanagement.dto.request.ProjectRequest;
import com.employeemanagement.dto.response.ProjectResponse;
import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Project;
import com.employeemanagement.exception.ResourceNotFoundException;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private ProjectService projectService;

    private Department department;
    private Project project;
    private ProjectRequest projectRequest;

    @BeforeEach
    void setUp() {

        department = Department.builder()
                .id(1L)
                .name("Engineering")
                .location("New York")
                .build();

        project = Project.builder()
                .id(1L)
                .name("Project Alpha")
                .description("Core platform")
                .department(department)
                .build();

        projectRequest = ProjectRequest.builder()
                .name("Project Alpha")
                .description("Core platform")
                .departmentId(1L)
                .build();
    }

    private void mockProjectFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
    }

    private void mockProjectNotFound() {
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());
    }

    private void mockDepartmentFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
    }

    private void mockProjectSave() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);
    }


    @Test
    void findAll_shouldReturnAllProjects() {

        when(projectRepository.findAll()).thenReturn(List.of(project));
        List<ProjectResponse> result = projectService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Project Alpha", result.get(0).getName());

        verify(projectRepository).findAll();
    }

    @Test
    void findById_whenProjectExists_shouldReturnProject() {

        mockProjectFound();
        ProjectResponse result = projectService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Project Alpha", result.getName());

        verify(projectRepository).findById(1L);
    }

    @Test
    void findById_whenProjectNotFound_shouldThrowResourceNotFoundException() {

        mockProjectNotFound();
        assertThrows(ResourceNotFoundException.class,
                () -> projectService.findById(999L));
    }

    @Test
    void create_shouldSaveAndReturnProject() {

        mockDepartmentFound();
        mockProjectSave();
        ProjectResponse result = projectService.create(projectRequest);
        assertNotNull(result);
        assertEquals("Project Alpha", result.getName());

        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void update_shouldUpdateAndReturnProject() {

        mockProjectFound();
        mockDepartmentFound();
        mockProjectSave();
        ProjectResponse result = projectService.update(1L, projectRequest);
        assertNotNull(result);
        assertEquals("Project Alpha", result.getName());

        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void delete_shouldDeleteProject() {

        mockProjectFound();
        projectService.delete(1L);
        verify(projectRepository).delete(project);
    }

    @Test
    void delete_whenProjectNotFound_shouldThrowResourceNotFoundException() {

        mockProjectNotFound();
        assertThrows(ResourceNotFoundException.class,
                () -> projectService.delete(999L));
        verify(projectRepository, never()).delete(any());
    }
}