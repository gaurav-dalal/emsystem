package com.employeemanagement.controller;

import com.employeemanagement.dto.request.EmployeeProjectRequest;
import com.employeemanagement.dto.response.EmployeeProjectResponse;
import com.employeemanagement.service.EmployeeProjectService;
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

@RestController
@RequestMapping("/api/v1/employee-projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
@Tag(name = "Employee-Project APIs", description = "Manage assignment of employees to projects")
public class EmployeeProjectController {

    private final EmployeeProjectService employeeProjectService;

    @Operation(
            summary = "Get all assignments",
            description = "Fetch paginated list of employee-project assignments"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assignments retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<EmployeeProjectResponse>> getAllAssignments(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(employeeProjectService.findAll(pageable));
    }

    @Operation(
            summary = "Get assignment by ID",
            description = "Fetch a specific employee-project assignment"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assignment found"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProjectResponse> getAssignmentById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeProjectService.findById(id));
    }

    @Operation(
            summary = "Assign employee to project",
            description = "Create a new mapping between an employee and a project with role and assigned date"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Assignment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Employee or Project not found")
    })
    @PostMapping
    public ResponseEntity<EmployeeProjectResponse> createAssignment(@Valid @RequestBody EmployeeProjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeProjectService.create(request));
    }

    @Operation(
            summary = "Update assignment",
            description = "Update employee-project mapping details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assignment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Assignment, Employee or Project not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeProjectResponse> updateAssignment(@PathVariable Long id,
                                                                    @Valid @RequestBody EmployeeProjectRequest request) {
        return ResponseEntity.ok(employeeProjectService.update(id, request));
    }

    @Operation(
            summary = "Delete assignment",
            description = "Remove an employee from a project"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Assignment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        employeeProjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}