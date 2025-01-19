CREATE DATABASE IF NOT EXISTS `JAVAFX_APP`;
USE `JAVAFX_APP`;

CREATE TABLE IF NOT EXISTS `PRODUCT_TYPE` (
    `ID` INT(11) NOT NULL AUTO_INCREMENT,
    `TYP_CODE` VARCHAR(30) NOT NULL,
    `PROPERTY_NAME` VARCHAR(100) NOT NULL,
    `CREATED_AT` DATETIME NOT NULL,
    `MODIFIED_AT` DATETIME DEFAULT NULL,
    CONSTRAINT `PK_PRODUCT_TYPE` PRIMARY KEY(`ID`),
    CONSTRAINT `UQ_PRODUCT_TYPE_CODE` UNIQUE(`TYP_CODE`)
);

CREATE TABLE IF NOT EXISTS `PRODUCT` (
    `ID` INT(11) NOT NULL AUTO_INCREMENT,
    `PROPERTY_NAME` VARCHAR(100) NOT NULL,
    `PRODUCT_CODE` CHAR(10) NOT NULL,
    `PRICE` DECIMAL(19,2) NOT NULL,
    `CREATED_AT` DATETIME NOT NULL,
    `MODIFIED_AT` DATETIME DEFAULT NULL,
    `PRODUCT_TYPE` INT(11) NOT NULL,
    CONSTRAINT `PK_PRODUCT` PRIMARY KEY(`ID`),
    CONSTRAINT `UQ_PRODUCT_PROPERTY` UNIQUE(`PROPERTY_NAME`),
    CONSTRAINT `UQ_PRODUCT_CODE` UNIQUE(`PRODUCT_CODE`),
    CONSTRAINT `FK_PRODUCT_TYPE` FOREIGN KEY (`PRODUCT_TYPE`) REFERENCES `PRODUCT_TYPE`(`ID`)
);

CREATE TABLE IF NOT EXISTS `CUSTOMER_ORDER` (
    `ID` INT(11) NOT NULL AUTO_INCREMENT,
    `ORDER_CODE` CHAR(20) NOT NULL,
    `CREATED_AT` DATETIME NOT NULL,
    `MODIFIED_AT` DATETIME DEFAULT NULL,
    `PROCESSED` TINYINT(1) DEFAULT 0 NOT NULL,
    CONSTRAINT `PK_CUSTOMER_ORDER` PRIMARY KEY(`ID`),
    CONSTRAINT `UQ_CUSTOMER_ORDER_CODE` UNIQUE(`ORDER_CODE`)
);

CREATE TABLE IF NOT EXISTS `PRODUCT_ORDER` (
    `ID` INT(11) NOT NULL AUTO_INCREMENT,
    `ORDER_ID` INT(11) NOT NULL,
    `PRODUCT_ID` INT(11) NOT NULL,
    `QUANTITY` INT(11) NOT NULL,
    CONSTRAINT `PK_PRODUCT_ORDER` PRIMARY KEY(`ID`),
    CONSTRAINT `FK_PO_ORDER` FOREIGN KEY (`ORDER_ID`) REFERENCES `CUSTOMER_ORDER`(`ID`),
    CONSTRAINT `FK_PO_PRODUCT` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT`(`ID`)
);
