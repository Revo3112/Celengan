-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 08, 2023 at 12:23 AM
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
-- Table structure for table `hapus_kategori`
--

CREATE TABLE `hapus_kategori` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `kategori_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `hapus_kategori`
--

INSERT INTO `hapus_kategori` (`id`, `user_id`, `kategori_id`) VALUES
(6, 15, 1),
(7, 15, 5),
(10, 15, 4);

-- --------------------------------------------------------

--
-- Table structure for table `saldo`
--

CREATE TABLE `saldo` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `balance` decimal(15,2) DEFAULT NULL,
  `kritis` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `saldo`
--

INSERT INTO `saldo` (`id`, `user_id`, `balance`, `kritis`) VALUES
(5, 15, '2000000.00', '1000000.00');

-- --------------------------------------------------------

--
-- Table structure for table `target`
--

CREATE TABLE `target` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `nama_target` varchar(512) NOT NULL,
  `harga_barang` decimal(30,2) DEFAULT NULL,
  `keterangan` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `target`
--

INSERT INTO `target` (`id`, `user_id`, `nama_target`, `harga_barang`, `keterangan`) VALUES
(6, 16, 'mobil', '2000000.00', 'Mobil bekas'),
(7, 15, 'Dajjal', '2000000000.00', 'Dajjal #!!@'),
(8, 15, 'Adopsi Anjing', '50000.00', 'Punya tetangga');

-- --------------------------------------------------------

--
-- Table structure for table `transac`
--

CREATE TABLE `transac` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `nominal` decimal(15,2) NOT NULL,
  `keterangan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `kategori_id` int NOT NULL,
  `tipe_kategori` int NOT NULL,
  `tipe_pembayaran` enum('Cash','Transfer') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` date NOT NULL,
  `tipe_transaksi` enum('pemasukan','pengeluaran') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `transac`
--

INSERT INTO `transac` (`id`, `user_id`, `nominal`, `keterangan`, `kategori_id`, `tipe_kategori`, `tipe_pembayaran`, `date`, `tipe_transaksi`) VALUES
(5, 15, '20000.00', 'TESTING', 0, 0, 'Transfer', '2023-12-03', 'pemasukan'),
(6, 15, '20000.00', 'testing', 15, 0, 'Cash', '2023-12-08', 'pengeluaran'),
(7, 15, '2000000.00', 'tetssss', 22, 0, 'Cash', '2023-12-08', 'pemasukan'),
(8, 15, '200000.00', 'ini pemasukan', 11, 1, 'Cash', '2023-12-09', 'pemasukan');

-- --------------------------------------------------------

--
-- Table structure for table `transac_kategori`
--

CREATE TABLE `transac_kategori` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `tipe` enum('pemasukan','pengeluaran') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `transac_kategori`
--

INSERT INTO `transac_kategori` (`id`, `name`, `tipe`) VALUES
(1, 'Makanan', 'pengeluaran'),
(2, 'Minuman', 'pengeluaran'),
(3, 'Transportasi', 'pengeluaran'),
(4, 'Pajak', 'pengeluaran'),
(5, 'Pulsa', 'pengeluaran'),
(6, 'Tagihan', 'pengeluaran'),
(7, 'Gaji', 'pemasukan'),
(8, 'Investasi', 'pemasukan'),
(9, 'Penjualan', 'pemasukan'),
(10, 'Deposito', 'pemasukan'),
(11, 'Penyewaan', 'pemasukan'),
(12, 'Tabungan', 'pemasukan');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `last_edited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remember_me` tinyint(1) DEFAULT NULL,
  `hash` varchar(5) DEFAULT NULL,
  `pincode` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `last_edited`, `remember_me`, `hash`, `pincode`) VALUES
(15, 'Revo', '972e762737e769b7766e05294dfbd9aaecb500a0adb65698caa0a9f6cd38537a', '2023-12-08 00:20:59', 1, 'OZaI6', '3e6961d825a5c60a2be1d5bc07932ea280129500d502c97d09af890b3079016a');

-- --------------------------------------------------------

--
-- Table structure for table `user_kategori`
--

CREATE TABLE `user_kategori` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `tipe` enum('pemasukan','pengeluaran') NOT NULL,
  `transac_kategori_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_kategori`
--

INSERT INTO `user_kategori` (`id`, `user_id`, `name`, `tipe`, `transac_kategori_id`) VALUES
(5, 15, 'Minuman Enak test', 'pengeluaran', 2),
(6, 15, 'Gaji dari Elon', 'pemasukan', 7),
(8, 15, 'Transportasi Umum', 'pengeluaran', 3),
(13, 15, 'Makanan Kantin', 'pengeluaran', 1),
(15, 15, 'kategori newsss', 'pengeluaran', 0),
(22, 15, 'Tabungan Keluarga', 'pemasukan', 12),
(23, 15, 'testing', 'pengeluaran', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hapus_kategori`
--
ALTER TABLE `hapus_kategori`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `saldo`
--
ALTER TABLE `saldo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_unique` (`user_id`);

--
-- Indexes for table `target`
--
ALTER TABLE `target`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_id_target` (`user_id`);

--
-- Indexes for table `transac`
--
ALTER TABLE `transac`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_transac` (`user_id`);

--
-- Indexes for table `transac_kategori`
--
ALTER TABLE `transac_kategori`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_kategori`
--
ALTER TABLE `user_kategori`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_kategori` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hapus_kategori`
--
ALTER TABLE `hapus_kategori`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `saldo`
--
ALTER TABLE `saldo`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `target`
--
ALTER TABLE `target`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `transac`
--
ALTER TABLE `transac`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `transac_kategori`
--
ALTER TABLE `transac_kategori`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `user_kategori`
--
ALTER TABLE `user_kategori`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `saldo`
--
ALTER TABLE `saldo`
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `transac`
--
ALTER TABLE `transac`
  ADD CONSTRAINT `FK_transac` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `user_kategori`
--
ALTER TABLE `user_kategori`
  ADD CONSTRAINT `fk_user_kategori` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
