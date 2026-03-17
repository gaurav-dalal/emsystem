package com.employeemanagement.controller;

import com.employeemanagement.dto.request.ProjectRequest;
import com.employeemanagement.dto.response.ProjectResponse;
import com.employeemanagement.exception.GlobalExceptionHandler;
import com.employeemanagement.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
@Import(GlobalExceptionHandler.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    @Test
    void getAllProjects_shouldReturn200() throws Exception {

        ProjectResponse response = ProjectResponse.builder()
                .id(1L)
                .name("Project Alpha")
                .description("First project")
                .departmentId(1L)
                .departmentName("Engineering")
                .build();

        Page<ProjectResponse> page = new PageImpl<>(List.of(response));

        when(projectService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/projects")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Project Alpha")))
                .andExpect(jsonPath("$.content[0].description", is("First project")));

        verify(projectService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getProjectById_shouldReturn200() throws Exception {
        ProjectResponse response = ProjectResponse.builder()
                .id(1L)
                .name("Project Alpha")
                .description("First project")
                .departmentId(1L)
                .departmentName("Engineering")
                .build();

        when(projectService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Project Alpha")));

        verify(projectService, times(1)).findById(1L);
    }

    @Test
    void createProject_shouldReturn201() throws Exception {
        ProjectRequest request = ProjectRequest.builder()
                .name("New Project")
                .description("Project description")
                .departmentId(1L)
                .build();

        ProjectResponse response = ProjectResponse.builder()
                .id(1L)
                .name("New Project")
                .description("Project description")
                .departmentId(1L)
                .departmentName("Engineering")
                .build();

        when(projectService.create(any(ProjectRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Project")));

        verify(projectService, times(1)).create(any(ProjectRequest.class));
    }

    @Test
    void updateProject_shouldReturn200() throws Exception {
        ProjectRequest request = ProjectRequest.builder()
                .name("Updated Project")
                .description("Updated description")
                .departmentId(1L)
                .build();

        ProjectResponse response = ProjectResponse.builder()
                .id(1L)
                .name("Updated Project")
                .build();

        when(projectService.update(eq(1L), any(ProjectRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Project")));

        verify(projectService, times(1)).update(eq(1L), any(ProjectRequest.class));
    }

    @Test
    void deleteProject_shouldReturn204() throws Exception {
        doNothing().when(projectService).delete(1L);

        mockMvc.perform(delete("/api/v1/projects/1"))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).delete(1L);
    }
}
