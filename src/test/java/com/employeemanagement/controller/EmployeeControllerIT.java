package com.employeemanagement.controller;

import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.entity.Department;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.EmployeeRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
class EmployeeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Department department;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();

        department = departmentRepository.save(
                Department.builder()
                        .name("Engineering")
                        .location("Bangalore")
                        .build()
        );
    }

    @Test
    void createEmployee_success() throws Exception {

        EmployeeRequest request = buildRequest("virat@company.com");

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Virat Kohli"))
                .andExpect(jsonPath("$.departmentName").value("Engineering"));

        assertEquals(1, employeeRepository.count()); // DB validation
    }

    @Test
    void createEmployee_duplicateEmail_shouldFail() throws Exception {

        createEmployee("duplicate@company.com");

        EmployeeRequest request = buildRequest("duplicate@company.com");

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEmployee_invalidInput_shouldReturn400() throws Exception {

        EmployeeRequest request = EmployeeRequest.builder()
                .name("") // invalid
                .email("invalid-email")
                .departmentId(department.getId())
                .build();

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getEmployeeById_success() throws Exception {

        Long id = createEmployee("test1@company.com");

        mockMvc.perform(get("/api/v1/employees/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.departmentName").value("Engineering"));
    }

    @Test
    void getEmployeeById_notFound() throws Exception {

        mockMvc.perform(get("/api/v1/employees/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllEmployees_withPagination() throws Exception {

        createEmployee("a@company.com");
        createEmployee("b@company.com");

        mockMvc.perform(get("/api/v1/employees?page=0&size=5&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.size").value(5));
    }


    @Test
    void updateEmployee_success() throws Exception {

        Long id = createEmployee("update@company.com");

        EmployeeRequest request = EmployeeRequest.builder()
                .name("Updated Name")
                .email("updated@company.com")
                .salary(BigDecimal.valueOf(120000))
                .hireDate(LocalDate.of(2023, 1, 1))
                .departmentId(department.getId())
                .build();

        mockMvc.perform(put("/api/v1/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@company.com"));

        assertEquals(1, employeeRepository.count());
    }

    @Test
    void updateEmployee_notFound() throws Exception {

        EmployeeRequest request = buildRequest("test@company.com");

        mockMvc.perform(put("/api/v1/employees/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }



    @Test
    void deleteEmployee_success() throws Exception {

        Long id = createEmployee("delete@company.com");

        mockMvc.perform(delete("/api/v1/employees/{id}", id))
                .andExpect(status().isNoContent());

        assertEquals(0, employeeRepository.count());
    }

    @Test
    void deleteEmployee_notFound() throws Exception {

        mockMvc.perform(delete("/api/v1/employees/{id}", 999L))
                .andExpect(status().isNotFound());
    }




    private EmployeeRequest buildRequest(String email) {
        return EmployeeRequest.builder()
                .name("Virat Kohli")
                .email(email)
                .salary(BigDecimal.valueOf(100000))
                .hireDate(LocalDate.of(2022, 1, 1))
                .departmentId(department.getId())
                .build();
    }

    private Long createEmployee(String email) throws Exception {

        EmployeeRequest request = buildRequest(email);

        String response = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }
}