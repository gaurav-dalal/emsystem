-- for creating DEPARTMENTS
CREATE TABLE departments (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             location VARCHAR(200)
);

-- for CREATING EMPLOYEES
CREATE TABLE employees (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(150) NOT NULL UNIQUE,
                           salary DECIMAL(10,2),
                           hire_date DATE,
                           department_id BIGINT NOT NULL,

                           CONSTRAINT fk_employee_department
                               FOREIGN KEY (department_id)
                                   REFERENCES departments(id)
                                   ON DELETE CASCADE
);

-- creates PROJECTS
CREATE TABLE projects (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(150) NOT NULL,
                          description VARCHAR(500),
                          department_id BIGINT NOT NULL,

                          CONSTRAINT fk_project_department
                              FOREIGN KEY (department_id)
                                  REFERENCES departments(id)
                                  ON DELETE CASCADE
);

-- cretes EMPLOYEE_PROJECTS
CREATE TABLE employee_projects (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   employee_id BIGINT NOT NULL,
                                   project_id BIGINT NOT NULL,
                                   assigned_date DATE,
                                   role VARCHAR(50),

                                   CONSTRAINT fk_ep_employee
                                       FOREIGN KEY (employee_id)
                                           REFERENCES employees(id)
                                           ON DELETE CASCADE,

                                   CONSTRAINT fk_ep_project
                                       FOREIGN KEY (project_id)
                                           REFERENCES projects(id)
                                           ON DELETE CASCADE,

                                   CONSTRAINT uk_employee_project UNIQUE (employee_id, project_id)
);