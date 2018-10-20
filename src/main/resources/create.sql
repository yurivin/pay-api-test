--liquibase formatted sql

--cahangeset yvinogradov:create_account
CREATE TABLE `test`.`account` (
  `id` bigint(20) NOT NULL,
  `balance` decimal(19,8) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `test`.`account`  (id, balance) VALUES (1, 10);
INSERT INTO `test`.`account`  (id, balance) VALUES (2, 10);