CREATE SCHEMA `krisha`
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

CREATE TABLE `krisha`.`raw_entity` (
  `id`                 INT          NOT NULL AUTO_INCREMENT,
  `raw_id`             INT          NOT NULL,
  `category`           VARCHAR(100) NOT NULL,
  `price`              VARCHAR(100) NOT NULL,
  `square_meter`       VARCHAR(100) NOT NULL,
  `rooms`              INT          NOT NULL,
  `city`               VARCHAR(100) NOT NULL,
  `region`             VARCHAR(100) NOT NULL,
  `address`            VARCHAR(100) NOT NULL,
  `post_date`          VARCHAR(100) NOT NULL,
  `internal_condition` VARCHAR(100) NOT NULL,
  `floor`              VARCHAR(100) NOT NULL,
  `house_desc`         TEXT         NOT NULL,
  `contact_type`       VARCHAR(100) NOT NULL,
  `contacts`           VARCHAR(100) NOT NULL,
  `profile_url`        VARCHAR(100) NOT NULL,
  `summary_html`       TEXT         NOT NULL,
  `profile_html`       TEXT         NOT NULL,
  `insert_timestamp`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
