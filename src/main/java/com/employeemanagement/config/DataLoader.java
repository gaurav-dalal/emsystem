package com.employeemanagement.config;

import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.entity.EmployeeProject;
import com.employeemanagement.entity.Project;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.EmployeeProjectRepository;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class DataLoader {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeProjectRepository employeeProjectRepository;

    private static final String[] FIRST_NAMES = {"Gaurav", "Sukumar", "Srinivas", "Mohit", "Jaya", "Seema",
            "Jamila", "Poorva", "Sayma", "Rahul", "Koel", "BalaKumar", "Yash", "Krishna", "Jaykumar", "July",
            "Praveen", "Sarah", "Charles", "Karen", "Ramesh", "Nancy", "Vinod", "Gayatari", "Shanta", "Arjun",
            "Reetu", "Sanju", "Surya", "Kumaran", "Donald", "Raghu", "Steven", "Kimberly", "Prakash", "Anju"};

    private static final String[] LAST_NAMES = {"Narkanda", "Sharma", "Kohli", "Shashtri", "Dhillon", "Singh",
            "Trivedi", "Bumrah", "Sindhu", "Samson", "Bhaskaran", "Patel", "Patil", "Dwiwedi", "Nagaraj",
            "Sinh", "Jurel", "Divekar", "Agarkar", "Tyagi", "Thalapathy", "Walia", "Jain", "Agarwal", "Agnihotri"};

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void loadData() {
        if (employeeRepository.count() > 0) {
            log.info("Data already loaded, skipping initialization");
            return;
        }

        log.info("Loading initial data...");

        List<Department> departments = createDepartments();
        departmentRepository.saveAll(departments);

        List<Project> projects = createProjects(departments);
        projectRepository.saveAll(projects);

        List<Employee> employees = createEmployees(departments);
        employeeRepository.saveAll(employees);

        createEmployeeProjects(employees, projects);


        log.info("Data loaded successfully: {} departments, {} projects, {} employees",
                departments.size(), projects.size(), employees.size());
    }

    private List<Department> createDepartments() {
        return List.of(
                Department.builder().name("Engineering").location("Kolkata").build(),
                Department.builder().name("Human Resources").location("Pune").build(),
                Department.builder().name("Agentic AI").location("Hyderabad").build(),
                Department.builder().name("Gen AI").location("Gurgaon").build(),
                Department.builder().name("Sales").location("Chennai").build(),
                Department.builder().name("Application Platform").location("Kolkata").build(),
                Department.builder().name("Data Science").location("Pune").build(),
                Department.builder().name("ML").location("Hyderabad").build(),
                Department.builder().name("BigQuery").location("Gurgaon").build(),
                Department.builder().name("DataAnalyst").location("Chennai").build(),

                Department.builder().name("New Agentic AI 4").location("Kolkata").build(),
                Department.builder().name("PackagedApp Dev").location("Pune").build(),
                Department.builder().name("Operations").location("Hyderabad").build(),
                Department.builder().name("Super AI").location("Gurgaon").build(),
                Department.builder().name("AI Programming").location("Chennai").build(),
                Department.builder().name("App Gen AI Python").location("Kolkata").build(),
                Department.builder().name("DSA").location("Pune").build(),
                Department.builder().name("Delivery").location("Hyderabad").build(),
                Department.builder().name("Hadoop").location("Gurgaon").build(),
                Department.builder().name("DataAnalyst").location("Chennai").build(),

                Department.builder().name("Operation Support").location("Bengaluru").build()
        );
    }

    private List<Project> createProjects(List<Department> departments) {
        return List.of(
                Project.builder().name("Aetna").description("Core platform development").department(departments.get(0)).build(),
                Project.builder().name("UHG").description("Healthcare").department(departments.get(0)).build(),
                Project.builder().name("IOT").description("Cloud migration").department(departments.get(0)).build(),
                Project.builder().name("HR Portal").description("Employee portal").department(departments.get(1)).build(),
                Project.builder().name("Reebok").description("Shoe one").department(departments.get(1)).build(),
                Project.builder().name("Adidas").description("Shoe two").department(departments.get(2)).build(),
                Project.builder().name("Hotstar").description("Sports telecast").department(departments.get(3)).build(),
                Project.builder().name("Avanade").description("implementation").department(departments.get(4)).build(),
                Project.builder().name("ORacle").description("Queries").department(departments.get(5)).build()
        );
    }

    private List<Employee> createEmployees(List<Department> departments) {
        List<Employee> employees = new ArrayList<>();
        int emailCounter = 1;

        for (int i = 0; i < 60; i++) {
            String firstName = FIRST_NAMES[i % FIRST_NAMES.length];
            String lastName = LAST_NAMES[i % LAST_NAMES.length];
            String name = firstName + " " + lastName;
            String email = "emp" + emailCounter + "." + lastName.toLowerCase() + "@xenture.com";
            emailCounter++;

            BigDecimal salary = BigDecimal.valueOf(45000 + ThreadLocalRandom.current().nextInt(100000));
            LocalDate hireDate = LocalDate.now().minusYears(ThreadLocalRandom.current().nextInt(1, 10))
                    .minusMonths(ThreadLocalRandom.current().nextInt(12));

            Department dept = departments.get(i % departments.size());

            employees.add(Employee.builder()
                    .name(name)
                    .email(email)
                    .salary(salary)
                    .hireDate(hireDate)
                    .department(dept)
                    .build());
        }
        return employees;
    }

    private void createEmployeeProjects(List<Employee> employees, List<Project> projects) {
        String[] roles = {"Lead Dev", "Gen AI Developer", "QAnalyst", "System Designer", "Tester", "Manager","Architect","PlayWright Tester"};
        for (int i = 0; i < 40; i++) {
            Employee emp = employees.get(i % employees.size());
            Project proj = projects.get(i % projects.size());

            EmployeeProject ep = EmployeeProject.builder()
                    .employee(emp)
                    .project(proj)
                    .assignedDate(LocalDate.now().minusMonths(ThreadLocalRandom.current().nextInt(1, 12)))
                    .role(roles[i % roles.length])
                    .build();
            employeeProjectRepository.save(ep);
        }
    }
}
