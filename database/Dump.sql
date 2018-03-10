CREATE DATABASE  IF NOT EXISTS `scouting` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `scouting`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: scouting
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` char(2) NOT NULL,
  `start_date` date NOT NULL,
  `current` tinyint(4) NOT NULL DEFAULT '0',
  `active` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'test','test','MN','2018-01-31',0,0),(2,'Lake Superior Regional 2018','Duluth','MN','2018-03-10',1,1),(3,'Northern Lights Regional 2018','Duluth','MN','2018-03-10',0,1),(4,'Iowa Regional','Cedar Falls','IA','2018-03-21',0,0),(5,'Minnesota North Star Regional','Minneapolis','MN','2018-03-28',0,0);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_survey`
--

DROP TABLE IF EXISTS `event_survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_survey` (
  `event_id` int(11) NOT NULL,
  `survey_id` int(11) NOT NULL,
  PRIMARY KEY (`event_id`,`survey_id`),
  KEY `fk_event_has_survey_survey1_idx` (`survey_id`),
  KEY `fk_event_has_survey_event1_idx` (`event_id`),
  CONSTRAINT `fk_event_has_survey_event1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_has_survey_survey1` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_survey`
--

LOCK TABLES `event_survey` WRITE;
/*!40000 ALTER TABLE `event_survey` DISABLE KEYS */;
INSERT INTO `event_survey` VALUES (1,1),(2,1),(3,1),(1,2),(2,2),(3,2);
/*!40000 ALTER TABLE `event_survey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matchup`
--

DROP TABLE IF EXISTS `matchup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matchup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `match_number` int(11) NOT NULL,
  `type` varchar(45) NOT NULL COMMENT 'The match type describes if this is a regular, semi-final, or final match round.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `matchup_uk1` (`event_id`,`match_number`,`type`),
  KEY `fk_Match_Event1_idx` (`event_id`),
  CONSTRAINT `fk_Match_Event1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matchup`
--

LOCK TABLES `matchup` WRITE;
/*!40000 ALTER TABLE `matchup` DISABLE KEYS */;
INSERT INTO `matchup` VALUES (1,1,'2018-02-23 14:57:00',-1,'pit'),(2,2,'2018-03-10 12:04:00',-1,'pit'),(3,3,'2018-03-10 11:55:00',-1,'pit'),(84,1,'2018-02-23 14:57:00',13,'qualifier'),(85,2,'2018-03-09 17:01:00',61,'qualifier'),(86,2,'2018-03-09 17:08:00',62,'qualifier'),(87,2,'2018-03-09 17:15:00',63,'qualifier'),(88,2,'2018-03-09 17:22:00',64,'qualifier'),(89,2,'2018-03-09 17:28:00',65,'qualifier'),(90,2,'2018-03-09 17:35:00',66,'qualifier'),(91,2,'2018-03-10 09:02:00',67,'qualifier'),(92,2,'2018-03-10 09:09:00',68,'qualifier'),(93,2,'2018-03-10 09:16:00',69,'qualifier'),(94,2,'2018-03-10 09:23:00',70,'qualifier'),(95,2,'2018-03-10 09:30:00',71,'qualifier'),(96,2,'2018-03-10 09:37:00',72,'qualifier'),(97,2,'2018-03-10 09:44:00',73,'qualifier'),(98,2,'2018-03-10 09:51:00',74,'qualifier'),(99,2,'2018-03-10 09:58:00',75,'qualifier'),(100,2,'2018-03-10 10:05:00',76,'qualifier'),(101,2,'2018-03-10 10:12:00',77,'qualifier'),(102,2,'2018-03-10 10:19:00',78,'qualifier'),(103,2,'2018-03-10 10:26:00',79,'qualifier'),(104,2,'2018-03-10 10:33:00',80,'qualifier'),(105,2,'2018-03-10 10:40:00',81,'qualifier'),(106,2,'2018-03-10 10:47:00',82,'qualifier'),(107,2,'2018-03-10 10:54:00',83,'qualifier'),(108,2,'2018-03-10 11:01:00',84,'qualifier'),(109,2,'2018-03-10 11:08:00',85,'qualifier'),(110,2,'2018-03-10 11:15:00',86,'qualifier'),(111,2,'2018-03-10 11:22:00',87,'qualifier'),(112,2,'2018-03-10 11:29:00',88,'qualifier'),(113,2,'2018-03-10 11:36:00',89,'qualifier'),(114,2,'2018-03-10 11:43:00',90,'qualifier'),(115,2,'2018-03-10 11:50:00',91,'qualifier'),(116,2,'2018-03-10 11:57:00',92,'qualifier'),(117,2,'2018-03-10 12:04:00',93,'qualifier'),(151,3,'2018-03-09 17:10:00',58,'qualifier'),(152,3,'2018-03-09 17:17:00',59,'qualifier'),(153,3,'2018-03-09 17:24:00',60,'qualifier'),(154,3,'2018-03-09 17:32:00',61,'qualifier'),(155,3,'2018-03-09 17:39:00',62,'qualifier'),(156,3,'2018-03-10 09:07:00',63,'qualifier'),(157,3,'2018-03-10 09:14:00',64,'qualifier'),(158,3,'2018-03-10 09:21:00',65,'qualifier'),(159,3,'2018-03-10 09:28:00',66,'qualifier'),(160,3,'2018-03-10 09:35:00',67,'qualifier'),(161,3,'2018-03-10 09:42:00',68,'qualifier'),(162,3,'2018-03-10 09:49:00',69,'qualifier'),(163,3,'2018-03-10 09:56:00',70,'qualifier'),(164,3,'2018-03-10 10:03:00',71,'qualifier'),(165,3,'2018-03-10 10:10:00',72,'qualifier'),(166,3,'2018-03-10 10:17:00',73,'qualifier'),(167,3,'2018-03-10 10:24:00',74,'qualifier'),(168,3,'2018-03-10 10:31:00',75,'qualifier'),(169,3,'2018-03-10 10:38:00',76,'qualifier'),(170,3,'2018-03-10 10:45:00',77,'qualifier'),(171,3,'2018-03-10 10:52:00',78,'qualifier'),(172,3,'2018-03-10 10:59:00',79,'qualifier'),(173,3,'2018-03-10 11:06:00',80,'qualifier'),(174,3,'2018-03-10 11:13:00',81,'qualifier'),(175,3,'2018-03-10 11:20:00',82,'qualifier'),(176,3,'2018-03-10 11:27:00',83,'qualifier'),(177,3,'2018-03-10 11:34:00',84,'qualifier'),(178,3,'2018-03-10 11:41:00',85,'qualifier'),(179,3,'2018-03-10 11:48:00',86,'qualifier'),(180,3,'2018-03-10 11:55:00',87,'qualifier');
/*!40000 ALTER TABLE `matchup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_type_id` int(11) NOT NULL,
  `survey_section_id` int(11) NOT NULL,
  `sequence` int(11) NOT NULL,
  `question` varchar(400) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Question_QuestionType1_idx` (`question_type_id`),
  KEY `fk_question_survey_section1_idx` (`survey_section_id`),
  CONSTRAINT `fk_Question_QuestionType1` FOREIGN KEY (`question_type_id`) REFERENCES `question_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_survey_section1` FOREIGN KEY (`survey_section_id`) REFERENCES `survey_section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,1,1,1,'Auto (moved)'),(2,1,1,2,'Cross Line'),(3,2,1,3,'Power Cubes on Switch'),(4,2,1,4,'Power Cubes on Scale'),(5,2,1,5,'Power Cubes in Exchange'),(6,2,2,6,'Power Cube on Switch - Attempt'),(7,2,2,7,'Power Cube on Switch - Succeed'),(8,2,2,8,'Power Cube on Scale - Attempt'),(9,2,2,9,'Power Cube on Scale - Succeed'),(10,2,2,10,'Power Cube on Opponent Switch - Attempt'),(11,2,2,11,'Power Cube on Opponent Switch - Succeed'),(12,2,2,12,'Cubes in Exchange'),(13,2,2,13,'Cubes from Portal - Attempt'),(14,1,3,15,'Got on Pad'),(15,6,3,16,'Climb'),(19,3,4,20,'Force - cube count'),(20,3,4,22,'Levitate	- cube count'),(21,3,4,24,'Boost - cube count'),(22,2,5,32,'Alliance Score'),(23,4,5,33,'Game Outcome'),(26,2,5,34,'Fouls'),(27,2,5,35,'Tech Fouls'),(28,1,5,36,'Yellow Card'),(29,1,5,37,'Red Card'),(30,5,5,38,'Comments'),(31,2,2,14,'Cubes from Portal - Succeed'),(32,1,6,40,'No Show'),(33,1,6,41,'Broken'),(34,1,6,42,'Died'),(35,1,6,43,'Fell Over'),(36,1,4,21,'Force Activated'),(37,1,4,23,'Levitate Activated'),(38,1,4,25,'Boost Activated'),(39,5,7,1,'Drive Train'),(40,7,7,2,'Vision System'),(41,8,7,3,'What type of vision system'),(42,1,8,1,'Cross Baseline'),(43,1,8,2,'Put Cube on Switch'),(44,1,8,3,'Put Cube on Scale'),(45,1,8,4,'Put Cube in Exchange'),(46,1,11,1,'Put Cube on Switch'),(47,1,11,2,'Put Cube on Scale'),(48,1,11,3,'Put Cube in Exchange'),(49,1,11,4,'Take Cube from Portal'),(50,7,9,1,'Can it climb'),(51,5,9,2,'How does it climb, mechanism:'),(52,1,9,3,'Assist others in climbing'),(53,5,9,4,'How does it assist:'),(54,7,11,6,'Defensive'),(55,5,11,7,'Vault Goals'),(56,1,10,1,'Willing to change'),(57,5,10,2,'General Strategy'),(59,5,10,3,'Additional Notes'),(60,9,10,4,'Team Feel');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_type`
--

DROP TABLE IF EXISTS `question_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_type`
--

LOCK TABLES `question_type` WRITE;
/*!40000 ALTER TABLE `question_type` DISABLE KEYS */;
INSERT INTO `question_type` VALUES (1,'boolean'),(2,'numeric'),(3,'choice'),(4,'radio'),(5,'text'),(6,'choice'),(7,'choice'),(8,'choice'),(9,'radio');
/*!40000 ALTER TABLE `question_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `response`
--

DROP TABLE IF EXISTS `response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `response` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `team_matchup_id` int(11) NOT NULL,
  `response` varchar(400) NOT NULL,
  `student_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `response_uk1` (`question_id`,`team_matchup_id`),
  KEY `fk_Response_Question1_idx` (`question_id`),
  KEY `fk_Response_TeamMatch1_idx` (`team_matchup_id`),
  KEY `fk_response_student1_idx` (`student_id`),
  CONSTRAINT `fk_Response_Question1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Response_TeamMatch1` FOREIGN KEY (`team_matchup_id`) REFERENCES `team_matchup` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_response_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=198 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `response`
--

LOCK TABLES `response` WRITE;
/*!40000 ALTER TABLE `response` DISABLE KEYS */;
/*!40000 ALTER TABLE `response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `response_value`
--

DROP TABLE IF EXISTS `response_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `response_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_type_id` int(11) NOT NULL,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ResponseType_QuestionType1_idx` (`question_type_id`),
  CONSTRAINT `fk_ResponseType_QuestionType1` FOREIGN KEY (`question_type_id`) REFERENCES `question_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `response_value`
--

LOCK TABLES `response_value` WRITE;
/*!40000 ALTER TABLE `response_value` DISABLE KEYS */;
INSERT INTO `response_value` VALUES (1,1,'true'),(2,1,'false'),(3,3,'1'),(4,3,'2'),(5,3,'3'),(6,4,'Win'),(7,4,'Loss'),(8,4,'Tie'),(9,6,'Solo'),(10,6,'Assisted Others'),(11,6,'Received Assistance'),(12,6,'Used Levitate Power Up'),(13,3,'0'),(14,7,'Yes'),(15,7,'No'),(16,7,'Maybe'),(17,8,'None'),(18,8,'Camera'),(19,8,'Off-Board Vision Processing'),(20,9,'Good'),(21,9,'Bad'),(22,9,'Not Good');
/*!40000 ALTER TABLE `response_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_uk1` (`first_name`,`last_name`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (3,'Aaron','Keyes',0),(4,'Ahmed-Rashid','Shire',0),(5,'Alex','Goodland',0),(6,'Ana Isabel','SE',0),(7,'Andrew','Hollar',1),(8,'Brynn','DeVaan',1),(9,'Anna','Teurman',1),(10,'Cassidey','Shafer',1),(11,'Charlie','Boesen',1),(12,'Charlie','Ruff',1),(13,'Duncan','Murphy',0),(14,'Ed','Shipp',1),(15,'Eli','Knaus',1),(16,'Ernest','Pridgeon',1),(17,'Evan','Oreschnick',0),(18,'Grant','Ober',1),(19,'Jack','Lonn',0),(20,'Jameson','Cochrane',0),(21,'Jack','Boesen',0),(22,'Kyle','Kennedy',0),(23,'Lars','Haugen',0),(24,'Max','Labrie',1),(25,'Megan','Irwin',0),(26,'Nils','Nordstrom',1),(27,'Noah','Hoska',0),(28,'Olivia','Donney',0),(29,'Owen','Cody',1),(30,'Rodrigo','Sanchez',0),(31,'Rudy','Donish',0),(32,'Sam','Crowley',0),(33,'Sam','Rysdon',0),(34,'Sergei','Miller',1),(35,'Sofia','Bulgaria',1),(36,'Sully','Leier',0),(37,'Svetlana','Greipel',1),(38,'Thomas','Schroeder',1),(39,'Victor','LaBrie',1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey`
--

DROP TABLE IF EXISTS `survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `default` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey`
--

LOCK TABLES `survey` WRITE;
/*!40000 ALTER TABLE `survey` DISABLE KEYS */;
INSERT INTO `survey` VALUES (1,'Match Scouting',1),(2,'Pit Scouting',0);
/*!40000 ALTER TABLE `survey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey_section`
--

DROP TABLE IF EXISTS `survey_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey_section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `survey_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `sequence` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Survey_has_Question_Survey1_idx` (`survey_id`),
  CONSTRAINT `fk_Survey_has_Question_Survey1` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_section`
--

LOCK TABLES `survey_section` WRITE;
/*!40000 ALTER TABLE `survey_section` DISABLE KEYS */;
INSERT INTO `survey_section` VALUES (1,1,'Autonomus',1),(2,1,'Teleop',2),(3,1,'End Game Climb',3),(4,1,'Vault',4),(5,1,'Other',5),(6,1,'Bad Outcomes',6),(7,2,'Mechanical',1),(8,2,'Auto',2),(9,2,'Climb',4),(10,2,'Other',5),(11,2,'Teleop',3);
/*!40000 ALTER TABLE `survey_section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `school` varchar(100) DEFAULT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (93,'NEW Apple Corps',NULL,'Appleton','Wisconsin','USA'),(167,'Children of the Corn',NULL,'Iowa City','Iowa','USA'),(171,'Cheese Curd Herd',NULL,'Platteville','Wisconsin','USA'),(525,'Swartdogs',NULL,'Cedar Falls','Iowa','USA'),(706,'Cyberhawks','Arrow Head High School','Hartland','Wisconsin','USA'),(876,'Thunder Robotics',NULL,'Northwood','North Dakota','USA'),(931,'Perpetual Chaos',NULL,'Saint Louis','Missouri','USA'),(967,'Iron Lions',NULL,'Marion','Iowa','USA'),(1094,'Channel Cats',NULL,'O Fallon','Missouri','USA'),(1156,'Under Control',NULL,'Novo Hamburgo','Rio Grande do Sul','Brazil'),(1259,'Paradigm Shift',NULL,'Pewaukee','Wisconsin','USA'),(1625,'Winnovation',NULL,'Winnebago','Illinois','USA'),(1710,'The Ravonics Revolution',NULL,'Olathe','Kansas','USA'),(1714,'MORE Robotics',NULL,'Milwaukee','Wisconsin','USA'),(1816,'\"The Green Machine\"',NULL,'Edina','Minnesota','USA'),(1982,'Cougar Robotics',NULL,'Shawnee','Kansas','USA'),(1985,'Robohawks',NULL,'Florissant','Missouri','USA'),(2052,'KnightKrawler',NULL,'Saint Paul','Minnesota','USA'),(2129,'Ultraviolet',NULL,'Minneapolis','Minnesota','USA'),(2175,'The Fighting Calculators',NULL,'Saint Paul','Minnesota','USA'),(2177,'The Robettes',NULL,'Mendota Heights','Minnesota','USA'),(2181,'G.E.A.R.s (Great Engineers Awesome Robots)',NULL,'Blaine','Minnesota','USA'),(2202,'BEAST Robotics',NULL,'Brookfield','Wisconsin','USA'),(2207,'Bright Bears',NULL,'White Bear Lake','Minnesota','USA'),(2225,'R.U.S.T',NULL,'Brooklyn Park','Minnesota','USA'),(2227,'Tigers',NULL,'Fridley','Minnesota','USA'),(2264,'Wayzata Robotics',NULL,'Plymouth','Minnesota','USA'),(2410,'Blue Valley CAPS Metal Mustang Robotics',NULL,'Overland Park','Kansas','USA'),(2450,'Raiderbots',NULL,'St. Paul','Minnesota','USA'),(2451,'PWNAGE',NULL,'Saint Charles','Illinois','USA'),(2472,'Centurions',NULL,'Circle Pines','Minnesota','USA'),(2491,'NoMythic',NULL,'Saint Paul','Minnesota','USA'),(2499,'Industrial Revolution',NULL,'Hibbing','Minnesota','USA'),(2501,'Bionic Polars',NULL,'North St. Paul','Minnesota','USA'),(2502,'Talon Robotics',NULL,'Eden Prairie','Minnesota','USA'),(2503,'Warrior Robotics',NULL,'Brainerd','Minnesota','USA'),(2509,'Tigerbots',NULL,'Hutchinson','Minnesota','USA'),(2511,'Cougars',NULL,'Lakeville','Minnesota','USA'),(2512,'Duluth East Daredevils',NULL,'Duluth','Minnesota','USA'),(2525,'The pHalcons',NULL,'Minneapolis','Minnesota','USA'),(2526,'Crimson Robotics',NULL,'Osseo','Minnesota','USA'),(2530,'Inconceivable',NULL,'Rochester','Minnesota','USA'),(2531,'RoboHawks',NULL,'Chaska','Minnesota','USA'),(2538,'The Plaid Pillagers',NULL,'Morris','Minnesota','USA'),(2549,'Millerbots','Washburn High School','Minneapolis','Minnesota','USA'),(2574,'RoboHuskie',NULL,'St. Anthony','Minnesota','USA'),(2606,'Irish Robotics',NULL,'Rosemount','Minnesota','USA'),(2654,'Rams',NULL,'Roseau','Minnesota','USA'),(2667,'Knights of the Valley',NULL,'Apple Valley','Minnesota','USA'),(2846,'FireBears',NULL,'Saint Paul','Minnesota','USA'),(2855,'BEASTBOT',NULL,'Saint Paul','Minnesota','USA'),(2861,'Infinity\'s End',NULL,'Lake City','Minnesota','USA'),(2874,'Iron Eagles',NULL,'Grain Valley','Missouri','USA'),(2883,'F.R.E.D (Fighting Rednecks Engineering and Design)',NULL,'Warroad','Minnesota','USA'),(2957,'Knights',NULL,'Alden','Minnesota','USA'),(2977,'Sir Lancer Bots',NULL,'La Crescent','Minnesota','USA'),(2987,'Rogue Robotics',NULL,'Farmington','Minnesota','USA'),(2989,'Star Tech',NULL,'Richfield','Minnesota','USA'),(3026,'Orange Crush Robotics',NULL,'Delano','Minnesota','USA'),(3036,'DROBA Warriors',NULL,'Deer River','Minnesota','USA'),(3042,'Cobalt Catalysts',NULL,'Apple Valley','Minnesota','USA'),(3054,'IceStorm',NULL,'Grand Marais','Minnesota','USA'),(3055,'Furious George',NULL,'Austin','Minnesota','USA'),(3058,'AnnDroids',NULL,'Annandale','Minnesota','USA'),(3082,'Chicken Bot Pie',NULL,'Minnetonka','Minnesota','USA'),(3100,'Lightning Turtles',NULL,'Mendota Heights','Minnesota','USA'),(3122,'The Alluminators',NULL,'New Ulm','Minnesota','USA'),(3130,'The ERRORs',NULL,'Woodbury','Minnesota','USA'),(3202,'KnightBots',NULL,'St. Paul','Minnesota','USA'),(3206,'Royal T-Wrecks',NULL,'Woodbury','Minnesota','USA'),(3212,'YME Stingbots',NULL,'Granite Falls','Minnesota','USA'),(3267,'Mariner Robotics',NULL,'Silver Bay','Minnesota','USA'),(3276,'TOOLCATS',NULL,'New London','Minnesota','USA'),(3277,'ProDigi',NULL,'Thief River Falls','Minnesota','USA'),(3291,'Au Pirates',NULL,'Brooklyn Park','Minnesota','USA'),(3293,'OtterBots',NULL,'Fergus Falls','Minnesota','USA'),(3294,'Backwoods Engineers',NULL,'Pine River','Minnesota','USA'),(3297,'Full Metal Jackets',NULL,'Perham','Minnesota','USA'),(3313,'Mechatronics',NULL,'Alexandria','Minnesota','USA'),(3367,'Circle Of Life Academy - COLA',NULL,'White Earth','Minnesota','USA'),(3610,'Islanders',NULL,'Minneapolis','Minnesota','USA'),(3630,'The Stampede',NULL,'Minneapolis','Minnesota','USA'),(3633,'Catalyst 3633',NULL,'Albert Lea','Minnesota','USA'),(3691,'RoboRaiders',NULL,'Northfield','Minnesota','USA'),(3723,'TEKnights',NULL,'Spring Valley','Minnesota','USA'),(3740,'Storm Robotics',NULL,'Sauk Rapids','Minnesota','USA'),(3745,'Governors',NULL,'Saint Paul','Minnesota','USA'),(3750,'Gator Robotics',NULL,'Badger','Minnesota','USA'),(3755,'Dragon Robotics',NULL,'Litchfield','Minnesota','USA'),(3840,'Teens \'Nto Technology',NULL,'Isanti','Minnesota','USA'),(3848,'Bots in Shining Armour',NULL,'Kenyon','Minnesota','USA'),(3871,'Trojan Robotics',NULL,'Worthington','Minnesota','USA'),(3883,'Data Bits',NULL,'Cottage Grove','Minnesota','USA'),(3926,'MPArors',NULL,'Saint Paul','Minnesota','USA'),(3928,'Team Neutrino',NULL,'Ames','Iowa','USA'),(4009,'Denfeld DNA Robotics',NULL,'Duluth','Minnesota','USA'),(4011,'πρbοtic (pi robotics)',NULL,'La Crosse','Wisconsin','USA'),(4021,'igKnightion',NULL,'Onalaska','Wisconsin','USA'),(4054,'Robo Raiders',NULL,'La Crosse','Wisconsin','USA'),(4166,'Robostang',NULL,'Mora','Minnesota','USA'),(4174,'Mustangs',NULL,'Hector','Minnesota','USA'),(4182,'Viking Robotics',NULL,'Minneota','Minnesota','USA'),(4198,'RoboCats',NULL,'Waconia','Minnesota','USA'),(4215,'Tritons',NULL,'Saint Paul','Minnesota','USA'),(4217,'Scitobors',NULL,'Nashwauk','Minnesota','USA'),(4225,'RoboCats',NULL,'Saint Paul','Minnesota','USA'),(4226,'Huskies',NULL,'Albany','Minnesota','USA'),(4230,'TopperBots',NULL,'Duluth','Minnesota','USA'),(4238,'BBE Resistance Robotics',NULL,'Belgrade','Minnesota','USA'),(4239,'WARPSPEED',NULL,'Willmar','Minnesota','USA'),(4260,'BEAR Bucs',NULL,'Blue Earth Area','Minnesota','USA'),(4277,'Thingamajiggers',NULL,'Minneapolis','Minnesota','USA'),(4329,'Lutheran Roboteers',NULL,'Saint Peters','Missouri','USA'),(4397,'TEAM Clutch Robotics',NULL,'Bagley','Minnesota','USA'),(4480,'UC-Botics',NULL,'Upsala','Minnesota','USA'),(4500,'RoboHounds',NULL,'Clayton','Missouri','USA'),(4506,'PioNerds',NULL,'Saint Paul','Minnesota','USA'),(4511,'Power Amplified',NULL,'Plymouth','Minnesota','USA'),(4531,'STEMpunk',NULL,'Two Rivers','Wisconsin','USA'),(4536,'MinuteBots',NULL,'Saint Paul','Minnesota','USA'),(4539,'KAOTIC Robotics',NULL,'Frazee','Minnesota','USA'),(4549,'Iron Bulls',NULL,'South Saint Paul','Minnesota','USA'),(4607,'C.I.S.',NULL,'Becker','Minnesota','USA'),(4623,'Flyer Robotics',NULL,'Little Falls','Minnesota','USA'),(4624,'Rebel Alliance',NULL,'Owatonna','Minnesota','USA'),(4632,'Monti-Pythons',NULL,'Monticello','Minnesota','USA'),(4646,'Team ASAP',NULL,'Des Moines','Iowa','USA'),(4648,'Techno-Tech',NULL,'Jordan','Minnesota','USA'),(4656,'Rock Solid Robotics',NULL,'Two Harbors','Minnesota','USA'),(4663,'Cyber Tigers',NULL,'Belle Plaine','Minnesota','USA'),(4665,'Predators',NULL,'Glencoe','Minnesota','USA'),(4693,'Team Rock-It',NULL,'Rockford','Minnesota','USA'),(4728,'Rocori Rench Reckers',NULL,'Cold Spring','Minnesota','USA'),(4741,'WingNuts',NULL,'Redwood Falls','Minnesota','USA'),(4778,'Stormbots',NULL,'Chanhassen','Minnesota','USA'),(4818,'The Herd',NULL,'West Fargo','North Dakota','USA'),(4845,'Lion\'s Pride',NULL,'Duluth','Minnesota','USA'),(4859,'The CyBears',NULL,'Byron','Minnesota','USA'),(4959,'Millennium Falcons',NULL,'Kansas City','Missouri','USA'),(5006,'Apophis',NULL,'Fayetteville','Arkansas','USA'),(5041,'CyBears',NULL,'West Branch','Iowa','USA'),(5143,'GRG Raw Steel',NULL,'Grand Rapids','Minnesota','USA'),(5232,'Talons',NULL,'Hermantown','Minnesota','USA'),(5253,'Bigfork Backwoods Bots',NULL,'Bigfork','Minnesota','USA'),(5275,'T.I.M.E.-Bots',NULL,'New Prague','Minnesota','USA'),(5278,'Los Clasicos',NULL,'Minneapolis','Minnesota','USA'),(5290,'Mechanical Howl',NULL,'Forest Lake','Minnesota','USA'),(5299,'Winger Tech',NULL,'Red Wing','Minnesota','USA'),(5340,'FAIR Robtics',NULL,'Minneapolis','Minnesota','USA'),(5348,'Charger Robotics',NULL,'Cokato','Minnesota','USA'),(5434,'Falcon Robotics',NULL,'Faribault','Minnesota','USA'),(5464,'Bluejacket Robotics',NULL,'Cambridge','Minnesota','USA'),(5541,'Shakopee Robotics',NULL,'Shakopee','Minnesota','USA'),(5542,'RoboHerd',NULL,'Buffalo','Minnesota','USA'),(5576,'Team Terminator',NULL,'Spirit Lake','Iowa','USA'),(5595,'NRHS TigerBytes',NULL,'New Richmond','Wisconsin','USA'),(5626,'Nu-matic Ninjas',NULL,'Norwood Young America','Minnesota','USA'),(5653,'Iron Mosquitos',NULL,'Babbitt','Minnesota','USA'),(5690,'SubZero Robotics',NULL,'Esko','Minnesota','USA'),(5720,'Jagobotics',NULL,'Hinckley','Minnesota','USA'),(5826,'Avis Automata',NULL,'Chippewa Falls','Wisconsin','USA'),(5837,'Unity4Tech',NULL,'Waterloo','Iowa','USA'),(5903,'ThorBots',NULL,'Westby','Wisconsin','USA'),(5906,'Titanium Badgers',NULL,'Bennington','Nebraska','USA'),(5913,'Patriotics',NULL,'Pequot Lakes','Minnesota','USA'),(5914,'Caledonia Robotic Warriors',NULL,'Caledonia','Minnesota','USA'),(5935,'Tech Tigers',NULL,'Grinnell','Iowa','USA'),(5991,'Chargers',NULL,'Westbrook','Minnesota','USA'),(5998,'The Chillbots',NULL,'International Falls','Minnesota','USA'),(5999,'Byte Force',NULL,'Milaca','Minnesota','USA'),(6022,'Wrench Warmers',NULL,'Blooming Prairie','Minnesota','USA'),(6025,'Adroit Androids',NULL,'Istanbul','Istanbul','Turkey'),(6044,'Circuit Breakers',NULL,'Moose Lake','Minnesota','USA'),(6045,'BREADBOTS',NULL,'Sartell','Minnesota','USA'),(6047,'Proctor Frostbyte',NULL,'Proctor','Minnesota','USA'),(6107,'Cyber Jagzz',NULL,'Huntsville','Alabama','USA'),(6132,'Iron Rangers',NULL,'Crosby','Minnesota','USA'),(6146,'Blackjacks',NULL,'Dawson','Minnesota','USA'),(6147,'Tonkabots',NULL,'Mound','Minnesota','USA'),(6160,'Bombatrons',NULL,'Barnum','Minnesota','USA'),(6164,'Moonshot Slaybots',NULL,'Dike','Iowa','USA'),(6217,'Bomb-Botz',NULL,'Cannon Falls','Minnesota','USA'),(6317,'Disruptive Innovation',NULL,'Davenport','Iowa','USA'),(6318,'FE Freedom Engineers',NULL,'Freedom','Wisconsin','USA'),(6320,'Iron Hawks',NULL,'Manchester','Iowa','USA'),(6359,'TechKnow Difficulties',NULL,'Aurora','Nebraska','USA'),(6371,'Spencer Robotics',NULL,'Spencer','Iowa','USA'),(6379,'Terabyte of Ram',NULL,'Pleasant Hill','Iowa','USA'),(6391,'Ursuline Bearbotics',NULL,'Saint Louis','Missouri','USA'),(6412,'For Sale By Owner',NULL,'Cedar Rapids','Iowa','USA'),(6419,'Innovation, Collaboration, and Excellence',NULL,'West Des Moines','Iowa','USA'),(6420,'Fire Island Robotics',NULL,'Muscatine','Iowa','USA'),(6424,'Stealth Panther Robotics',NULL,'Knob Noster','Missouri','USA'),(6455,'The Coded Collective',NULL,'Waterloo','Iowa','USA'),(6467,'Tiger Robotics',NULL,'Lenox','Iowa','USA'),(6536,'TheDarkSide',NULL,'Vinton','Iowa','USA'),(6613,'Gogebic Range Robotics',NULL,'Hurley','Wisconsin','USA'),(6628,'KMS ROBOTICS',NULL,'Kerkhoven','Minnesota','USA'),(6630,'F.U.N. (Fiercely Uknighted Nation)',NULL,'La Porte City','Iowa','USA'),(6707,'RCW Jaguar Robosapiens',NULL,'Renville','Minnesota','USA'),(6720,'Walker-Hackensack-Akeley',NULL,'Walker','Minnesota','USA'),(6749,'tERAbytes',NULL,'Hopkins','Minnesota','USA'),(6758,'Otternauts',NULL,'Kasson','Minnesota','USA'),(6819,'Viking Robotics',NULL,'Växjö','Kronobergs län','Sweden'),(6889,'DC Current',NULL,'Bloomfield','Iowa','USA'),(6912,'Gentry Robotics',NULL,'Saint Paul','Minnesota','USA'),(6950,'West Central Valley TAG',NULL,'Stuart','Iowa','USA'),(6999,'B.E.S.T.',NULL,'Sinop','Sinop','Turkey'),(7019,'Tech NoLogic: Powered by NASA',NULL,'Minneapolis','Minnesota','USA'),(7021,'TC Robotics',NULL,'Arcadia','Wisconsin','USA'),(7028,'Binary Battalion',NULL,'Saint Michael','Minnesota','USA'),(7041,'Doomsday Dogs',NULL,'Carlton','Minnesota','USA'),(7122,'WYSIWYG',NULL,'Des Moines','Iowa','USA'),(7141,'Cyberhounds',NULL,'Fort Madison','Iowa','USA'),(7142,'Saydel High School Robotics Team---Vulcan Eagles',NULL,'Des Moines','Iowa','USA'),(7258,'Hiawatha Collegiate',NULL,'Minneapolis','Minnesota','USA'),(7273,'ZooM Robotics',NULL,'Zumbrota','Minnesota','USA'),(7309,'Green Lightning',NULL,'Storm Lake','Iowa','USA'),(7311,'SquintSquad',NULL,'Virginia','Minnesota','USA');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_matchup`
--

DROP TABLE IF EXISTS `team_matchup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_matchup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `matchup_id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `alliance` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `team_match_ui1` (`matchup_id`,`team_id`),
  KEY `fk_Match_has_Team_Team1_idx` (`team_id`),
  KEY `fk_Match_has_Team_Match1_idx` (`matchup_id`),
  CONSTRAINT `fk_Match_has_Team_Match1` FOREIGN KEY (`matchup_id`) REFERENCES `matchup` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Match_has_Team_Team1` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1119 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_matchup`
--

LOCK TABLES `team_matchup` WRITE;
/*!40000 ALTER TABLE `team_matchup` DISABLE KEYS */;
INSERT INTO `team_matchup` VALUES (624,151,3267,'red'),(625,151,3212,'red'),(626,151,4665,'red'),(627,151,3054,'blue'),(628,151,3297,'blue'),(629,151,6758,'blue'),(630,152,1259,'red'),(631,152,4511,'red'),(632,152,706,'red'),(633,152,3926,'blue'),(634,152,4531,'blue'),(635,152,3691,'blue'),(636,153,2861,'red'),(637,153,4226,'red'),(638,153,4632,'red'),(639,153,2450,'blue'),(640,153,2512,'blue'),(641,153,2177,'blue'),(642,154,6132,'red'),(643,154,4859,'red'),(644,154,2227,'red'),(645,154,876,'blue'),(646,154,4506,'blue'),(647,154,4182,'blue'),(648,155,6613,'red'),(649,155,3630,'red'),(650,155,6819,'red'),(651,155,4174,'blue'),(652,155,6044,'blue'),(653,155,3745,'blue'),(654,156,3267,'red'),(655,156,2502,'red'),(656,156,2501,'red'),(657,156,5999,'blue'),(658,156,5434,'blue'),(659,156,3036,'blue'),(660,157,7021,'red'),(661,157,6045,'red'),(662,157,3054,'red'),(663,157,4239,'blue'),(664,157,5653,'blue'),(665,157,4624,'blue'),(666,158,3723,'red'),(667,158,5720,'red'),(668,158,4549,'red'),(669,158,5232,'blue'),(670,158,3297,'blue'),(671,158,2987,'blue'),(672,159,5541,'red'),(673,159,4656,'red'),(674,159,4623,'red'),(675,159,3212,'blue'),(676,159,2989,'blue'),(677,159,5826,'blue'),(678,160,2181,'red'),(679,160,5903,'red'),(680,160,5998,'red'),(681,160,6707,'blue'),(682,160,6147,'blue'),(683,160,4665,'blue'),(684,161,7311,'red'),(685,161,2531,'red'),(686,161,5999,'red'),(687,161,2450,'blue'),(688,161,6758,'blue'),(689,161,2509,'blue'),(690,162,6819,'red'),(691,162,4226,'red'),(692,162,3036,'red'),(693,162,706,'blue'),(694,162,4632,'blue'),(695,162,3054,'blue'),(696,163,4511,'red'),(697,163,6613,'red'),(698,163,3723,'red'),(699,163,1259,'blue'),(700,163,876,'blue'),(701,163,7021,'blue'),(702,164,5653,'red'),(703,164,4174,'red'),(704,164,5232,'red'),(705,164,4531,'blue'),(706,164,2989,'blue'),(707,164,5541,'blue'),(708,165,6132,'red'),(709,165,2512,'red'),(710,165,3926,'red'),(711,165,4549,'blue'),(712,165,4665,'blue'),(713,165,6045,'blue'),(714,166,6707,'red'),(715,166,4182,'red'),(716,166,3212,'red'),(717,166,2502,'blue'),(718,166,4239,'blue'),(719,166,2177,'blue'),(720,167,3297,'red'),(721,167,5826,'red'),(722,167,6147,'red'),(723,167,2501,'blue'),(724,167,2531,'blue'),(725,167,3745,'blue'),(726,168,5720,'red'),(727,168,7311,'red'),(728,168,2227,'red'),(729,168,5998,'blue'),(730,168,2861,'blue'),(731,168,3630,'blue'),(732,169,3691,'red'),(733,169,2987,'red'),(734,169,4506,'red'),(735,169,5903,'blue'),(736,169,3267,'blue'),(737,169,4624,'blue'),(738,170,6758,'red'),(739,170,5434,'red'),(740,170,6044,'red'),(741,170,4623,'blue'),(742,170,2181,'blue'),(743,170,4859,'blue'),(744,171,2509,'red'),(745,171,5653,'red'),(746,171,4182,'red'),(747,171,2512,'blue'),(748,171,4656,'blue'),(749,171,1259,'blue'),(750,172,4174,'red'),(751,172,6147,'red'),(752,172,2450,'red'),(753,172,4511,'blue'),(754,172,6132,'blue'),(755,172,3297,'blue'),(756,173,876,'red'),(757,173,5541,'red'),(758,173,3054,'red'),(759,173,2531,'blue'),(760,173,2502,'blue'),(761,173,5720,'blue'),(762,174,706,'red'),(763,174,5998,'red'),(764,174,2987,'red'),(765,174,6045,'blue'),(766,174,4632,'blue'),(767,174,5826,'blue'),(768,175,6044,'red'),(769,175,5903,'red'),(770,175,4239,'red'),(771,175,2227,'blue'),(772,175,5999,'blue'),(773,175,4549,'blue'),(774,176,6707,'red'),(775,176,2181,'red'),(776,176,3267,'red'),(777,176,2861,'blue'),(778,176,2989,'blue'),(779,176,3926,'blue'),(780,177,6758,'red'),(781,177,4624,'red'),(782,177,4623,'red'),(783,177,4656,'blue'),(784,177,6613,'blue'),(785,177,4226,'blue'),(786,178,3691,'red'),(787,178,2501,'red'),(788,178,3212,'red'),(789,178,3723,'blue'),(790,178,6819,'blue'),(791,178,7311,'blue'),(792,179,4859,'red'),(793,179,4665,'red'),(794,179,4506,'red'),(795,179,4531,'blue'),(796,179,2177,'blue'),(797,179,3630,'blue'),(798,180,5232,'red'),(799,180,3036,'red'),(800,180,3745,'red'),(801,180,2509,'blue'),(802,180,7021,'blue'),(803,180,5434,'blue'),(805,3,3054,'none'),(806,3,3212,'none'),(807,3,3267,'none'),(808,3,3297,'none'),(809,3,4665,'none'),(810,3,6758,'none'),(811,3,706,'none'),(812,3,1259,'none'),(813,3,3691,'none'),(814,3,3926,'none'),(815,3,4511,'none'),(816,3,4531,'none'),(817,3,2177,'none'),(818,3,2450,'none'),(819,3,2512,'none'),(820,3,2861,'none'),(821,3,4226,'none'),(822,3,4632,'none'),(823,3,876,'none'),(824,3,2227,'none'),(825,3,4182,'none'),(826,3,4506,'none'),(827,3,4859,'none'),(828,3,6132,'none'),(829,3,3630,'none'),(830,3,3745,'none'),(831,3,4174,'none'),(832,3,6044,'none'),(833,3,6613,'none'),(834,3,6819,'none'),(835,3,2501,'none'),(836,3,2502,'none'),(837,3,3036,'none'),(838,3,5434,'none'),(839,3,5999,'none'),(840,3,4239,'none'),(841,3,4624,'none'),(842,3,5653,'none'),(843,3,6045,'none'),(844,3,7021,'none'),(845,3,2987,'none'),(846,3,3723,'none'),(847,3,4549,'none'),(848,3,5232,'none'),(849,3,5720,'none'),(850,3,2989,'none'),(851,3,4623,'none'),(852,3,4656,'none'),(853,3,5541,'none'),(854,3,5826,'none'),(855,3,2181,'none'),(856,3,5903,'none'),(857,3,5998,'none'),(858,3,6147,'none'),(859,3,6707,'none'),(860,3,2509,'none'),(861,3,2531,'none'),(862,3,7311,'none'),(863,85,5253,'red'),(864,85,3082,'red'),(865,85,3058,'blue'),(866,85,6160,'blue'),(867,85,4397,'blue'),(868,85,4480,'red'),(869,86,5299,'red'),(870,86,2264,'red'),(871,86,4728,'blue'),(872,86,3755,'blue'),(873,86,4166,'blue'),(874,86,4238,'red'),(875,87,5143,'blue'),(876,87,4539,'red'),(877,87,5690,'red'),(878,87,3293,'red'),(879,87,3055,'blue'),(880,87,3740,'blue'),(881,88,4741,'blue'),(882,88,3100,'red'),(883,88,5595,'blue'),(884,88,6022,'red'),(885,88,4607,'red'),(886,88,6318,'blue'),(887,89,2977,'red'),(888,89,1816,'blue'),(889,89,2503,'red'),(890,89,3122,'blue'),(891,89,6146,'blue'),(892,89,4011,'red'),(893,90,4845,'red'),(894,90,5253,'blue'),(895,90,4536,'blue'),(896,90,4215,'blue'),(897,90,2052,'red'),(898,90,6047,'red'),(899,91,3276,'blue'),(900,91,4238,'red'),(901,91,2264,'blue'),(902,91,2538,'red'),(903,91,2526,'red'),(904,91,3042,'blue'),(905,92,5299,'blue'),(906,92,93,'red'),(907,92,3840,'red'),(908,92,3058,'red'),(909,92,2202,'blue'),(910,92,3291,'blue'),(911,93,4728,'red'),(912,93,6160,'red'),(913,93,5348,'blue'),(914,93,3294,'blue'),(915,93,5464,'blue'),(916,93,4009,'red'),(917,94,5542,'blue'),(918,94,2499,'blue'),(919,94,4166,'red'),(920,94,6217,'blue'),(921,94,5913,'red'),(922,94,6720,'red'),(923,95,2846,'red'),(924,95,3755,'red'),(925,95,4217,'red'),(926,95,3082,'blue'),(927,95,2511,'blue'),(928,95,7028,'blue'),(929,96,4480,'red'),(930,96,4230,'blue'),(931,96,2175,'blue'),(932,96,7041,'blue'),(933,96,4198,'red'),(934,96,4778,'red'),(935,97,2977,'blue'),(936,97,6628,'blue'),(937,97,4397,'red'),(938,97,5690,'red'),(939,97,6047,'blue'),(940,97,2264,'red'),(941,98,4011,'blue'),(942,98,2202,'red'),(943,98,2052,'blue'),(944,98,4009,'red'),(945,98,3100,'red'),(946,98,2538,'blue'),(947,99,2526,'red'),(948,99,6217,'red'),(949,99,6160,'red'),(950,99,3293,'blue'),(951,99,6318,'blue'),(952,99,4607,'blue'),(953,100,5464,'blue'),(954,100,2846,'blue'),(955,100,3291,'red'),(956,100,6146,'red'),(957,100,4539,'blue'),(958,100,4166,'red'),(959,101,1816,'red'),(960,101,5143,'red'),(961,101,4215,'red'),(962,101,2511,'blue'),(963,101,3740,'blue'),(964,101,7041,'blue'),(965,102,4728,'blue'),(966,102,3276,'red'),(967,102,2175,'red'),(968,102,5595,'red'),(969,102,5253,'blue'),(970,102,5913,'blue'),(971,103,4741,'blue'),(972,103,4217,'blue'),(973,103,3840,'red'),(974,103,2503,'blue'),(975,103,4480,'red'),(976,103,4536,'red'),(977,104,7028,'red'),(978,104,5542,'blue'),(979,104,3042,'blue'),(980,104,4397,'red'),(981,104,4230,'red'),(982,104,3294,'blue'),(983,105,6022,'red'),(984,105,4198,'blue'),(985,105,5348,'blue'),(986,105,93,'red'),(987,105,3755,'blue'),(988,105,4845,'red'),(989,106,3082,'red'),(990,106,3058,'red'),(991,106,2499,'blue'),(992,106,3055,'blue'),(993,106,4778,'blue'),(994,106,4238,'red'),(995,107,5464,'red'),(996,107,5299,'red'),(997,107,6720,'blue'),(998,107,7041,'blue'),(999,107,6628,'red'),(1000,107,3122,'blue'),(1001,108,4536,'blue'),(1002,108,3740,'red'),(1003,108,2264,'red'),(1004,108,3100,'blue'),(1005,108,2052,'blue'),(1006,108,2175,'red'),(1007,109,4480,'blue'),(1008,109,4009,'blue'),(1009,109,5913,'red'),(1010,109,3042,'red'),(1011,109,6146,'red'),(1012,109,5690,'blue'),(1013,110,3755,'red'),(1014,110,3293,'red'),(1015,110,2503,'blue'),(1016,110,4845,'red'),(1017,110,4230,'blue'),(1018,110,4607,'blue'),(1019,111,1816,'blue'),(1020,111,3055,'red'),(1021,111,3840,'blue'),(1022,111,6022,'red'),(1023,111,2846,'blue'),(1024,111,3276,'red'),(1025,112,4011,'blue'),(1026,112,3058,'red'),(1027,112,4728,'blue'),(1028,112,7028,'red'),(1029,112,6318,'red'),(1030,112,4539,'blue'),(1031,113,5143,'blue'),(1032,113,3082,'blue'),(1033,113,2202,'red'),(1034,113,4166,'blue'),(1035,113,3294,'red'),(1036,113,2977,'red'),(1037,114,3122,'blue'),(1038,114,5542,'red'),(1039,114,4778,'blue'),(1040,114,4198,'red'),(1041,114,4215,'red'),(1042,114,4741,'blue'),(1043,115,4238,'blue'),(1044,115,6628,'red'),(1045,115,5348,'red'),(1046,115,6217,'blue'),(1047,115,3291,'red'),(1048,115,2511,'blue'),(1049,116,2538,'blue'),(1050,116,4217,'red'),(1051,116,93,'blue'),(1052,116,6160,'red'),(1053,116,6720,'blue'),(1054,116,5253,'red'),(1055,117,5595,'red'),(1056,117,2499,'blue'),(1057,117,2526,'blue'),(1058,117,4397,'red'),(1059,117,6047,'red'),(1060,117,5299,'blue'),(1061,2,706,'none'),(1062,2,876,'none'),(1063,2,1259,'none'),(1064,2,2177,'none'),(1065,2,2181,'none'),(1066,2,2227,'none'),(1067,2,2450,'none'),(1068,2,2501,'none'),(1069,2,2502,'none'),(1070,2,2509,'none'),(1071,2,2512,'none'),(1072,2,2531,'none'),(1073,2,2861,'none'),(1074,2,2987,'none'),(1075,2,2989,'none'),(1076,2,3036,'none'),(1077,2,3054,'none'),(1078,2,3212,'none'),(1079,2,3267,'none'),(1080,2,3297,'none'),(1081,2,3630,'none'),(1082,2,3691,'none'),(1083,2,3723,'none'),(1084,2,3745,'none'),(1085,2,3926,'none'),(1086,2,4174,'none'),(1087,2,4182,'none'),(1088,2,4226,'none'),(1089,2,4239,'none'),(1090,2,4506,'none'),(1091,2,4511,'none'),(1092,2,4531,'none'),(1093,2,4549,'none'),(1094,2,4623,'none'),(1095,2,4624,'none'),(1096,2,4632,'none'),(1097,2,4656,'none'),(1098,2,4665,'none'),(1099,2,4859,'none'),(1100,2,5232,'none'),(1101,2,5434,'none'),(1102,2,5541,'none'),(1103,2,5653,'none'),(1104,2,5720,'none'),(1105,2,5826,'none'),(1106,2,5903,'none'),(1107,2,5998,'none'),(1108,2,5999,'none'),(1109,2,6044,'none'),(1110,2,6045,'none'),(1111,2,6132,'none'),(1112,2,6147,'none'),(1113,2,6613,'none'),(1114,2,6707,'none'),(1115,2,6758,'none'),(1116,2,6819,'none'),(1117,2,7021,'none'),(1118,2,7311,'none');
/*!40000 ALTER TABLE `team_matchup` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-09 21:52:20
