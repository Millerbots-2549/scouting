ALTER TABLE `scouting`.`survey`
    ADD COLUMN `type` VARCHAR(10) NULL DEFAULT NULL AFTER `name`,
    ADD COLUMN `year` INT(11) NULL DEFAULT NULL AFTER `type`,
    ADD UNIQUE INDEX `survey_udx1` (`name` ASC) INVISIBLE,
    ADD UNIQUE INDEX `survey_udx2` (`type` ASC, `year` ASC) VISIBLE;

UPDATE `scouting`.`survey` SET `type` = 'MATCH', `year` = '2018' WHERE (`id` = '1');
UPDATE `scouting`.`survey` SET `type` = 'PIT', `year` = '2018' WHERE (`id` = '2');
UPDATE `scouting`.`survey` SET `type` = 'MATCH', `year` = '2019' WHERE (`id` = '3');
UPDATE `scouting`.`survey` SET `type` = 'PIT', `year` = '2019' WHERE (`id` = '4');
UPDATE `scouting`.`survey` SET `type` = 'MATCH', `year` = '2020' WHERE (`id` = '5');
UPDATE `scouting`.`survey` SET `type` = 'PIT', `year` = '2020' WHERE (`id` = '6');

ALTER TABLE `scouting`.`survey`
    CHANGE COLUMN `type` `type` VARCHAR(10) NOT NULL,
    CHANGE COLUMN `year` `year` INT(11) NOT NULL;
