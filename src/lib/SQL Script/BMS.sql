CREATE DATABASE  IF NOT EXISTS `bms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `bms`;
-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: bms
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agencyproduct`
--

DROP TABLE IF EXISTS `agencyproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `agencyproduct` (
  `APID` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `APName` varchar(80) NOT NULL,
  `APTotalUnits` int(11) NOT NULL,
  `APWeightOfUnit` varchar(45) NOT NULL,
  `APBuyingPricePerUnit` float NOT NULL,
  `APMarketPricePerUnit` float NOT NULL,
  `APSellingPricePerUnit` float NOT NULL,
  `APMDate` varchar(10) NOT NULL,
  `APEDate` varchar(10) NOT NULL,
  `APADate` varchar(10) NOT NULL,
  `APDADate` varchar(10) NOT NULL,
  PRIMARY KEY (`APID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `allowance`
--

DROP TABLE IF EXISTS `allowance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `allowance` (
  `AID` int(4) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `ATitle` varchar(45) NOT NULL,
  `ADescription` varchar(100) NOT NULL,
  `AType` varchar(15) NOT NULL,
  `AValue` float NOT NULL,
  PRIMARY KEY (`AID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `allowancepay`
--

DROP TABLE IF EXISTS `allowancepay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `allowancepay` (
  `APEID` int(5) unsigned zerofill NOT NULL,
  `APAID` int(4) unsigned zerofill NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bakeryproduct`
--

DROP TABLE IF EXISTS `bakeryproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bakeryproduct` (
  `BPID` int(4) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `BPName` varchar(80) NOT NULL,
  `BPType` varchar(15) NOT NULL,
  `BPWeight` varchar(45) NOT NULL,
  `BPDescription` varchar(250) NOT NULL,
  `BPPrice` float NOT NULL,
  `BPStatus` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`BPID`),
  UNIQUE KEY `BPID_UNIQUE` (`BPID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `basicsalaryscheme`
--

DROP TABLE IF EXISTS `basicsalaryscheme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `basicsalaryscheme` (
  `BSSID` int(4) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `BSSTitle` varchar(45) NOT NULL,
  `BSSAmount` float NOT NULL,
  `BSSAddedDate` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`BSSID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bill` (
  `BNo` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `BCashierID` varchar(45) NOT NULL,
  `BDate` date NOT NULL,
  `BTime` varchar(8) NOT NULL,
  `BClearance` varchar(20) NOT NULL,
  PRIMARY KEY (`BNo`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `employee` (
  `EID` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `EName` varchar(100) NOT NULL,
  `ENIC` varchar(12) NOT NULL,
  `EAddress` varchar(100) NOT NULL,
  `EGender` varchar(6) NOT NULL,
  `EDOB` date NOT NULL,
  `ETitle` varchar(45) NOT NULL,
  `EPhone` int(10) NOT NULL,
  `EBankName` varchar(40) NOT NULL,
  `EAccNo` bigint(20) NOT NULL,
  `EBSSID` int(4) NOT NULL,
  PRIMARY KEY (`EID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `incomestatement`
--

DROP TABLE IF EXISTS `incomestatement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `incomestatement` (
  `ISID` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `ISType` varchar(15) DEFAULT NULL,
  `ISPeriod` varchar(20) DEFAULT NULL,
  `ISAmount` double DEFAULT NULL,
  PRIMARY KEY (`ISID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `item` (
  `IID` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `IName` varchar(80) NOT NULL,
  `IUnitsPerBlock` int(11) DEFAULT NULL,
  `IBlocks` int(11) DEFAULT NULL,
  `IWeightOfUnit` varchar(45) NOT NULL,
  `IBuyingPricePerUnit` float NOT NULL,
  `IExpireDate` date NOT NULL,
  `IAddedDate` date NOT NULL,
  `IMinQuantityLimit` int(11) NOT NULL,
  `IAvailableQuantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`IID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `itemwithdraw`
--

DROP TABLE IF EXISTS `itemwithdraw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `itemwithdraw` (
  `IWID` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `IWIID` int(11) unsigned zerofill NOT NULL,
  `IWDescription` varchar(100) NOT NULL,
  `IWQuantity` int(11) NOT NULL,
  `IWUser` varchar(45) NOT NULL,
  `IWDate` date NOT NULL,
  `IWTime` varchar(10) NOT NULL,
  PRIMARY KEY (`IWID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jobtitle`
--

DROP TABLE IF EXISTS `jobtitle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jobtitle` (
  `JTID` int(11) NOT NULL,
  `JTitle` varchar(45) NOT NULL,
  PRIMARY KEY (`JTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordermenuitem`
--

DROP TABLE IF EXISTS `ordermenuitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ordermenuitem` (
  `OMIID` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `OMIImage` longblob,
  `OMIName` varchar(80) DEFAULT NULL,
  `OMIDescription` varchar(250) DEFAULT NULL,
  `OMIWeight` varchar(45) DEFAULT NULL,
  `OMIPrice` float DEFAULT NULL,
  `OMIStatus` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`OMIID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `OID` int(6) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `OOMID` int(5) unsigned zerofill DEFAULT NULL,
  `OType` varchar(100) DEFAULT NULL,
  `ODetails` varchar(100) DEFAULT NULL,
  `OQuantity` varchar(45) DEFAULT NULL,
  `ODeliveryDate` date DEFAULT NULL,
  `ODeliveryTime` varchar(50) DEFAULT NULL,
  `OCustomerName` varchar(80) DEFAULT NULL,
  `OCustomerNIC` varchar(12) DEFAULT NULL,
  `OCustomerPhone` int(10) DEFAULT NULL,
  `OTakenDate` date DEFAULT NULL,
  `OTakenTime` varchar(50) DEFAULT NULL,
  `OTakenUID` int(5) unsigned zerofill DEFAULT NULL,
  `OStatus` varchar(20) DEFAULT NULL,
  `ODeliveredDate` date DEFAULT NULL,
  `ODeliveredTime` varchar(50) DEFAULT NULL,
  `OProcessingStatus` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`OID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `otherexpenses`
--

DROP TABLE IF EXISTS `otherexpenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `otherexpenses` (
  `OEID` int(7) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `OETitle` varchar(45) NOT NULL,
  `OEDescription` varchar(100) NOT NULL,
  `OEPeriod` varchar(45) NOT NULL,
  `OEAmount` double NOT NULL,
  `OEPaidDate` varchar(12) NOT NULL,
  `OEAddedDate` varchar(12) NOT NULL,
  PRIMARY KEY (`OEID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `paysheet`
--

DROP TABLE IF EXISTS `paysheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `paysheet` (
  `PSID` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `PSEID` int(5) unsigned zerofill DEFAULT NULL,
  `PSEName` varchar(45) DEFAULT NULL,
  `PSENIC` varchar(12) DEFAULT NULL,
  `PSBSSTitle` varchar(45) DEFAULT NULL,
  `PSBSSAmount` double DEFAULT NULL,
  `PSTotalAllowances` double DEFAULT NULL,
  `PSBank` varchar(40) DEFAULT NULL,
  `PSAccount` bigint(20) DEFAULT NULL,
  `PSGeneratedDate` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PSID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `paysheetallowance`
--

DROP TABLE IF EXISTS `paysheetallowance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `paysheetallowance` (
  `PSAID` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `PSAPSID` int(10) unsigned zerofill DEFAULT NULL,
  `PSAAID` int(4) unsigned zerofill DEFAULT NULL,
  `PSAATitle` varchar(45) DEFAULT NULL,
  `PSAAType` varchar(15) DEFAULT NULL,
  `PSAAAmount` float DEFAULT NULL,
  PRIMARY KEY (`PSAID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `purchase` (
  `PID` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `PItemID` int(5) unsigned zerofill NOT NULL DEFAULT '00000',
  `PSupplierID` int(4) unsigned zerofill DEFAULT '0000',
  `PType` varchar(10) NOT NULL,
  `PDate` varchar(10) DEFAULT NULL,
  `PStatus` varchar(15) DEFAULT NULL,
  `PBankInfo` varchar(200) DEFAULT 'None',
  `PBankPaidDate` varchar(12) DEFAULT 'None',
  PRIMARY KEY (`PID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `salesitem`
--

DROP TABLE IF EXISTS `salesitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `salesitem` (
  `SIID` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `SIPID` int(11) unsigned zerofill NOT NULL,
  `SIPName` varchar(45) NOT NULL,
  `SIWeight` varchar(20) NOT NULL,
  `SIQuantity` int(11) NOT NULL,
  `SIUnitPrice` float NOT NULL,
  `SITotalAmount` double NOT NULL,
  `SIBNo` int(11) NOT NULL,
  `SIType` varchar(20) NOT NULL,
  PRIMARY KEY (`SIID`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `supplierinfo`
--

DROP TABLE IF EXISTS `supplierinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `supplierinfo` (
  `SIID` int(4) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `SIName` varchar(80) NOT NULL,
  `SIAddress` varchar(100) NOT NULL,
  `SIPhone1` int(10) NOT NULL,
  `SIPhone2` int(10) DEFAULT NULL,
  `SIEmail` varchar(80) DEFAULT NULL,
  `SIType` varchar(20) DEFAULT NULL,
  `SIBankName` varchar(80) NOT NULL,
  `SIAccNo` bigint(19) NOT NULL,
  PRIMARY KEY (`SIID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `systemuser`
--

DROP TABLE IF EXISTS `systemuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `systemuser` (
  `SUUID` int(11) NOT NULL,
  `SUEID` varchar(45) NOT NULL,
  PRIMARY KEY (`SUUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `UID` varchar(45) NOT NULL,
  `UName` varchar(80) NOT NULL,
  `UPassword` varchar(100) NOT NULL,
  `UType` varchar(11) NOT NULL,
  `UStatus` varchar(10) NOT NULL,
  PRIMARY KEY (`UID`),
  UNIQUE KEY `UID_UNIQUE` (`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'bms'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-06  7:17:23
