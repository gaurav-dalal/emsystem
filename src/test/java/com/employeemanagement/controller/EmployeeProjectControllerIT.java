package com.employeemanagement.controller;

import com.employeemanagement.dto.request.EmployeeProjectRequest;
import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.entity.EmployeeProject;
import com.employeemanagement.entity.Project;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.EmployeeProjectRepository;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.repository.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
class EmployeeProjectControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    private Employee employee;
    private Project project;
    private Department department;

    @BeforeEach
    void setup() {


        employeeProjectRepository.deleteAll();
        employeeRepository.deleteAll();
        projectRepository.deleteAll();
        departmentRepository.deleteAll();


        department = departmentRepository.save(
                Department.builder()
                        .name("IT")
                        .location("Delhi")
                        .build()
        );

        employee = employeeRepository.save(
                Employee.builder()
                        .name("Narender")
                        .email("narender@test.com")
                        .department(department)
                        .build()
        );

        project = projectRepository.save(
                Project.builder()
                        .name("EMS")
                        .description("Test Project")
                        .department(department)
                        .build()
        );
    }

    @Test
    void shouldCreateEmployeeProjectAssignment() throws Exception {

        EmployeeProjectRequest request = EmployeeProjectRequest.builder()
                .employeeId(employee.getId())
                .projectId(project.getId())
                .role("Developer")
                .build();

        mockMvc.perform(post("/api/v1/employee-projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(employee.getId()))
                .andExpect(jsonPath("$.projectId").value(project.getId()))
                .andExpect(jsonPath("$.role").value("Developer"));
    }


    @Test
    void shouldReturnAllAssignments() throws Exception {

        employeeProjectRepository.save(
                EmployeeProject.builder()
                        .employee(employee)
                        .project(project)
                        .role("Dev")
                        .build()
        );

        mockMvc.perform(get("/api/v1/employee-projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }


    @Test
    void shouldReturnAssignmentById() throws Exception {

        EmployeeProject ep = employeeProjectRepository.save(
                EmployeeProject.builder()
                        .employee(employee)
                        .project(project)
                        .role("Tester")
                        .build()
        );

        mockMvc.perform(get("/api/v1/employee-projects/" + ep.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(employee.getId()))
                .andExpect(jsonPath("$.projectId").value(project.getId()));
    }


    @Test
    void shouldReturn404WhenEmployeeNotFound() throws Exception {

        EmployeeProjectRequest request = EmployeeProjectRequest.builder()
                .employeeId(999L)
                .projectId(project.getId())
                .build();

        mockMvc.perform(post("/api/v1/employee-projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAssignment() throws Exception {

        EmployeeProject ep = employeeProjectRepository.save(
                EmployeeProject.builder()
                        .employee(employee)
                        .project(project)
                        .build()
        );

        mockMvc.perform(delete("/api/v1/employee-projects/" + ep.getId()))
                .andExpect(status().isNoContent());

        assertFalse(employeeProjectRepository.existsById(ep.getId()));
    }
}
