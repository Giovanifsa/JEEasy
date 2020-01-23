CREATE TABLE IF NOT EXISTS `User` (
  `idUser` BIGINT NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(64) NOT NULL,
  `base64SHA512Password` VARCHAR(256) NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `passwordExpirationDate` DATETIME NULL,
  `systemUser` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `UserAuthorization` (
  `idUserAuthorization` INT NOT NULL AUTO_INCREMENT,
  `authorization` VARCHAR(45) NULL,
  `expirationDate` DATETIME NULL,
  `idUser` BIGINT NOT NULL,
  PRIMARY KEY (`idUserAuthorization`),
  INDEX `FKUserAuthorizationToUser` (`idUser` ASC) VISIBLE,
  CONSTRAINT `FKUserAuthorizationToUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `UserRole` (
  `idUserRole` BIGINT NOT NULL AUTO_INCREMENT,
  `roleName` VARCHAR(45) NOT NULL,
  `systemRole` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`idUserRole`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `UserRolePermission` (
  `idUserRolePermission` BIGINT NOT NULL AUTO_INCREMENT,
  `permissionName` VARCHAR(64) NULL,
  PRIMARY KEY (`idUserRolePermission`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `UserMMUserRole` (
  `idUser` BIGINT NOT NULL,
  `idUserRole` BIGINT NOT NULL,
  PRIMARY KEY (`idUser`, `idUserRole`),
  INDEX `FKUserMMUserRoleToUserRoleIndex` (`idUserRole` ASC) VISIBLE,
  INDEX `FKUserMMUserRoleToUserIndex` (`idUser` ASC) VISIBLE,
  CONSTRAINT `FKUserMMUserRoleToUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FKUserMMUserRoleToUserRole`
    FOREIGN KEY (`idUserRole`)
    REFERENCES `UserRole` (`idUserRole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `UserRoleMMUserRolePermission` (
  `idUserRole` BIGINT NOT NULL,
  `idUserRolePermission` BIGINT NOT NULL,
  PRIMARY KEY (`idUserRole`, `idUserRolePermission`),
  INDEX `FKUserRoleMMUserRolePermissionToUserRolePermissionIndex` (`idUserRolePermission` ASC) VISIBLE,
  INDEX `FKUserRoleMMUserRolePermissionToUserRoleIndex` (`idUserRole` ASC) VISIBLE,
  CONSTRAINT `FKUserRoleMMUserRolePermissionToUserRole`
    FOREIGN KEY (`idUserRole`)
    REFERENCES `UserRole` (`idUserRole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FKUserRoleMMUserRolePermissionToUserRolePermission`
    FOREIGN KEY (`idUserRolePermission`)
    REFERENCES `UserRolePermission` (`idUserRolePermission`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;