package com.employeemanagement.controller;


import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Project;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.EmployeeProjectRepository;
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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
//@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class ProjectControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;

    @BeforeEach
    void setup() {
        employeeProjectRepository.deleteAll();
        projectRepository.deleteAll();
        departmentRepository.deleteAll();

        department = departmentRepository.save(
                Department.builder()
                        .name("IT")
                        .location("Delhi")
                        .build()
        );
    }

    @Test
    void shouldCreateProject() throws Exception {

        String request = """
        {
            "name": "EMS Project",
            "description": "Employee Management System",
            "departmentId": %d
        }
    """.formatted(department.getId());

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("EMS Project"))
                .andExpect(jsonPath("$.departmentId").value(department.getId()));
    }

    @Test
    void shouldGetProjectById() throws Exception {

        Project project = Project.builder()
                .name("Agentic AI")
                .description("Desc")
                .department(department)
                .build();

        project = projectRepository.save(project);

        mockMvc.perform(get("/api/v1/projects/" + project.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(project.getId()))
                .andExpect(jsonPath("$.name").value("Agentic AI"));
    }

    @Test
    void shouldReturnPaginatedProjects() throws Exception {

        int totalProjects = 15;

        for (int i = 0; i < totalProjects; i++) {
            projectRepository.save(Project.builder()
                    .name("Project " + i)
                    .description("Desc")
                    .department(department)
                    .build());
        }

        mockMvc.perform(get("/api/v1/projects?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(10))
                .andExpect(jsonPath("$.totalElements").value(totalProjects))
                .andExpect(jsonPath("$.content[0].name").value("Project 0"));
    }
}
