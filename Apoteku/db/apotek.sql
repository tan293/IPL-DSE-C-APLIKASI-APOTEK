-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 28 Des 2019 pada 22.02
-- Versi server: 10.4.6-MariaDB
-- Versi PHP: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
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
-- Struktur dari tabel `kasir`
--

CREATE TABLE `kasir` (
  `id_jual` int(5) NOT NULL,
  `kode_transaksi` int(10) NOT NULL,
  `kode_obat` varchar(10) NOT NULL,
  `nama_obat` varchar(100) NOT NULL,
  `merek_obat` varchar(100) NOT NULL,
  `harga_jual` int(11) NOT NULL,
  `jumlah_beli` int(11) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `tanggal_transaksi` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `medicine`
--

CREATE TABLE `medicine` (
  `id_obat` int(11) NOT NULL,
  `kode_obat` varchar(20) NOT NULL,
  `nama_obat` varchar(100) NOT NULL,
  `kategori_obat` varchar(15) NOT NULL,
  `jenis_obat` varchar(20) NOT NULL,
  `merek_obat` varchar(100) NOT NULL,
  `harga_beli_obat` decimal(10,0) NOT NULL,
  `harga_jual_obat` decimal(10,0) NOT NULL,
  `jumlah_obat` int(3) NOT NULL,
  `tanggal_masuk` timestamp NOT NULL DEFAULT current_timestamp(),
  `expired` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `medicine`
--

INSERT INTO `medicine` (`id_obat`, `kode_obat`, `nama_obat`, `kategori_obat`, `jenis_obat`, `merek_obat`, `harga_beli_obat`, `harga_jual_obat`, `jumlah_obat`, `tanggal_masuk`, `expired`) VALUES
(27, 'A1', 'Bodrek', 'Obat Dalam', 'Pil', 'Bodrek', '2500', '3000', 267, '2019-12-26 09:28:46', '2019-12-26'),
(28, 'A2', 'Inzana', 'Obat Dalam', 'Pil', 'Inzana', '1000', '2000', 270, '2019-12-26 09:29:59', '2019-12-26'),
(29, 'A3', 'Komix', 'Obat Dalam', 'Botol', 'Komiko', '10000', '15000', 1, '2019-12-26 09:31:36', '2020-12-31'),
(30, 'A4', 'Salep 88', 'Obat Luar', 'Salep', 'Salepku', '20000', '25000', 273, '2019-12-26 09:32:54', '2019-12-26'),
(31, 'A5', 'Oskadon', 'Obat Dalam', 'Pil', 'Oskadon SP', '3000', '4500', 0, '2019-12-26 09:33:35', '2020-12-31');

-- --------------------------------------------------------

--
-- Struktur dari tabel `nota`
--

CREATE TABLE `nota` (
  `id_jual` int(5) NOT NULL,
  `kode_transaksi` varchar(40) NOT NULL,
  `Nama_Pembeli` varchar(40) NOT NULL,
  `kode_obat` varchar(10) NOT NULL,
  `nama_obat` varchar(100) NOT NULL,
  `merek_obat` varchar(100) NOT NULL,
  `harga_jual` int(20) NOT NULL,
  `jumlah_beli` int(20) NOT NULL,
  `total_harga` int(20) NOT NULL,
  `Total_Belanja` int(30) NOT NULL,
  `Bayar` int(30) NOT NULL,
  `Kembali` int(30) NOT NULL,
  `tanggal_transaksi` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `nota`
--

INSERT INTO `nota` (`id_jual`, `kode_transaksi`, `Nama_Pembeli`, `kode_obat`, `nama_obat`, `merek_obat`, `harga_jual`, `jumlah_beli`, `total_harga`, `Total_Belanja`, `Bayar`, `Kembali`, `tanggal_transaksi`) VALUES
(242, 'AP/26122019/000001', 'Ikhsan Maulana', 'A1', 'Bodrek', 'Bodrek', 3000, 1, 3000, 0, 0, 0, '26-12-2019'),
(243, 'AP/26122019/000002', 'Ikhsan Maulana', 'A2', 'Inzana', 'Inzana', 2000, 2, 4000, 0, 0, 0, '26-12-2019'),
(244, 'AP/26122019/000003', 'Ikhsan Maulana', 'A1', 'Bodrek', 'Bodrek', 3000, 3, 9000, 0, 0, 0, '26-12-2019'),
(245, 'AP/26122019/000004', 'Ikhsan Maulana', 'A2', 'Inzana', 'Inzana', 2000, 5, 10000, 0, 0, 0, '26-12-2019');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan`
--

CREATE TABLE `penjualan` (
  `Nomor` int(10) NOT NULL,
  `Kode_Transaksi` varchar(60) NOT NULL,
  `Nama_Pembeli` varchar(40) NOT NULL,
  `Kode_Obat` varchar(10) NOT NULL,
  `Nama_Obat` varchar(100) NOT NULL,
  `Merek_Obat` varchar(100) NOT NULL,
  `Harga_Jual` int(20) NOT NULL,
  `Jumlah_Beli` int(20) NOT NULL,
  `Total_Harga` int(20) NOT NULL,
  `Tanggal_Transaksi` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penjualan`
--

INSERT INTO `penjualan` (`Nomor`, `Kode_Transaksi`, `Nama_Pembeli`, `Kode_Obat`, `Nama_Obat`, `Merek_Obat`, `Harga_Jual`, `Jumlah_Beli`, `Total_Harga`, `Tanggal_Transaksi`) VALUES
(1, 'AP/29122019/000001', 'A', 'A1', 'Bodrek', 'Bodrek', 3000, 1, 3000, '29-12-2019');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jenis_kelamin` varchar(15) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `akses` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  `email` varchar(50) NOT NULL,
  `no_hp` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `nama`, `jenis_kelamin`, `username`, `password`, `akses`, `alamat`, `email`, `no_hp`) VALUES
(18, 'admin', 'Perempuan', 'admin', 'admin', 'Admin', '', '', ''),
(20, 'Ikhsan', 'Laki-Laki', 'Ikhsan Maulana', '123', 'Admin', 'Banioro', 'ikhsanmaulanasanna@gmail.com', '085921254662');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `kasir`
--
ALTER TABLE `kasir`
  ADD PRIMARY KEY (`id_jual`);

--
-- Indeks untuk tabel `medicine`
--
ALTER TABLE `medicine`
  ADD PRIMARY KEY (`id_obat`),
  ADD UNIQUE KEY `kode_obat` (`kode_obat`),
  ADD UNIQUE KEY `kode_obat_2` (`kode_obat`),
  ADD UNIQUE KEY `kode_obat_3` (`kode_obat`),
  ADD UNIQUE KEY `kode_obat_4` (`kode_obat`),
  ADD UNIQUE KEY `kode_obat_5` (`kode_obat`),
  ADD UNIQUE KEY `kode_obat_7` (`kode_obat`),
  ADD UNIQUE KEY `id_obat` (`id_obat`),
  ADD UNIQUE KEY `kode_obat_8` (`kode_obat`),
  ADD KEY `kode_obat_6` (`kode_obat`);

--
-- Indeks untuk tabel `nota`
--
ALTER TABLE `nota`
  ADD PRIMARY KEY (`id_jual`);

--
-- Indeks untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`Nomor`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `kasir`
--
ALTER TABLE `kasir`
  MODIFY `id_jual` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=200;

--
-- AUTO_INCREMENT untuk tabel `medicine`
--
ALTER TABLE `medicine`
  MODIFY `id_obat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT untuk tabel `nota`
--
ALTER TABLE `nota`
  MODIFY `id_jual` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=246;

--
-- AUTO_INCREMENT untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  MODIFY `Nomor` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
