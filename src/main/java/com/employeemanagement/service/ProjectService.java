package com.employeemanagement.service;

import com.employeemanagement.dto.request.ProjectDTO;
import com.employeemanagement.dto.response.ProjectResponse;
import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Project;
import com.employeemanagement.exception.ResourceNotFoundException;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<ProjectResponse> findAll() {
        log.info("Fetching all projects");
        List<ProjectResponse> projects = projectRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.info("Found {} projects", projects.size());
        return projects;
    }

    @Transactional(readOnly = true)
    public ProjectResponse findById(Long id) {
        log.info("Fetching project with id: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
        return toResponse(project);
    }

    @Transactional
    public ProjectResponse create(ProjectDTO request) {
        log.info("Creating project with name: {}", request.getName());
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.getDepartmentId()));

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .department(department)
                .build();
        ProjectResponse response = toResponse(projectRepository.save(project));
        log.info("Created project with id: {}", response.getId());
        return response;
    }

    @Transactional
    public ProjectResponse update(Long id, ProjectDTO request) {
        log.info("Updating project with id: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.getDepartmentId()));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setDepartment(department);

        ProjectResponse response = toResponse(projectRepository.save(project));
        log.info("Updated project with id: {}", id);
        return response;
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting project with id: {}", id);
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project", id);
        }
        projectRepository.deleteById(id);
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
}
