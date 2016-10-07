SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `csebank` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `csebank`;

DROP TABLE IF EXISTS `Account`;
CREATE TABLE IF NOT EXISTS `Account` (
  `AccountId` bigint(16) UNSIGNED NOT NULL,
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `AccType` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `AccOpenDate` date NOT NULL,
  `AccBalance` int(11) NOT NULL,
  `AccStatus` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`AccountId`),
  UNIQUE KEY `index_accountid` (`AccountId`),
  UNIQUE KEY `AccountId` (`AccountId`),
  KEY `UserId` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `AccountLog`;
CREATE TABLE IF NOT EXISTS `AccountLog` (
  `TransId` int(11) NOT NULL,
  `AccountBalance` int(11) NOT NULL,
  `AccountId` bigint(16) UNSIGNED NOT NULL,
  UNIQUE KEY `AccountId` (`AccountId`),
  KEY `TransId` (`TransId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Credit`;
CREATE TABLE IF NOT EXISTS `Credit` (
  `CreditAccId` bigint(16) UNSIGNED NOT NULL,
  `CreditBalance` int(11) NOT NULL,
  `CreditLimit` int(11) NOT NULL,
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`CreditAccId`),
  KEY `UserId` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Devices`;
CREATE TABLE IF NOT EXISTS `Devices` (
  `DeviceId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`DeviceId`),
  KEY `UserId` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `KYCRequest`;
CREATE TABLE IF NOT EXISTS `KYCRequest` (
  `UserfieldId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `FieldValue` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Status` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  KEY `UserId` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Session`;
CREATE TABLE IF NOT EXISTS `Session` (
  `SessionKey` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `SessionStart` datetime NOT NULL,
  `SessionEnd` datetime NOT NULL,
  `SessionRequest` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `SessionTimeout` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `SessionOTP` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  UNIQUE KEY `SessionKey` (`SessionKey`),
  KEY `UserId` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Transaction`;
CREATE TABLE IF NOT EXISTS `Transaction` (
  `TransId` int(11) NOT NULL,
  `TransType` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'non-critical',
  `TransDescription` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransStatus` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransSrcAccNo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TransDestAccNo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TransOwner` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransTimestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TransApprovedBy` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransAmount` int(11) NOT NULL,
  `TransComments` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TransResult` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`TransId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserRole` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `FirstName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `LastName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `SSN` int(11) NOT NULL,
  `DOB` date NOT NULL,
  `Password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserStatus` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `LoginAttempt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SecurityQn` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `SecurityAns` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Organization` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


ALTER TABLE `Account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`);

ALTER TABLE `AccountLog`
  ADD CONSTRAINT `accountlog_ibfk_1` FOREIGN KEY (`AccountId`) REFERENCES `Account` (`AccountId`),
  ADD CONSTRAINT `accountlog_ibfk_2` FOREIGN KEY (`TransId`) REFERENCES `Transaction` (`TransId`);

ALTER TABLE `Credit`
  ADD CONSTRAINT `credit_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`),
  ADD CONSTRAINT `credit_ibfk_2` FOREIGN KEY (`CreditAccId`) REFERENCES `Account` (`AccountId`);

ALTER TABLE `Devices`
  ADD CONSTRAINT `devices_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`);

ALTER TABLE `KYCRequest`
  ADD CONSTRAINT `kycrequest_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`);

ALTER TABLE `Session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
