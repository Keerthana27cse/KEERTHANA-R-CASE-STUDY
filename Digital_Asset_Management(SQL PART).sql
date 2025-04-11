-- DROP DATABASE digital_asset_management_application;

CREATE DATABASE digital_asset_management_application;
USE digital_asset_management_application;

-- Drop tables if they exist
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS asset_allocation;
DROP TABLE IF EXISTS maintenance_records;
DROP TABLE IF EXISTS assets;
DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE assets (
    asset_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_name VARCHAR(100) NOT NULL,
    asset_type VARCHAR(100) NOT NULL,
    serial_number VARCHAR(100) UNIQUE NOT NULL,
    purchase_date DATE NOT NULL,
    location VARCHAR(100),
    asset_status ENUM('IN_USE', 'DECOMMISSIONED', 'UNDER_MAINTENANCE', 'AVAILABLE', 'ALLOCATED') NOT NULL,
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES employees(employee_id) ON DELETE SET NULL
);

CREATE TABLE maintenance_records (
    maintenance_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
	maintenance_date DATE NOT NULL,
    description TEXT NOT NULL,
    cost DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE
);

CREATE TABLE asset_allocations (
    allocation_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
    employee_id INT NOT NULL,
    allocation_date DATE NOT NULL,
    return_date DATE DEFAULT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE
);

-- Reservations Table
CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
    employee_id INT NOT NULL,
    reservation_date DATE NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reservation_status ENUM('PENDING', 'APPROVED', 'CANCELED') NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE
);

SELECT * FROM employees;
SELECT * FROM assets;
SELECT * FROM maintenance_records;
SELECT * FROM asset_allocations;
SELECT * FROM reservations;




INSERT INTO employees (employee_name, department, email, password)
VALUES 
('Keerthana S', 'IT', 'keerthana@example.com', 'password123'),
('Rahul V', 'Finance', 'rahul.v@example.com', 'fin@pass456'),
('Ankitha R', 'HR', 'ankitha.hr@example.com', 'hr@secure789'),
('Naveen K', 'IT', 'naveen.k@example.com', 'it@pass001'),
('Priya M', 'Marketing', 'priya.m@example.com', 'market@123'),
('Dinesh R', 'Logistics', 'dinesh.r@example.com', 'logi@456'),
('Sneha L', 'Finance', 'sneha.l@example.com', 'fin@secure998'),
('Vikram A', 'IT', 'vikram.a@example.com', 'it@code777'),
('Lavanya P', 'HR', 'lavanya.p@example.com', 'hr@strong321'),
('Karthik B', 'Marketing', 'karthik.b@example.com', 'market@456'),
('Meena S', 'Operations', 'meena.s@example.com', 'ops@pass222'),
('Gokul T', 'IT', 'gokul.t@example.com', 'tech@it789'),
('Reshma N', 'Finance', 'reshma.n@example.com', 'fin@123secure');

