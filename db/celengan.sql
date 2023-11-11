-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 11, 2023 at 08:03 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `celengan`
--

-- --------------------------------------------------------

--
-- Table structure for table `pemasukan_kategori`
--

CREATE TABLE `pemasukan_kategori` (
  `id` int NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pemasukan_kategori`
--

INSERT INTO `pemasukan_kategori` (`id`, `name`) VALUES
(1, 'makanan'),
(2, 'minuman'),
(3, 'Transportasi');

-- --------------------------------------------------------

--
-- Table structure for table `pengeluaran_kategori`
--

CREATE TABLE `pengeluaran_kategori` (
  `id` int NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `transac`
--

CREATE TABLE `transac` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `nominal` decimal(15,2) NOT NULL,
  `keterangan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category_id` int NOT NULL,
  `payment_type` enum('Cash','Transfer') NOT NULL,
  `date` date NOT NULL,
  `tipe_transaksi` enum('pemasukan','pengeluaran') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `transac`
--

INSERT INTO `transac` (`id`, `user_id`, `nominal`, `keterangan`, `category_id`, `payment_type`, `date`, `tipe_transaksi`) VALUES
(1, 1, '100000.00', 'membeli sarapan', 1, 'Cash', '2023-10-30', 'pemasukan');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_edited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remember_me` enum('True','False') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `last_edited`, `remember_me`) VALUES
(1, 'Revo Rahmat', '1234', '2023-10-30 16:33:25', 'True');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pemasukan_kategori`
--
ALTER TABLE `pemasukan_kategori`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pengeluaran_kategori`
--
ALTER TABLE `pengeluaran_kategori`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transac`
--
ALTER TABLE `transac`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_transac` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pemasukan_kategori`
--
ALTER TABLE `pemasukan_kategori`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `pengeluaran_kategori`
--
ALTER TABLE `pengeluaran_kategori`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `transac`
--
ALTER TABLE `transac`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transac`
--
ALTER TABLE `transac`
  ADD CONSTRAINT `FK_transac` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
