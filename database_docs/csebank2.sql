SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

DROP DATABASE IF EXISTS `csebank`;
CREATE DATABASE IF NOT EXISTS `csebank` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `csebank`;

DROP TABLE IF EXISTS `Account`;
CREATE TABLE IF NOT EXISTS `Account` (
  `accountId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `accountNumber` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `routingNumber` int(11) NOT NULL,
  `userId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `accType` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `accOpenDate` date NOT NULL,
  `accBalance` int(11) NOT NULL,
  `accountStatus` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`accountId`),
  UNIQUE KEY `index_accountid` (`accountId`),
  UNIQUE KEY `AccountId` (`accountId`),
  KEY `UserId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `AccountLog`;
CREATE TABLE IF NOT EXISTS `AccountLog` (
  `transId` int(11) NOT NULL,
  `accountBalance` int(11) NOT NULL,
  `accountId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  UNIQUE KEY `AccountId` (`accountId`),
  KEY `TransId` (`transId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Credit`;
CREATE TABLE IF NOT EXISTS `Credit` (
  `creditAccountId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `creditBalance` int(11) NOT NULL,
  `creditLimit` int(11) NOT NULL,
  `userId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`creditAccountId`),
  KEY `UserId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Device`;
CREATE TABLE IF NOT EXISTS `Device` (
  `deviceId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `userId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`deviceId`),
  KEY `UserId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `KYCRequest`;
CREATE TABLE IF NOT EXISTS `KYCRequest` (
  `userFieldId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `userId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `fieldValue` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  KEY `UserId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Session`;
CREATE TABLE IF NOT EXISTS `Session` (
  `sessionKey` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `userId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `sessionStart` datetime NOT NULL,
  `sessionEnd` datetime NOT NULL,
  `sessionRequest` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `sessionTimeout` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `sessionOTP` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  UNIQUE KEY `SessionKey` (`sessionKey`),
  KEY `UserId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `Transaction`;
CREATE TABLE IF NOT EXISTS `Transaction` (
  `transId` int(11) NOT NULL,
  `transType` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'non-critical',
  `transDescription` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `transStatus` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `transSrcAccNo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `transDestAccNo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `transOwner` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `transTimestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `transApprovedBy` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `transAmount` int(11) NOT NULL,
  `transComments` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `transResult` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`transId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
  `userId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `userRole` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `firstName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `lastName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phoneNumber` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `SSN` int(11) NOT NULL,
  `DOB` date NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `userStatus` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `loginAttemptNumber` int(11) NOT NULL DEFAULT '0',
  `securityQn1` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `securityAns1` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `securityQn2` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `securityAns2` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `organization` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


ALTER TABLE `Account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`);

ALTER TABLE `AccountLog`
  ADD CONSTRAINT `accountlog_ibfk_2` FOREIGN KEY (`transId`) REFERENCES `Transaction` (`TransId`),
  ADD CONSTRAINT `accountlog_ibfk_3` FOREIGN KEY (`accountId`) REFERENCES `Account` (`AccountId`);

ALTER TABLE `Credit`
  ADD CONSTRAINT `credit_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`UserId`),
  ADD CONSTRAINT `credit_ibfk_2` FOREIGN KEY (`creditAccountId`) REFERENCES `Account` (`AccountId`);

ALTER TABLE `KYCRequest`
  ADD CONSTRAINT `kycrequest_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`UserId`);

ALTER TABLE `Session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`UserId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
