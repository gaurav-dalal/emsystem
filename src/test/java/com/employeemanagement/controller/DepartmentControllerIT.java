package com.employeemanagement.controller;

import com.employeemanagement.dto.request.DepartmentRequest;
import com.employeemanagement.entity.Department;
import com.employeemanagement.repository.DepartmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class DepartmentControllerIT {

    private static final String BASE_URL = "/api/v1/departments";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void createDepartment_success() throws Exception {

        DepartmentRequest request = DepartmentRequest.builder()
                .departmentName("Engineering")
                .departmentLocation("Bangalore")
                .build();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Engineering"));

        assertEquals(1, departmentRepository.count());
    }

    @Test
    void createDepartment_invalidInput_shouldReturn400() throws Exception {

        DepartmentRequest request = DepartmentRequest.builder()
                .departmentName(" ")
                .departmentLocation("Bangalore")
                .build();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getDepartmentById_success() throws Exception {

        Department dept = createDepartment("HR");

        mockMvc.perform(get(BASE_URL + "/{id}", dept.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("HR"));
    }

    @Test
    void getDepartmentById_notFound() throws Exception {

        mockMvc.perform(get(BASE_URL + "/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllDepartments_success() throws Exception {

        createDepartment("HR");
        createDepartment("Finance");

        mockMvc.perform(get(BASE_URL)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.size").value(2));
    }



    @Test
    void updateDepartment_success() throws Exception {

        Department dept = createDepartment("Old Name");

        DepartmentRequest request = DepartmentRequest.builder()
                .departmentName("New Name")
                .departmentLocation("Mumbai")
                .build();

        mockMvc.perform(put(BASE_URL + "/{id}", dept.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.location").value("Mumbai"));

    }

    @Test
    void updateDepartment_notFound() throws Exception {

        DepartmentRequest request = DepartmentRequest.builder()
                .departmentName("Valid Name")   // IMPORTANT
                .departmentLocation("Delhi")
                .build();

        mockMvc.perform(put(BASE_URL + "/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteDepartment_success() throws Exception {

        Department dept = createDepartment("ToDelete");

        mockMvc.perform(delete(BASE_URL + "/{id}", dept.getId()))
                .andExpect(status().isNoContent());

        assertEquals(0, departmentRepository.count());
    }

    @Test
    void deleteDepartment_notFound() throws Exception {

        mockMvc.perform(delete(BASE_URL + "/{id}", 999L))
                .andExpect(status().isNotFound());
    }


    private Department createDepartment(String name) {
        return departmentRepository.save(
                Department.builder()
                        .name(name)
                        .location("Default Location")
                        .build()
        );
    }
}