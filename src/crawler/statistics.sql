CREATE SCHEMA `statistics` ;

USE statistics;

DROP TABLE IF EXISTS `keywords`;
CREATE TABLE `keywords` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` nvarchar(2048) NOT NULL,
  `PersonID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Namesofpersons_idx` (`PersonID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `pages`;
CREATE TABLE `pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` nvarchar(2048) NOT NULL,
  `siteid` int(11) NOT NULL,
  `founddatetime` datetime NOT NULL,
  `lastscandate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_siteid_idx` (`siteid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `personpagerank`;
CREATE TABLE `personpagerank` (
  `personid` int(11) NOT NULL,
  `pageid` int(11) NOT NULL,
  `rank` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`personid`,`pageid`),
  KEY `fk_pageid_idx` (`pageid`),
  KEY `fk_personid_idx` (`personid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `persons`;
CREATE TABLE `persons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` nvarchar(2048) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sites`;
CREATE TABLE `sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` nvarchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `keywords` 
ADD CONSTRAINT `fk_Namesofpersons` FOREIGN KEY (`PersonID`) REFERENCES `persons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `pages` 
ADD CONSTRAINT `fk_siteid` FOREIGN KEY (`siteid`) REFERENCES `sites` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `personpagerank` 
ADD CONSTRAINT `fk_pageid` FOREIGN KEY (`pageid`) REFERENCES `pages` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_personid` FOREIGN KEY (`personid`) REFERENCES `persons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
#два первичных ключа образуют связку. пара pageid и personid будут составлять уникальный элемент