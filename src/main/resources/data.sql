-- Departments
INSERT INTO department (department_id, name) VALUES
                                                 ('d-1', 'Engineering'),
                                                 ('d-2', 'Cyber'),
                                                 ('d-3', 'Marketing'),
                                                 ('d-4', 'Finance');

-- Staff
INSERT INTO staff (staff_id, username, first_name, surname, password_hash, role, manager_id, department_id, leave_allocation) VALUES
('a-1', 'admin1', 'System', 'Admin', 'adminpass', 'ADMIN', NULL, 'd-1', 30),
('a-2', 'admin2', 'Admin', 'Two', 'adminpass', 'ADMIN', NULL, 'd-2', 30),
('m-1', 'manager1', 'Jane', 'Smith', 'managerpass', 'MANAGER', 'a-1', 'd-1', 25),
('m-2', 'manager2', 'Douglas', 'Adams', 'managerpass', 'MANAGER', 'a-2', 'd-2', 25),
('s-1', 'staff1', 'John', 'Doe', 'staffpass', 'STAFF', 'm-1', 'd-1', 20),
('s-2', 'staff2', 'Alice', 'Smith', 'staffpass', 'STAFF', 'm-2', 'd-2', 20);

-- Leave Requests
INSERT INTO leave_request (request_id, staff_id, start_date, end_date, status) VALUES
                                                                                   ('lr-1', 's-1', '2025-09-20', '2025-09-22', 'PENDING'),
                                                                                   ('lr-2', 's-2', '2025-09-21', '2025-09-23', 'PENDING');