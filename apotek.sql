-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 30 Nov 2022 pada 10.50
-- Versi server: 10.4.19-MariaDB
-- Versi PHP: 7.3.28

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
-- Struktur dari tabel `tb_obat`
--

CREATE TABLE `tb_obat` (
  `id_Obat` char(10) NOT NULL,
  `Nama_Obat` varchar(25) NOT NULL,
  `Jenis_Obat` varchar(25) NOT NULL,
  `Harga` varchar(25) NOT NULL,
  `Exp_Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_pembeli`
--

CREATE TABLE `tb_pembeli` (
  `id_Pembeli` char(10) NOT NULL,
  `Nama_Pembeli` varchar(25) NOT NULL,
  `Tgl_Lahir` date NOT NULL,
  `Alamat` varchar(50) NOT NULL,
  `No_HP` int(12) NOT NULL,
  `id_Registrasi` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_registrasi`
--

CREATE TABLE `tb_registrasi` (
  `id_Registrasi` char(10) NOT NULL,
  `Nama_Pengguna` varchar(25) NOT NULL,
  `Email` varchar(25) NOT NULL,
  `No_Telepon` int(12) NOT NULL,
  `Password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_transaksi`
--

CREATE TABLE `tb_transaksi` (
  `id_Transaksi` char(10) NOT NULL,
  `id_Pembeli` char(10) NOT NULL,
  `id_Obat` char(10) NOT NULL,
  `Jumlah_Obat` varchar(10) NOT NULL,
  `Harga_Obat` varchar(25) NOT NULL,
  `Total_Harga` varchar(25) NOT NULL,
  `Tgl_Pembelian` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_obat`
--
ALTER TABLE `tb_obat`
  ADD PRIMARY KEY (`id_Obat`);

--
-- Indeks untuk tabel `tb_pembeli`
--
ALTER TABLE `tb_pembeli`
  ADD PRIMARY KEY (`id_Pembeli`),
  ADD UNIQUE KEY `id_Registrasi` (`id_Registrasi`);

--
-- Indeks untuk tabel `tb_registrasi`
--
ALTER TABLE `tb_registrasi`
  ADD PRIMARY KEY (`id_Registrasi`);

--
-- Indeks untuk tabel `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  ADD PRIMARY KEY (`id_Transaksi`),
  ADD UNIQUE KEY `id_Obat` (`id_Obat`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tb_obat`
--
ALTER TABLE `tb_obat`
  ADD CONSTRAINT `tb_obat_ibfk_1` FOREIGN KEY (`id_Obat`) REFERENCES `tb_transaksi` (`id_Obat`);

--
-- Ketidakleluasaan untuk tabel `tb_registrasi`
--
ALTER TABLE `tb_registrasi`
  ADD CONSTRAINT `tb_registrasi_ibfk_1` FOREIGN KEY (`id_Registrasi`) REFERENCES `tb_pembeli` (`id_Pembeli`);

--
-- Ketidakleluasaan untuk tabel `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  ADD CONSTRAINT `tb_transaksi_ibfk_1` FOREIGN KEY (`id_Transaksi`) REFERENCES `tb_pembeli` (`id_Pembeli`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
