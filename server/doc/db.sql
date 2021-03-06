-- MySQL Script generated by MySQL Workbench
-- 08/27/15 09:06:25
-- Model: New Model    Version: 1.0
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `db` ;
CREATE SCHEMA IF NOT EXISTS `db` DEFAULT CHARACTER SET utf8 ;
USE `db` ;

-- -----------------------------------------------------
-- Table `db`.`sys_permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db`.`sys_permission` ;

CREATE TABLE IF NOT EXISTS `db`.`sys_permission` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(100) NOT NULL COMMENT '权限编码，形如user:add、user:view',
  `NAME` VARCHAR(100) NOT NULL COMMENT '权限名称',
  `TYPE_CODE` VARCHAR(255) NOT NULL COMMENT '权限类型，包括页面权限、其他权限',
  `PID` BIGINT(20) NULL COMMENT '父权限',
  `PIDS` VARCHAR(100) NULL DEFAULT NULL COMMENT '以逗号区隔的父权限组',
  `WEIGHT` INT(11) NULL COMMENT '排序',
  `STATUS` TINYINT(4) NULL COMMENT '状态，启用，禁用',
  `URL` VARCHAR(200) NULL DEFAULT NULL COMMENT '权限地址，权限类型为页面权限时生效',
  `ICON` VARCHAR(45) NULL DEFAULT NULL COMMENT '图标，展示时有用',
  `EXTRA` TEXT NULL DEFAULT NULL COMMENT '扩展字段，JSON格式',
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `CODE_UNIQUE` (`CODE` ASC),
  INDEX `idx_permission` (`ID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = gbk;


-- -----------------------------------------------------
-- Table `db`.`sys_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db`.`sys_role` ;

CREATE TABLE IF NOT EXISTS `db`.`sys_role` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(45) NOT NULL COMMENT '角色编码',
  `NAME` VARCHAR(45) NULL COMMENT '角色名称',
  `STATUS` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '状态，启用，禁用',
  `ASDEFAULT` TINYINT(1) NULL DEFAULT '0' COMMENT '是否作为默认角色【用户新增时自动授予】',
  `INTRO` TEXT NULL DEFAULT NULL COMMENT '简介',
  `EXTRA` TEXT NULL DEFAULT NULL COMMENT '扩展字段，JSON格式',
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `VCODE_UNIQUE` (`CODE` ASC),
  INDEX `idx_role` (`ID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = gbk;


-- -----------------------------------------------------
-- Table `db`.`sys_role_permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db`.`sys_role_permission` ;

CREATE TABLE IF NOT EXISTS `db`.`sys_role_permission` (
  `PK_ROLE` BIGINT(20) NOT NULL,
  `PK_PERMISSION` BIGINT(20) NOT NULL,
  PRIMARY KEY (`PK_ROLE`, `PK_PERMISSION`),
  CONSTRAINT `fk_role_permission_role1`
    FOREIGN KEY (`PK_ROLE`)
    REFERENCES `db`.`sys_role` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_role_permission_permission1`
    FOREIGN KEY (`PK_PERMISSION`)
    REFERENCES `db`.`sys_permission` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = gbk;


-- -----------------------------------------------------
-- Table `db`.`sys_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db`.`sys_user` ;

CREATE TABLE IF NOT EXISTS `db`.`sys_user` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `USERNAME` VARCHAR(45) NOT NULL COMMENT '登录名',
  `PASSWORD` VARCHAR(100) NOT NULL,
  `SALT` VARCHAR(100) NOT NULL,
  `STATUS` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '用户状态',
  `DISPLAYNAME` VARCHAR(100) NULL COMMENT '显示名称，昵称',
  `EMAIL` VARCHAR(100) NULL COMMENT '邮箱',
  `CREATETIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `EXTRA` TEXT NULL DEFAULT NULL COMMENT '扩展字段，JSON格式',
  `SOCIALTYPE` VARCHAR(100) NULL COMMENT '社会化登录类型，QQ，微信，微博等',
  `SOCIALID` VARCHAR(100) NULL COMMENT '社会化登录身份',
  `SOCIALEXTRA` TEXT NULL COMMENT '社会化登录的扩展字段，JSON格式',
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `CODE_UNIQUE` (`USERNAME` ASC),
  UNIQUE INDEX `EMAIL_UNIQUE` (`EMAIL` ASC),
  INDEX `idx_user` (`ID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = gbk;


-- -----------------------------------------------------
-- Table `db`.`sys_user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db`.`sys_user_role` ;

CREATE TABLE IF NOT EXISTS `db`.`sys_user_role` (
  `PK_USER` BIGINT(20) NOT NULL DEFAULT '0',
  `PK_ROLE` BIGINT(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`PK_USER`, `PK_ROLE`),
  CONSTRAINT `fk_user_role_user`
    FOREIGN KEY (`PK_USER`)
    REFERENCES `db`.`sys_user` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_role1`
    FOREIGN KEY (`PK_ROLE`)
    REFERENCES `db`.`sys_role` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = gbk;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
