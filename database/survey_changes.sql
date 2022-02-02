ALTER TABLE `scouting`.`question` ADD COLUMN `editable` TINYINT NOT NULL DEFAULT 0 AFTER `type`;
ALTER TABLE `scouting`.`choice_group` ADD COLUMN `editable` TINYINT NOT NULL DEFAULT 0 AFTER `description`;
ALTER TABLE `scouting`.`survey` ADD COLUMN `editable` TINYINT NOT NULL DEFAULT 0 AFTER `year`;

