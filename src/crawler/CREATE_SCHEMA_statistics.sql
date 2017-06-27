CREATE SCHEMA IF NOT EXISTS `statistics` CHARACTER SET utf8 COLLATE utf8_general_ci;

USE statistics;

DROP TABLE IF EXISTS `persons`;
CREATE TABLE `persons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(2048) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sites`;
CREATE TABLE `sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `keywords`;
CREATE TABLE `keywords` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(2048) NOT NULL,
  `PersonID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Namesofpersons_idx` (`PersonID`),
  CONSTRAINT `fk_Namesofpersons` FOREIGN KEY (`PersonID`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `pages`;
CREATE TABLE `pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(2048) NOT NULL,
  `siteid` int(11) NOT NULL,
  `founddatetime` datetime NOT NULL,
  `lastscandate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_siteid_idx` (`siteid`),
  CONSTRAINT `fk_siteid` FOREIGN KEY (`siteid`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `personpagerank`;
CREATE TABLE `personpagerank` (
  `personid` int(11) NOT NULL,
  `pageid` int(11) NOT NULL,
  `rank` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`personid`,`pageid`),
  KEY `fk_pageid_idx` (`pageid`),
  KEY `fk_personid_idx` (`personid`),
  CONSTRAINT `fk_pageid` FOREIGN KEY (`pageid`) REFERENCES `pages` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_personid` FOREIGN KEY (`personid`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `siteblock`;
CREATE TABLE `siteblock` (
  `siteid` int(11) NOT NULL,
  `isblocked` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`siteid`),
  KEY `fk_siteidsiteblock_idx` (`siteid`),
  CONSTRAINT `fk_siteidsiteblock` FOREIGN KEY (`siteid`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;