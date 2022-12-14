-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 14 Des 2022 pada 04.30
-- Versi server: 10.4.19-MariaDB
-- Versi PHP: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apotek`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `dokter`
--

CREATE TABLE `dokter` (
  `ID_Dokter` int(5) NOT NULL,
  `Nama` varchar(30) NOT NULL,
  `Jenis_Kelamin` varchar(10) NOT NULL,
  `Alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `obat`
--

CREATE TABLE `obat` (
  `ID_Obat` int(5) NOT NULL,
  `Nama` varchar(30) NOT NULL,
  `Jenis` text NOT NULL,
  `Harga` varchar(20) NOT NULL,
  `Exp_Date` date NOT NULL,
  `Aturan_Pakai` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `pasien`
--

CREATE TABLE `pasien` (
  `ID_Pasien` int(5) NOT NULL,
  `Nama` varchar(30) NOT NULL,
  `Jenis_Kelamin` varchar(10) NOT NULL,
  `Alamat` text NOT NULL,
  `Tgl_Lahir` date NOT NULL,
  `No_HP` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `pemeriksaan`
--

CREATE TABLE `pemeriksaan` (
  `ID_Pemeriksaan` int(5) NOT NULL,
  `ID_Pasien` int(5) NOT NULL,
  `ID_Dokter` int(5) NOT NULL,
  `Tgl_Pemeriksaan` datetime NOT NULL,
  `Jenis_Pemeriksaan` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `ID_Transaksi` int(5) NOT NULL,
  `ID_Obat` int(5) NOT NULL,
  `ID_Pasien` int(5) NOT NULL,
  `Total_Harga` varchar(10) NOT NULL,
  `Tgl_Transaksi` datetime NOT NULL,
  `Jenis_Transaksi` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `dokter`
--
ALTER TABLE `dokter`
  ADD PRIMARY KEY (`ID_Dokter`);

--
-- Indeks untuk tabel `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`ID_Obat`);

--
-- Indeks untuk tabel `pasien`
--
ALTER TABLE `pasien`
  ADD PRIMARY KEY (`ID_Pasien`);

--
-- Indeks untuk tabel `pemeriksaan`
--
ALTER TABLE `pemeriksaan`
  ADD PRIMARY KEY (`ID_Pemeriksaan`),
  ADD KEY `ID_Pasien` (`ID_Pasien`),
  ADD KEY `ID_Dokter` (`ID_Dokter`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`ID_Transaksi`),
  ADD KEY `ID_Obat` (`ID_Obat`),
  ADD KEY `ID_Pasien` (`ID_Pasien`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `pemeriksaan`
--
ALTER TABLE `pemeriksaan`
  ADD CONSTRAINT `pemeriksaan_ibfk_1` FOREIGN KEY (`ID_Pasien`) REFERENCES `pasien` (`ID_Pasien`),
  ADD CONSTRAINT `pemeriksaan_ibfk_2` FOREIGN KEY (`ID_Dokter`) REFERENCES `dokter` (`ID_Dokter`);

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`ID_Obat`) REFERENCES `obat` (`ID_Obat`),
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`ID_Pasien`) REFERENCES `pasien` (`ID_Pasien`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
