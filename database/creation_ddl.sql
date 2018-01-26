-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema scouting
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema scouting
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `scouting` DEFAULT CHARACTER SET utf8 ;
USE `scouting` ;

-- -----------------------------------------------------
-- Table `scouting`.`team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`team` (
  `id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `school` VARCHAR(100) NOT NULL,
  `city` VARCHAR(100) NOT NULL,
  `state` CHAR(2) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scouting`.`survey`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`survey` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scouting`.`event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`event` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `survey_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `city` VARCHAR(100) NOT NULL,
  `state` CHAR(2) NOT NULL,
  `start_date` DATE NOT NULL,
  `current` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_Event_Survey1_idx` (`survey_id` ASC),
  CONSTRAINT `fk_Event_Survey1`
  FOREIGN KEY (`survey_id`)
  REFERENCES `scouting`.`survey` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scouting`.`matchup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`matchup` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `event_id` INT NOT NULL,
  `start_time` DATETIME NOT NULL,
  `match_number` INT NOT NULL,
  `type` VARCHAR(45) NOT NULL COMMENT 'The match type describes if this is a regular, semi-final, or final match round.',
  PRIMARY KEY (`id`),
  INDEX `fk_Match_Event1_idx` (`event_id` ASC),
  CONSTRAINT `fk_Match_Event1`
  FOREIGN KEY (`event_id`)
  REFERENCES `scouting`.`event` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scouting`.`question_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`question_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scouting`.`survey_section`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`survey_section` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `survey_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `sequence` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Survey_has_Question_Survey1_idx` (`survey_id` ASC),
  CONSTRAINT `fk_Survey_has_Question_Survey1`
  FOREIGN KEY (`survey_id`)
  REFERENCES `scouting`.`survey` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scouting`.`question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question_type_id` INT NOT NULL,
  `survey_section_id` INT NOT NULL,
  `sequence` INT NOT NULL,
  `question` VARCHAR(400) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Question_QuestionType1_idx` (`question_type_id` ASC),
  INDEX `fk_question_survey_section1_idx` (`survey_section_id` ASC),
  CONSTRAINT `fk_Question_QuestionType1`
  FOREIGN KEY (`question_type_id`)
  REFERENCES `scouting`.`question_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_survey_section1`
  FOREIGN KEY (`survey_section_id`)
  REFERENCES `scouting`.`survey_section` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scouting`.`team_matchup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`team_matchup` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `matchup_id` INT NOT NULL,
  `team_id` INT NOT NULL,
  `alliance` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Match_has_Team_Team1_idx` (`team_id` ASC),
  INDEX `fk_Match_has_Team_Match1_idx` (`matchup_id` ASC),
  UNIQUE INDEX `team_match_ui1` (`matchup_id` ASC, `team_id` ASC),
  CONSTRAINT `fk_Match_has_Team_Match1`
  FOREIGN KEY (`matchup_id`)
  REFERENCES `scouting`.`matchup` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Match_has_Team_Team1`
  FOREIGN KEY (`team_id`)
  REFERENCES `scouting`.`team` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scouting`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`student` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `active` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `student_uk1` (`first_name` ASC, `last_name` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scouting`.`response`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`response` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NOT NULL,
  `team_matchup_id` INT NOT NULL,
  `response` VARCHAR(400) NOT NULL,
  `student_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Response_Question1_idx` (`question_id` ASC),
  UNIQUE INDEX `response_uk1` (`question_id` ASC, `team_matchup_id` ASC),
  INDEX `fk_Response_TeamMatch1_idx` (`team_matchup_id` ASC),
  INDEX `fk_response_student1_idx` (`student_id` ASC),
  CONSTRAINT `fk_Response_Question1`
  FOREIGN KEY (`question_id`)
  REFERENCES `scouting`.`question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Response_TeamMatch1`
  FOREIGN KEY (`team_matchup_id`)
  REFERENCES `scouting`.`team_matchup` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_response_student1`
  FOREIGN KEY (`student_id`)
  REFERENCES `scouting`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scouting`.`response_value`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scouting`.`response_value` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question_type_id` INT NOT NULL,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ResponseType_QuestionType1_idx` (`question_type_id` ASC),
  CONSTRAINT `fk_ResponseType_QuestionType1`
  FOREIGN KEY (`question_type_id`)
  REFERENCES `scouting`.`question_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
