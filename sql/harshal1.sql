-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 21, 2024 at 02:12 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `harshal1`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments` (
  `appointment_id` varchar(50) NOT NULL,
  `patient_id` varchar(50) DEFAULT NULL,
  `doctor_id` varchar(50) DEFAULT NULL,
  `appointment_date` varchar(50) DEFAULT NULL,
  `fees` varchar(50) DEFAULT NULL,
  `room_id` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`appointment_id`, `patient_id`, `doctor_id`, `appointment_date`, `fees`, `room_id`) VALUES
('A001', 'P007', 'D005', '2024-01-23', '400', 'R001'),
('A008', 'P005', 'D002', '2024-01-16', '400', 'R001');

--
-- Triggers `appointments`
--
DELIMITER $$
CREATE TRIGGER `after_appointment_insert` AFTER INSERT ON `appointments` FOR EACH ROW BEGIN
    DECLARE total_fees VARCHAR(50);

    -- Calculate total fees by combining appointment fees and room price
    SELECT CONCAT(NEW.fees, ' + ', r.room_price) INTO total_fees
    FROM rooms r
    WHERE r.room_id = NEW.room_id;

    -- Insert the compiled data into master_data table
    INSERT INTO master_data (
        master_id, 
        patient_id, 
        patient_name, 
        patient_dob, 
        patient_contact, 
        patient_address, 
        doctor_id, 
        doctor_name, 
        doctor_specialization, 
        doctor_contact, 
        appointment_date, 
        disease, 
        fees, 
        room_id, 
        room_type, 
        room_capacity, 
        room_price
    )
    SELECT 
        UUID(),  -- Generate a unique master_id
        p.patient_id, 
        p.patient_name, 
        p.patient_dob, 
        p.patient_contact, 
        p.patient_address, 
        d.doctor_id, 
        d.doctor_name, 
        d.doctor_specialization, 
        d.doctor_contact, 
        NEW.appointment_date, 
        p.disease, 
        total_fees, 
        r.room_id, 
        r.room_type, 
        r.room_capacity, 
        r.room_price
    FROM patients p
    JOIN doctors d ON d.doctor_id = NEW.doctor_id
    JOIN rooms r ON r.room_id = NEW.room_id
    WHERE p.patient_id = NEW.patient_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `doctor_id` varchar(50) NOT NULL,
  `doctor_name` varchar(100) DEFAULT NULL,
  `doctor_specialization` varchar(50) DEFAULT NULL,
  `doctor_contact` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`doctor_id`, `doctor_name`, `doctor_specialization`, `doctor_contact`) VALUES
('A003', 'harshal', 'yhvjh', ' 123456789'),
('D001', 'Dr. John Doe', 'Cardiology', '1234567890'),
('D002', 'Dr. Jane Smith', 'Neurology', '0987654321'),
('D005', 'ishan', 'kuch nhi', ' 123456789');

-- --------------------------------------------------------

--
-- Table structure for table `master_data`
--

CREATE TABLE `master_data` (
  `master_id` varchar(50) NOT NULL,
  `patient_id` varchar(50) DEFAULT NULL,
  `patient_name` varchar(100) DEFAULT NULL,
  `patient_dob` varchar(20) DEFAULT NULL,
  `patient_contact` varchar(50) DEFAULT NULL,
  `patient_address` varchar(255) DEFAULT NULL,
  `doctor_id` varchar(50) DEFAULT NULL,
  `doctor_name` varchar(100) DEFAULT NULL,
  `doctor_specialization` varchar(50) DEFAULT NULL,
  `doctor_contact` varchar(50) DEFAULT NULL,
  `appointment_date` varchar(50) DEFAULT NULL,
  `disease` varchar(100) DEFAULT NULL,
  `fees` varchar(50) DEFAULT NULL,
  `room_id` varchar(50) DEFAULT NULL,
  `room_type` varchar(50) DEFAULT NULL,
  `room_capacity` varchar(50) DEFAULT NULL,
  `room_price` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `master_data`
--

INSERT INTO `master_data` (`master_id`, `patient_id`, `patient_name`, `patient_dob`, `patient_contact`, `patient_address`, `doctor_id`, `doctor_name`, `doctor_specialization`, `doctor_contact`, `appointment_date`, `disease`, `fees`, `room_id`, `room_type`, `room_capacity`, `room_price`) VALUES
('31cfa769-a5b9-11ef-ba9b-a8b13b810e05', 'P005', 'harshal', ' 08-04-2006', '123456789', ' goregaon', 'D005', 'ishan', 'kuch nhi', ' 123456789', '18-11-2024', ' covid', '50 + 500', 'R001', 'ICU', '2', '500'),
('59ac5ffc-a635-11ef-a891-a8b13b810e05', 'P005', 'harshal', ' 08-04-2006', '123456789', ' goregaon', 'D002', 'Dr. Jane Smith', 'Neurology', '0987654321', '2024-01-16', ' covid', '400 + 500', 'R001', 'ICU', '2', '500'),
('8964e460-a630-11ef-a891-a8b13b810e05', 'P007', 'soham', '2024-11-03', '123456789', ' dadar', 'D005', 'ishan', 'kuch nhi', ' 123456789', '2024-01-23', ' covid', '400 + 500', 'R001', 'ICU', '2', '500'),
('8e965a22-a5c1-11ef-ba9b-a8b13b810e05', 'P002', 'Bob Brown', '1985-05-05', '0987654321', '456 Oak Avenue', 'D005', 'ishan', 'kuch nhi', ' 123456789', '2024-01-22', 'Migraine', '10 + 800', 'R003', 'Private', '1', '800');

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

CREATE TABLE `patients` (
  `patient_id` varchar(50) NOT NULL,
  `patient_name` varchar(100) DEFAULT NULL,
  `patient_dob` varchar(20) DEFAULT NULL,
  `patient_contact` varchar(50) DEFAULT NULL,
  `patient_address` varchar(255) DEFAULT NULL,
  `doctor_id` varchar(50) DEFAULT NULL,
  `appointment_date` varchar(50) DEFAULT NULL,
  `disease` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`patient_id`, `patient_name`, `patient_dob`, `patient_contact`, `patient_address`, `doctor_id`, `appointment_date`, `disease`) VALUES
('P001', 'Alice Johnson', '1990-01-01', '1234567890', '123 Elm Street', 'D001', '2024-12-01', 'Heart Disease'),
('P002', 'Bob Brown', '1985-05-05', '0987654321', '456 Oak Avenue', 'D002', '2024-12-05', 'Migraine'),
('P005', 'harshal', ' 08-04-2006', '123456789', ' goregaon', NULL, '18-11-2024', ' covid'),
('P007', 'soham', '2024-11-03', '123456789', ' dadar', NULL, '2024-11-23', ' covid');

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `room_id` varchar(50) NOT NULL,
  `room_type` varchar(50) DEFAULT NULL,
  `room_capacity` varchar(50) DEFAULT NULL,
  `room_price` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`room_id`, `room_type`, `room_capacity`, `room_price`) VALUES
('R001', 'ICU', '2', '500'),
('R002', 'General', '4', '200'),
('R003', 'Private', '1', '800');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
  ADD PRIMARY KEY (`appointment_id`),
  ADD KEY `patient_id` (`patient_id`),
  ADD KEY `doctor_id` (`doctor_id`),
  ADD KEY `room_id` (`room_id`);

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`doctor_id`);

--
-- Indexes for table `master_data`
--
ALTER TABLE `master_data`
  ADD PRIMARY KEY (`master_id`);

--
-- Indexes for table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`patient_id`),
  ADD KEY `doctor_id` (`doctor_id`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`room_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointments`
--
ALTER TABLE `appointments`
  ADD CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`patient_id`),
  ADD CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`),
  ADD CONSTRAINT `appointments_ibfk_3` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`);

--
-- Constraints for table `patients`
--
ALTER TABLE `patients`
  ADD CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
