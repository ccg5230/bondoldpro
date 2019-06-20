DROP TABLE IF EXISTS reader;
DROP TABLE IF EXISTS writer;

CREATE TABLE `reader` (`id` INT  NOT NULL AUTO_INCREMENT,`firstName`  VARCHAR(20) NULL,`lastName`   VARCHAR(20) NULL,`random_num` VARCHAR(20) NULL, PRIMARY KEY (`id`));

CREATE TABLE `writer` (`id` INT  NOT NULL AUTO_INCREMENT,`full_name`  VARCHAR(40) NULL,`random_num` VARCHAR(20) NULL, PRIMARY KEY (`id`));

INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('abc', 'def', '11');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('def', 'zhu', '12');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('dummy', 'name', '13');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('non', 'pay', '14');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('spring', 'batch', '15');


select * from reader;
select * from writer;
delete from writer;