package com.employeemanagement.controller;

import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.dto.response.EmployeeResponse;
import com.employeemanagement.service.EmployeeService;
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
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
@Tag(name = "Employee APIs", description = "Operations related to Employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Retrieves paginated + sorted list of employees
     * Pagination is handled using {Pageable}
     * If no query parameters are provided then
     *      default values will be applied (page=0, size=10, sort=id,asc)
     *   pagination and sorting can be customized using request parameters
     *   page - page number (0-based)
     *  size - number of records per page
     *  sort - sorting criteria (for ex  sort=name,asc or sort=Id,desc)
     *
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        log.info("GET /api/v1/employees/{}", id);
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        log.info("POST /api/v1/employees - Creating employee: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id,
                                                           @Valid @RequestBody EmployeeRequest request) {
        log.info("PUT /api/v1/employees/{}", id);
        return ResponseEntity.ok(employeeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("DELETE /api/v1/employees/{}", id);
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
