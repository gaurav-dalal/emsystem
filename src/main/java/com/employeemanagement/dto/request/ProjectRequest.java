package com.employeemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Project creation/update request")
public class ProjectRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 150)
    @Schema(description = "Project name", example = "EMS System")
    private String name;

    @Size(max = 500)
    @Schema(description = "Project description", example = "Employee Management System")
    private String description;

    @NotNull(message = "Department ID is required")
    @Schema(description = "Department ID", example = "1")
    private Long departmentId;
}
