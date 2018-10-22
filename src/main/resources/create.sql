--liquibase formatted sql

--cahangeset yvinogradov:create_account
CREATE TABLE `test`.`account` (
  `id` bigint(20) NOT NULL,
  `balance` decimal(19,8) DEFAULT NULL,
  `e_version` int DEFAULT 1,
  PRIMARY KEY (`id`)
);

INSERT INTO `test`.`account`  (id, balance, e_version) VALUES (1, 10, 1);
INSERT INTO `test`.`account`  (id, balance, e_version) VALUES (2, 10, 1);