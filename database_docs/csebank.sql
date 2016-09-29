/**
 * Database creation script file
 *
 * @package    CSE-Bank
 * @authors    1. Mihir Thakkar (Task #20)
 *             2. 
 *             3.
 */

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `csebank` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `csebank`;

DROP TABLE IF EXISTS `Account`;
CREATE TABLE `Account` (
  `AccountId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `AccType` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `AccOpenDate` date NOT NULL,
  `AccBalance` int(11) NOT NULL,
  `AccStatus` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `AccountLog`;
CREATE TABLE `AccountLog` (
  `AccountId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `AccountBalance` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Credit`;
CREATE TABLE `Credit` (
  `CreditAccId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `CreditLimit` int(11) NOT NULL,
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Devices`;
CREATE TABLE `Devices` (
  `DeviceId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `KYCRequest`;
CREATE TABLE `KYCRequest` (
  `UserfieldId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `FieldValue` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Status` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Session`;
CREATE TABLE `Session` (
  `SessionKey` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `SessionStart` datetime NOT NULL,
  `SessionEnd` datetime NOT NULL,
  `SessionRequest` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `SessionTimeout` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `SessionOTP` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Transaction`;
CREATE TABLE `Transaction` (
  `TransId` int(11) NOT NULL,
  `TransType` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransDescription` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransStatus` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransSrcAccNo` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransDestAccNo` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransOwner` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TransApprovedBy` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransAmount` int(11) NOT NULL,
  `TransComments` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TransResult` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `UserId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserRole` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `FirstName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `LastName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `SSN` int(11) NOT NULL,
  `DOB` date NOT NULL,
  `Password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserStatus` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `LoginAttempt` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `SecurityQn` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `SecurityAns` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Organization` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


ALTER TABLE `Account`
  ADD PRIMARY KEY (`AccountId`),
  ADD KEY `UserId` (`UserId`);

ALTER TABLE `AccountLog`
  ADD KEY `AccountId` (`AccountId`);

ALTER TABLE `Credit`
  ADD PRIMARY KEY (`CreditAccId`),
  ADD KEY `UserId` (`UserId`);

ALTER TABLE `Devices`
  ADD PRIMARY KEY (`DeviceId`),
  ADD KEY `UserId` (`UserId`);

ALTER TABLE `KYCRequest`
  ADD KEY `UserId` (`UserId`);

ALTER TABLE `Session`
  ADD UNIQUE KEY `SessionKey` (`SessionKey`),
  ADD KEY `UserId` (`UserId`);

ALTER TABLE `Transaction`
  ADD PRIMARY KEY (`TransId`);

ALTER TABLE `User`
  ADD PRIMARY KEY (`UserId`);


ALTER TABLE `Account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`);

ALTER TABLE `AccountLog`
  ADD CONSTRAINT `accountlog_ibfk_1` FOREIGN KEY (`AccountId`) REFERENCES `Account` (`AccountId`);

ALTER TABLE `Credit`
  ADD CONSTRAINT `credit_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`);

ALTER TABLE `Devices`
  ADD CONSTRAINT `devices_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`);

ALTER TABLE `KYCRequest`
  ADD CONSTRAINT `kycrequest_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`);

ALTER TABLE `Session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
