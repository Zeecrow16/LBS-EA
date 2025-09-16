-- Departments
INSERT INTO department (department_id, name) VALUES
    ('d-1', 'Engineering');

-- Staff
INSERT INTO staff (staff_id, username, first_name, surname, password_hash, role, manager_id, department_id, leave_allocation) VALUES
                                                                                                                                  ('a-1', 'admin', 'System', 'Admin', 'adminpass', 'ADMIN', NULL, 'd-1', 30),
                                                                                                                                  ('m-1', 'manager', 'Jane', 'Smith', 'managerpass', 'MANAGER', 'a-1', 'd-1', 25),
                                                                                                                                  ('s-1', 'jdoe', 'John', 'Doe', 'staffpass', 'STAFF', 'm-1', 'd-1', 20);


-- Leave Requests
INSERT INTO leave_request (request_id, staff_id, start_date, end_date, status) VALUES
    ('lr-1', 's-1', '2025-09-20', '2025-09-22', 'PENDING');