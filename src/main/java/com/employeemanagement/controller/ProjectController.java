package com.employeemanagement.controller;

import com.employeemanagement.dto.request.ProjectRequest;
import com.employeemanagement.dto.response.ProjectResponse;
import com.employeemanagement.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
@Tag(name = "Project APIs", description = "Operations related to Projects")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Get all the projects", description = "Fetch all the projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully") })
    @GetMapping
    public ResponseEntity<Page<ProjectResponse>> getAllProjects(
            @PageableDefault(size = 10, sort = "id") Pageable pageable    ) {

        return ResponseEntity.ok(projectService.findAll(pageable));
    }

    @Operation(summary = "Get project by ID", description = "Fetch a project using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {

        return ResponseEntity.ok(projectService.findById(id));
    }

    @Operation(summary = "Create new project", description = "API to create a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(request));
    }

    @Operation(summary = "To update project by ID", description = "Update an existing project by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id,
                                                        @Valid @RequestBody ProjectRequest request) {
            return ResponseEntity.ok(projectService.update(id, request));
    }

    @Operation(summary = "Delete project by ID", description = "Delete a project using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")})

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {

        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
