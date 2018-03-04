CREATE DATABASE  IF NOT EXISTS `scouting` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `scouting`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: scouting
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
INSERT INTO `event` VALUES (1,'test','test','MN','2018-01-31',0,1),(2,'Lake Superior Regional ','Duluth','MN','2018-03-10',1,1),(3,'Northern Lights Regional','Duluth','MN','2018-03-10',0,1),(4,'Iowa Regional','Cedar Falls','IA','2018-03-21',0,1),(5,'Minnesota North Star Regional','Minneapolis','MN','2018-03-28',0,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matchup`
--

LOCK TABLES `matchup` WRITE;
/*!40000 ALTER TABLE `matchup` DISABLE KEYS */;
INSERT INTO `matchup` VALUES (1,1,'2018-02-23 14:57:00',-1,'pit'),(84,1,'2018-02-23 14:57:00',13,'qualifier');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
INSERT INTO `student` VALUES (3,'Aaron','Keyes',1),(4,'Ahmed-Rashid','Shire',1),(5,'Alex','Goodland',1),(6,'Ana Isabel','SE',1),(7,'Andrew','Hollar',1),(8,'Brynn','DeVaan',1),(9,'Anna','Teurman',1),(10,'Cassidey','Shafer',1),(11,'Charlie','Boesen',1),(12,'Charlie','Ruff',1),(13,'Duncan','Murphy',1),(14,'Ed','Shipp',1),(15,'Eli','Knaus',1),(16,'Ernest','Pridgeon',1),(17,'Evan','Oreschnick',1),(18,'Grant','Ober',1),(19,'Jack','Lonn',1),(20,'Jameson','Cochrane',1),(21,'Jack','Boesen',1),(22,'Kyle','Kennedy',1),(23,'Lars','Haugen',1),(24,'Max','Labrie',1),(25,'Megan','Irwin',1),(26,'Nils','Nordstrom',1),(27,'Noah','Hoska',1),(28,'Olivia','Donney',1),(29,'Owen','Cody',1),(30,'Rodrigo','Sanchez',1),(31,'Rudy','Donish',1),(32,'Sam','Crowley',1),(33,'Sam','Rysdon',1),(34,'Sergei','Miller',1),(35,'Sofia','Bulgaria',1),(36,'Sully','Leier',1),(37,'Svetlana','Greipel',1),(38,'Thomas','Schroeder',1),(39,'Victor','LaBrie',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_matchup`
--

LOCK TABLES `team_matchup` WRITE;
/*!40000 ALTER TABLE `team_matchup` DISABLE KEYS */;
INSERT INTO `team_matchup` VALUES (1,84,706,'red'),(2,84,876,'red'),(3,84,1259,'red'),(4,84,2177,'blue'),(5,84,3691,'blue'),(6,84,5541,'blue'),(47,1,93,'none'),(48,1,167,'none'),(49,1,171,'none'),(50,1,525,'none'),(51,1,706,'none'),(52,1,876,'none'),(53,1,931,'none'),(54,1,967,'none'),(55,1,1094,'none'),(56,1,1156,'none'),(57,1,1259,'none'),(58,1,1625,'none'),(59,1,1710,'none'),(60,1,1714,'none'),(61,1,1816,'none'),(62,1,1982,'none'),(63,1,1985,'none'),(64,1,2052,'none'),(65,1,2129,'none'),(66,1,2175,'none'),(67,1,2177,'none'),(68,1,2181,'none'),(69,1,2202,'none'),(70,1,2207,'none'),(71,1,2225,'none'),(72,1,2227,'none'),(73,1,2264,'none'),(74,1,2410,'none'),(75,1,2450,'none'),(76,1,2451,'none'),(77,1,2472,'none'),(78,1,2491,'none'),(79,1,2499,'none'),(80,1,2501,'none'),(81,1,2502,'none'),(82,1,2503,'none'),(83,1,2509,'none'),(84,1,2511,'none'),(85,1,2512,'none'),(86,1,2525,'none'),(87,1,2526,'none'),(88,1,2530,'none'),(89,1,2531,'none'),(90,1,2538,'none'),(91,1,2549,'none'),(92,1,2574,'none'),(93,1,2606,'none'),(94,1,2654,'none'),(95,1,2667,'none'),(96,1,2846,'none'),(97,1,2855,'none'),(98,1,2861,'none'),(99,1,2874,'none'),(100,1,2883,'none'),(101,1,2957,'none'),(102,1,2977,'none'),(103,1,2987,'none'),(104,1,2989,'none'),(105,1,3026,'none'),(106,1,3036,'none'),(107,1,3042,'none'),(108,1,3054,'none'),(109,1,3055,'none'),(110,1,3058,'none'),(111,1,3082,'none'),(112,1,3100,'none'),(113,1,3122,'none'),(114,1,3130,'none');
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

-- Dump completed on 2018-03-03 23:48:43
