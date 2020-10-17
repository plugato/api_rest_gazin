CREATE TABLE  IF NOT EXISTS `db`.`developer` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `nome` VARCHAR(255) NULL,
 `sexo` VARCHAR(1) NULL,
 `idade` INT NULL,
 `hobby` VARCHAR(255) NULL,
 `datanascimento` DATE NULL,

 PRIMARY KEY (`id`) );
 
