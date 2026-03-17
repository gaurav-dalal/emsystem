package com.employeemanagement.service;

import com.employeemanagement.dto.request.ProjectRequest;
import com.employeemanagement.dto.response.ProjectResponse;
import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Project;
import com.employeemanagement.exception.ResourceNotFoundException;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public Page<ProjectResponse> findAll(Pageable pageable) {
        log.info(" inside findAll projects with pagination ");
        return projectRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Cacheable(value = "projects", key = "#id")
    @Transactional(readOnly = true)
    public ProjectResponse findById(Long id) {

        Project project = getProjectOrThrow(id);

        return toResponse(project);
    }

    @Transactional
    public ProjectResponse create(ProjectRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.getDepartmentId()));

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .department(department)
                .build();

        log.info("project creatted");
        return toResponse(projectRepository.save(project));
    }

    @CacheEvict(value = "projects", key = "#id")
    @Transactional
    public ProjectResponse update(Long id, ProjectRequest request) {
        log.info("Updating project with id: {}", id);

        Project project = getProjectOrThrow(id);

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.getDepartmentId()));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setDepartment(department);

        ProjectResponse response = toResponse(projectRepository.save(project));
        log.info("Updated project with id: {}", id);
        return response;
    }

    @CacheEvict(value = "projects", key = "#id")
    @Transactional
    public void delete(Long id) {
        log.info("Deleting project with id: {}", id);
        Project project = getProjectOrThrow(id);
        projectRepository.delete(project);
        log.info("Deleted project with id: {}", id);
    }

    private ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .departmentId(project.getDepartment().getId())
                .departmentName(project.getDepartment().getName())
                .build();
    }

    private Project getProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
    }
}
