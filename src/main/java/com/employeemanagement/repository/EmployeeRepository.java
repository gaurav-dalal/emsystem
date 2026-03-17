package com.employeemanagement.repository;

import com.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);


    /**
     *
     * To solve N+1 Entity Graph is being used
     * Entity Graph : Whenever fetch Employee ->  also fetch Department in the same query
     * Entity Graph - allowing Hibernate to fetch related data using a join in a single query
     * @param pageable
     * @return
     */
    @Override
    @EntityGraph(attributePaths = {"department"})
    Page<Employee> findAll(Pageable pageable);
}
