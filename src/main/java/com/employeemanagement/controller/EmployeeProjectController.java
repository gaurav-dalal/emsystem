package com.employeemanagement.controller;

import com.employeemanagement.dto.request.EmployeeProjectRequest;
import com.employeemanagement.dto.response.EmployeeProjectResponse;
import com.employeemanagement.service.EmployeeProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee-projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class EmployeeProjectController {

    private final EmployeeProjectService employeeProjectService;

    @GetMapping
    public ResponseEntity<List<EmployeeProjectResponse>> getAllAssignments() {
        log.info("GET /api/v1/employee-projects");
        return ResponseEntity.ok(employeeProjectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProjectResponse> getAssignmentById(@PathVariable Long id) {
        log.info("GET /api/v1/employee-projects/{}", id);
        return ResponseEntity.ok(employeeProjectService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeProjectResponse> createAssignment(@Valid @RequestBody EmployeeProjectRequest request) {
        log.info("POST /api/v1/employee-projects - Assigning employee {} to project {}", request.getEmployeeId(), request.getProjectId());
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeProjectService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeProjectResponse> updateAssignment(@PathVariable Long id,
                                                                    @Valid @RequestBody EmployeeProjectRequest request) {
        log.info("PUT /api/v1/employee-projects/{}", id);
        return ResponseEntity.ok(employeeProjectService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        log.info("DELETE /api/v1/employee-projects/{}", id);
        employeeProjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
