# Employee Management - Spring Boot Application

A Spring Boot REST API application for managing employees, departments, projects, and employee-project assignments.

## Tech Stack

- **Java 17**
- **Spring Boot 3.x.x**
- **Spring Data JPA**
- **MySQL**
- **Lombok**
- **JUnit 5 + Mockito** (Unit Tests)

## Project Structure

```
employee-management/
├── src/main/java/com/employeemanagement/
│   ├── config/          # DataLoader for seed data
│   ├── controller/     # REST controllers
│   ├── dto/            # Request & Response DTOs
│   ├── entity/         # JPA entities
│   ├── exception/      # Global exception handling
│   ├── repository/     # JPA repositories
│   └── service/        # Business logic
└── src/test/           # Unit tests
```

## Database Schema (4 Tables)

| Table | Description |
|-------|-------------|
| **departments** | Department info (name, location) |
| **employees** | Employee info (name, email, salary, hire_date, department_id) |
| **projects** | Project info (name, description, department_id) |
| **employee_projects** | Many-to-many link (employee_id, project_id, role, assigned_date) |

## Prerequisites

- Java 17
- Maven 3.6+
- MySQL 9.0+ (running on localhost:3306)

## Configuration

Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
spring.datasource.username=root
spring.datasource.password=root
```

## Running the Application

```bash
git clone https://github.com/gaurav-dalal/emsystem.git
cd emsystem
mvn spring-boot:run
```

The application will create the database, tables, and seed 60 employees, 6 departments, 9 projects, and 40 assignments on first run.

## API Endpoints

- **Employees:** GET/POST/PUT/DELETE `/api/v1/employees`
- **Departments:** GET/POST/PUT/DELETE `/api/v1/departments`
- **Projects:** GET/POST/PUT/DELETE `/api/v1/projects`
- **Employee-Projects:** GET/POST/PUT/DELETE `/api/v1/employee-projects`

## Pagination
- **Retrieves paginated + sorted list of employees**
- **Pagination is handled using {Pageable}**
- **If no query parameters are provided then default values will be applied (page=0, size=10, sort=id,asc)**
- **pagination and sorting can be customized using request parameters :** 
- `page - page number (0-based)`
- `size - number of records per page`
- `sort - sorting criteria (for ex  sort=name,asc or sort=Id,desc)`
- **sample url ->**`localhost:8080/api/v1/employees?page=0&size=5&sort=name,asc`

## Hikari CP Connection Pool
- **Hikari configuration is provided in application.properties**
- **Connection Pooling: Tuned HikariCP settings to efficiently manage DB connections and improve performance**

## N+1 Queries profiling 
- **Query Profiling: Enabled Hibernate SQL logs to observe queries executed by APIs and identify inefficiencies**
- **N+1 Issue Fix: Resolved N+1 problem in Employee and Employee-Project flows using @EntityGraph  reducing multiple queries to a single optimized JOIN query**
## Running Tests

```bash
mvn test
```
