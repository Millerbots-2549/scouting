USE scouting;

DROP VIEW `scouting`.`warehouse`;

ALTER TABLE `scouting`.`survey`
    ADD COLUMN `type` VARCHAR(10) NULL DEFAULT NULL AFTER `name`,
    ADD COLUMN `year` INT         NULL DEFAULT NULL AFTER `type`,
    ADD UNIQUE INDEX `survey_udx1` (`name` ASC) INVISIBLE,
    ADD UNIQUE INDEX `survey_udx2` (`type` ASC, `year` ASC) VISIBLE;

UPDATE `scouting`.`survey` SET `type` = 'MATCH',`year` = '2018' WHERE (`id` = '1');
UPDATE `scouting`.`survey` SET `type` = 'PIT',`year` = '2018' WHERE (`id` = '2');
UPDATE `scouting`.`survey` SET `type` = 'MATCH',`year` = '2019' WHERE (`id` = '3');
UPDATE `scouting`.`survey` SET `type` = 'PIT',`year` = '2019' WHERE (`id` = '4');
UPDATE `scouting`.`survey` SET `type` = 'MATCH', `year` = '2020' WHERE (`id` = '5');
UPDATE `scouting`.`survey` SET `type` = 'PIT', `year` = '2020' WHERE (`id` = '6');

ALTER TABLE `scouting`.`survey`
    CHANGE COLUMN `type` `type` VARCHAR(10) NOT NULL,
    CHANGE COLUMN `year` `year` INT NOT NULL;

ALTER TABLE `scouting`.`question`
    ADD COLUMN `type` VARCHAR(15) NULL DEFAULT NULL AFTER `question`;

update scouting.question q set q.type = (select upper(description) from scouting.question_type qt where q.question_type_id = qt.id);

ALTER TABLE `scouting`.`question` DROP FOREIGN KEY `fk_Question_QuestionType1`;
ALTER TABLE `scouting`.`response_value` DROP FOREIGN KEY `fk_ResponseType_QuestionType1`;

ALTER TABLE `scouting`.`question`
    CHANGE COLUMN `question_type_id` `choice_group_id` INT NULL DEFAULT NULL,
    CHANGE COLUMN `type` `type` VARCHAR(15) NOT NULL,
    DROP INDEX `fk_Question_QuestionType1_idx`,
    ADD INDEX `fk_question_idx1` (`choice_group_id` ASC) VISIBLE;
ALTER TABLE `scouting`.`question` RENAME INDEX `fk_question_survey_section1_idx` TO `fk_question_idx2`;
ALTER TABLE `scouting`.`question` ALTER INDEX `fk_question_idx2` VISIBLE;

ALTER TABLE `scouting`.`question_type`
    CHANGE COLUMN `description` `description` VARCHAR(100) NOT NULL,
    RENAME TO `scouting`.`choice_group`;

ALTER TABLE `scouting`.`response_value`
    CHANGE COLUMN `question_type_id` `choice_group_id` INT NOT NULL,
    CHANGE COLUMN `value` `value` VARCHAR(50) NOT NULL,
    DROP INDEX `fk_ResponseType_QuestionType1_idx`,
    ADD INDEX `fk_choice_idx1` (`choice_group_id` ASC) INVISIBLE,
    RENAME TO `scouting`.`choice`;

ALTER TABLE `scouting`.`question`
    ADD CONSTRAINT `fk_question_choice_group1`
        FOREIGN KEY (`choice_group_id`)
            REFERENCES `scouting`.`choice_group` (`id`);

ALTER TABLE `scouting`.`choice`
    ADD CONSTRAINT `fk_choice_choice_group1`
        FOREIGN KEY (`choice_group_id`)
            REFERENCES `scouting`.`choice_group` (`id`);

CREATE VIEW `warehouse` AS
select `r`.`question_id` AS `question_id`,
       `tm`.`team_id` AS `team_id`,
       `tm`.`matchup_id` AS `matchup_id`,
       `tr`.`event_id` AS `event_id`,
       `e`.`name` AS `event_name`,
       `t`.`name` AS `team_name`,
       `m`.`match_number` AS `match_number`,
       `tm`.`alliance` AS `alliance`,
       `r`.`response` AS `response`,
       `tr`.`team_rank` AS `team_rank`,
       (case
           when (`tm`.`alliance` = 'red') then `m`.`red_score`
           when (`tm`.`alliance` = 'blue') then `m`.`blue_score`
           else NULL end) AS `alliance_score`,
       `q`.`question` AS `question`
from `response` `r`
    join `question` `q` on `r`.`question_id` = `q`.`id`
    join `team_matchup` `tm` on `r`.`team_matchup_id` = `tm`.`id`
    join `matchup` `m` on `tm`.`matchup_id` = `m`.`id`
    join `event` `e` on `m`.`event_id` = `e`.`id`
    join `team` `t` on `tm`.`team_id` = `t`.`id`
    join `team_ranking` `tr` on `tr`.`team_id` = `t`.`id` and `tr`.`event_id` = `e`.`id`
where `q`.`type` <> 'TEXT' and `tm`.`alliance` <> 'pit';

update question set choice_group_id = null where type in ('NUMERIC', 'TEXT', 'BOOLEAN');
delete from choice where choice_group_id in (select id from choice_group where description in ('numeric', 'text', 'boolean'));
delete from choice_group where description in ('numeric', 'text', 'boolean');

update choice_group set description = 'cube count choices' where id = 3;
update choice_group set description = 'game outcome choices' where id = 4;
update choice_group set description = 'climb choices' where id = 6;
update choice_group set description = 'Yes/No/Maybe' where id = 7;
update choice_group set description = 'vision system choices' where id = 8;
update choice_group set description = 'Good/Bad/Ok' where id = 9;
update choice_group set description = 'Left/Right/Middle' where id = 10;
update choice_group set description = 'programming language choices' where id = 11;
update choice_group set description = '1/2/3' where id = 12;
update choice_group set description = 'Ball/Panel/None' where id = 13;
update choice_group set description = 'robot starting positions' where id = 14;
update choice_group set description = '1/2/3/4/5' where id = 15;
update choice_group set description = 'Robot Build Choices' where id = 16;
update choice_group set description = 'type of automation' where id = 17;