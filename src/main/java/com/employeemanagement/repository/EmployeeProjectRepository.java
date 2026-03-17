package com.employeemanagement.repository;

import com.employeemanagement.entity.EmployeeProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {

    @Override
    @EntityGraph(attributePaths = {"employee", "project"})
    Page<EmployeeProject> findAll(Pageable pageable);
}
