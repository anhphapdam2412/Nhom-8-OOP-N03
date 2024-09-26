-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: csdl_qlhs
-- ------------------------------------------------------
-- Server version	8.0.38

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `thongtinhocsinh`
--

DROP TABLE IF EXISTS `thongtinhocsinh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thongtinhocsinh` (
  `maHS` varchar(45) NOT NULL,
  `hoDem` varchar(45) DEFAULT NULL,
  `ten` varchar(45) DEFAULT NULL,
  `ngaySinh` date DEFAULT NULL,
  `gioiTinh` tinyint(1) DEFAULT NULL,
  `maDinhDanh` varchar(45) DEFAULT NULL,
  `sdt` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `lop` varchar(45) DEFAULT NULL,
  `diaChi` text,
  `trangThai` tinyint(1) DEFAULT NULL,
  `ghichuTT` text,
  PRIMARY KEY (`maHS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thongtinhocsinh`
--

LOCK TABLES `thongtinhocsinh` WRITE;
/*!40000 ALTER TABLE `thongtinhocsinh` DISABLE KEYS */;
INSERT INTO `thongtinhocsinh` VALUES ('24000000','anhdam','phap','2024-09-19',0,'02','123','123','321','An Giang, Châu Phú, Bình Phú, 531',NULL,'513'),('24000001','anhphap','123','2024-09-06',0,'321','123','321','321','An Giang, Châu Phú, Bình Phú, 123',NULL,'321'),('24000002','123','123','2024-09-05',0,'1321','321','123','123','Bình Dương, Dĩ An, null, 12312',NULL,'312'),('24000003','','','2024-09-12',0,'','','','','Bà Rịa - Vũng Tàu, Côn Đảo, null, 12332',NULL,''),('24000004','','','2024-09-13',0,'','','','','null, null, null, ',NULL,''),('24000005','dsa321312','dsa','2024-09-12',0,'123','123','123','23','Bà Rịa - Vũng Tàu, Côn Đảo, null, 12332',NULL,'123'),('24000006','','','2024-09-13',0,'','','','','null, null, null,  ',NULL,''),('24000007','','','2024-09-12',0,'','','','','null, null, null, .',NULL,''),('24000008','','','2024-09-06',0,'','','','','',NULL,''),('24000009','','','2024-09-20',0,'','','','','null, null, null, ',NULL,''),('24000010','','','2024-09-06',0,'','','','','null, null, null, 1',NULL,''),('24000011','a','a','2024-09-11',0,'','','','','null, null, null, null',NULL,''),('24000012','a','a','2024-09-14',0,'123123123123','0123123123','','','null, null, null, null',NULL,''),('24000013','','','2024-09-27',0,'','','','','null, null, null, null',NULL,''),('24000014','','','2024-09-13',0,'','','','','null, null, null, null',NULL,''),('24000015','a','a','2024-09-12',0,'','','','','null, null, null, null',NULL,'');
/*!40000 ALTER TABLE `thongtinhocsinh` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-25 23:48:19
