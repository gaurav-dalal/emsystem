INSERT INTO departments (id, name, location) VALUES (1, 'Engineering', 'Kolkata');
INSERT INTO departments (id, name, location) VALUES (2, 'HR', 'Pune');
INSERT INTO departments (id, name, location) VALUES (3, 'Sales', 'Chennai');
INSERT INTO departments (id, name, location) VALUES (4, 'AI', 'Hyderabad');
INSERT INTO departments (id, name, location) VALUES (5, 'Gen AI', 'Gurgaon');
INSERT INTO departments (id, name, location) VALUES (6, 'Agentic', 'Bengaluru');
INSERT INTO departments (id, name, location) VALUES (7, 'Operations', 'Kolkata');
INSERT INTO departments (id, name, location) VALUES (8, 'Machine Learning', 'Pune');
INSERT INTO departments (id, name, location) VALUES (9, 'New GenAI 4', 'Chennai');
INSERT INTO departments (id, name, location) VALUES (10, 'Multi model AI', 'Hyderabad');
INSERT INTO departments (id, name, location) VALUES (11, 'RAG', 'Gurgaon');
INSERT INTO departments (id, name, location) VALUES (12, 'LangChain', 'Bengaluru');


-- for inseting PROJECTS

INSERT INTO projects (id, name, description, department_id) VALUES (4, 'CVS', 'Healthcare platform', 1);
INSERT INTO projects (id, name, description, department_id) VALUES (5, 'Best Buy', 'E-commerce platform', 1);
INSERT INTO projects (id, name, description, department_id) VALUES (6, 'Vertex', 'Retail analytics system', 2);
INSERT INTO projects (id, name, description, department_id) VALUES (7, 'Salesforce Revamp', 'CRM modernization', 3);
INSERT INTO projects (id, name, description, department_id) VALUES (8, 'AI Chatbot', 'Customer support bot', 4);
INSERT INTO projects (id, name, description, department_id) VALUES (9, 'GenAI Insights', 'AI-driven analytics', 5);
INSERT INTO projects (id, name, description, department_id) VALUES (10, 'Agentic Automation', 'Autonomous workflows', 6);
INSERT INTO projects (id, name, description, department_id) VALUES (11, 'Operations Dashboard', 'Ops monitoring', 7);
INSERT INTO projects (id, name, description, department_id) VALUES (12, 'ML Pipeline 2.0', 'Next-gen ML pipelines', 8);


-- for adding Employees
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Gaurav Dalal', 'gaurav@xenture.com', 990000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Rahul Sharma', 'rahul@xenture.com', 70000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Virat Kohli', 'virat@xenture.com', 55000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Deepika Sharma', 'deepika@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Sukumar Mohan', 'sukumar@xenture.com', 270000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Revathi Reddy', 'revathi@xenture.com', 230000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Malvika Kohli', 'malvika@xenture.com', 410000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Shreedhar Yallanka', 'shreedharY@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Sriniwas', 'sriniwas@xenture.com', 150000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('ranvir d', 'ranvir@xenture.com', 670000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Namitha Kohli', 'namitha@xenture.com', 55000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Prerna Sharma', 'prerna@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Amol kumar', 'amol@xenture.com', 270000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('nayan Reddy', 'nayan@xenture.com', 230000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('shashi Kohli', 'shashi@xenture.com', 410000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('ridhi', 'ridhi@xenture.com', 350000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('vikas', 'vikas@xenture.com', 310000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'sonia Sharma', 'sonia@xenture.com', 70000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'chetan Kohli', 'chetan@xenture.com', 55000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'sudhir Sharma', 'sudhir@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'sewon das', 'das@xenture.com', 270000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'anshuman paul', 'anshuman@xenture.com', 230000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'dinesh saharan', 'dinesh@xenture.com', 410000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'yogesh yallanka', 'yogesh@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'trupati', 'trupati@xenture.com', 150000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'dheeraj sharma', 'dheeraj@xenture.com', 670000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Lovesh Kohli', 'lovesh@xenture.com', 55000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'MS Dhoni', 'ms@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Wridhiman saha', 'wridhiman@xenture.com', 270000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('srinath reddy', 'srinath@xenture.com', 230000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('vashitha Kohli', 'vashitha@xenture.com', 410000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('sujeeth', 'sujeeth@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Apurvva paul', 'apurva@xenture.com', 490000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Ashwini saharan', 'ashwini@xenture.com', 690000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Jyotika yallanka', 'Jyotika@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Tanvi', 'tanvi@xenture.com', 430000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Simran sharma', 'simran@xenture.com', 670000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Dhivya Kohli', 'dhivya@xenture.com', 55000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Himanshu', 'himanshu@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'N Sakthivel', 'sakthivel@xenture.com', 270000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Venketesh reddy', 'venketesh@xenture.com', 230000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('SathishKumar Ranganathan', 'SathishKumar@xenture.com', 410000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('RajeshGurav', 'rajeshgurav@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Nibedita Das', 'nibedita@xenture.com', 230000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Ramakrishna', 'ramakrishna@xenture.com', 410000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Rameela Shekhar', 'rameela@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Dalmiya', 'dalmiya@xenture.com', 150000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Sumita sharma', 'sumita@xenture.com', 670000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Sudhanshu Kohli', 'sudhanshu@xenture.com', 55000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'KrishnaSowjanya', 'krishna.sowjanya@xenture.com', 35000, '2021-05-10', 2);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ( 'Pranay saha', 'pranay@xenture.com', 270000, '2022-01-01', 1);

INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Muduganuru reddy', 'muduganuru@xenture.com', 230000, '2021-05-10', 2);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Manvitha Kohli', 'manvitha@xenture.com', 410000, '2022-01-01', 1);
INSERT INTO employees (name, email, salary, hire_date, department_id)
VALUES ('Sravana Sedhari', 'sravana@xenture.com', 35000, '2021-05-10', 2);





-- for adding EMPLOYEE-PROJECT mappings

INSERT INTO employee_projects (employee_id, project_id, assigned_date, role)
VALUES
    ((SELECT id FROM employees WHERE email='gaurav@xenture.com'),
     (SELECT id FROM projects WHERE name='CVS'),
     '2023-05-01', 'Architect'),

    ((SELECT id FROM employees WHERE email='virat@xenture.com'),
     (SELECT id FROM projects WHERE name='Best Buy'),
     '2023-06-01', 'Backend Developer'),

    ((SELECT id FROM employees WHERE email='rahul@xenture.com'),
     (SELECT id FROM projects WHERE name='Vertex'),
     '2023-07-01', 'Business Analyst'),

    ((SELECT id FROM employees WHERE email='deepika@xenture.com'),
     (SELECT id FROM projects WHERE name='Salesforce Revamp'),
     '2023-08-01', 'Coordinator'),

    ((SELECT id FROM employees WHERE email='sukumar@xenture.com'),
     (SELECT id FROM projects WHERE name='AI Chatbot'),
     '2023-09-01', 'ML Engineer'),

    ((SELECT id FROM employees WHERE email='revathi@xenture.com'),
     (SELECT id FROM projects WHERE name='GenAI Insights'),
     '2023-10-01', 'Data Analyst'),

    ((SELECT id FROM employees WHERE email='malvika@xenture.com'),
     (SELECT id FROM projects WHERE name='Agentic Automation'),
     '2023-11-01', 'AI Engineer'),

    ((SELECT id FROM employees WHERE email='sriniwas@xenture.com'),
     (SELECT id FROM projects WHERE name='Operations Dashboard'),
     '2023-12-01', 'Operations Lead'),

    ((SELECT id FROM employees WHERE email='ranvir@xenture.com'),
     (SELECT id FROM projects WHERE name='ML Pipeline 2.0'),
     '2024-01-01', 'Data Engineer'),

    ((SELECT id FROM employees WHERE email='namitha@xenture.com'),
     (SELECT id FROM projects WHERE name='CVS'),
     '2024-02-01', 'QA Engineer'),

    ((SELECT id FROM employees WHERE email='prerna@xenture.com'),
     (SELECT id FROM projects WHERE name='Best Buy'),
     '2024-03-01', 'Frontend Developer'),

    ((SELECT id FROM employees WHERE email='amol@xenture.com'),
     (SELECT id FROM projects WHERE name='Vertex'),
     '2024-04-01', 'Tech Lead');