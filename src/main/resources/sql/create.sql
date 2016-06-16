BEGIN;

# SELECT @@global.max_allowed_packet;
# SET GLOBAL max_allowed_packet = 1024*1024*16;

DROP TABLE IF EXISTS `votes`;
DROP TABLE IF EXISTS `images`;
DROP TABLE IF EXISTS `contests`;
DROP TABLE IF EXISTS `seasons`;
DROP TABLE IF EXISTS `users_groups`;
DROP TABLE IF EXISTS `groups`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `app_roles`;
DROP TABLE IF EXISTS `group_types`;
DROP TABLE IF EXISTS `member_status`;

CREATE TABLE `app_roles` (
  `id`   BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(20) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `group_types` (
  `id`   BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(20) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `member_status` (
  `id`     BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(20) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `users` (
  `id`            BIGINT(20)  NOT NULL             AUTO_INCREMENT,
  `creation_date` DATETIME    NOT NULL,
  `email`         VARCHAR(50) NOT NULL UNIQUE,
  `password`      VARCHAR(50) NOT NULL,
  `username`      VARCHAR(30) NOT NULL UNIQUE,
  `app_role_id`   BIGINT(20)  NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_users_app_role_id` FOREIGN KEY (`app_role_id`) REFERENCES `app_roles` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `groups` (
  `id`            BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `creation_date` DATETIME    NOT NULL,
  `description`   VARCHAR(255)         DEFAULT '',
  `name`          VARCHAR(30) NOT NULL UNIQUE,
  `group_type_id` BIGINT(20)  NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_groups_group_type_id` FOREIGN KEY (`group_type_id`) REFERENCES `group_types` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `users_groups` (
  `user_id`          BIGINT(20) NOT NULL,
  `group_id`         BIGINT(20) NOT NULL,
  `member_status_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`group_id`, `user_id`),
  CONSTRAINT `FK_member_status_id` FOREIGN KEY (`member_status_id`) REFERENCES `member_status` (`id`),
  CONSTRAINT `FK_users_groups_group_id` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `FK_users_groups_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `seasons` (
  `id`            BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `creation_date` DATETIME    NOT NULL,
  `description`   VARCHAR(255)         DEFAULT '',
  `finish_date`   DATETIME    NOT NULL,
  `name`          VARCHAR(30) NOT NULL,
  `start_date`    DATETIME    NOT NULL,
  `group_id`      BIGINT(20)  NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_seasons_group_id` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `contests` (
  `id`                    BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `creation_date`         DATETIME    NOT NULL,
  `description`           VARCHAR(255)         DEFAULT '',
  `finish_uploading_date` DATETIME    NOT NULL,
  `finish_voting_date`    DATETIME    NOT NULL,
  `name`                  VARCHAR(30) NOT NULL,
  `start_date`            DATETIME    NOT NULL,
  `season_id`             BIGINT(20)  NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_contests_season_id` FOREIGN KEY (`season_id`) REFERENCES `seasons` (`id`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `images` (
  `id`            BIGINT(20) NOT NULL AUTO_INCREMENT,
  `content`       MEDIUMBLOB NOT NULL,
  `creation_date` DATETIME   NOT NULL,
  `title`         VARCHAR(30)         DEFAULT '',
  `contest_id`    BIGINT(20) NOT NULL,
  `user_id`       BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_images_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_images_contest_id` FOREIGN KEY (`contest_id`) REFERENCES `contests` (`id`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `votes` (
  `image_id`      BIGINT(20)  NOT NULL,
  `user_id`       BIGINT(20)  NOT NULL,
  `rating`        SMALLINT(6) NOT NULL CHECK (`rating` BETWEEN 0 AND 5),
  `creation_date` DATETIME    NOT NULL,
  PRIMARY KEY (`image_id`, `user_id`),
  CONSTRAINT `FK_votes_image_id` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `FK_votes_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO app_roles VALUES
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_USER');

INSERT INTO group_types VALUES
  (1, 'PRIVATE'),
  (2, 'PUBLIC');

INSERT INTO member_status VALUES
  (1, 'MEMBER'),
  (2, 'ADMIN'),
  (3, 'MODERATOR'),
  (4, 'BANNED'),
  (5, 'LEAVER'),
  (6, 'REQUESTER');

COMMIT;