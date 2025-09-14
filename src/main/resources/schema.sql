-- Departments table
CREATE TABLE department (
                            department_id VARCHAR(10) PRIMARY KEY,
                            name VARCHAR(50) NOT NULL
);

-- Staff table
CREATE TABLE staff (
                       staff_id VARCHAR(10) PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password_hash VARCHAR(100) NOT NULL,
                       role VARCHAR(20) NOT NULL,
                       manager_id VARCHAR(10),
                       department_id VARCHAR(10),
                       leave_allocation INT,
                       FOREIGN KEY (manager_id) REFERENCES staff(staff_id),
                       FOREIGN KEY (department_id) REFERENCES department(department_id)
);

-- Leave Requests table
CREATE TABLE leave_request (
                               request_id VARCHAR(10) PRIMARY KEY,
                               staff_id VARCHAR(10) NOT NULL,
                               start_date DATE NOT NULL,
                               end_date DATE NOT NULL,
                               status VARCHAR(20) NOT NULL,
                               FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
);