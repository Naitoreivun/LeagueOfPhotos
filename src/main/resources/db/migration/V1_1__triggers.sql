#**********************************************#
#                                              #
#                HASH PASSWORDS:               #
#                                              #
#**********************************************#

DELIMITER //
DROP TRIGGER IF EXISTS hash_user_password //

CREATE TRIGGER hash_user_password
BEFORE INSERT ON users
FOR EACH ROW
  BEGIN
    SET new.password = sha2(new.password, 0);
  END //
DELIMITER ;



DELIMITER //
DROP PROCEDURE IF EXISTS validate_password //

CREATE PROCEDURE validate_password(IN p_password VARCHAR(64), IN p_user_id BIGINT(20), OUT is_valid BOOL)
  BEGIN
    DECLARE hashed_password VARCHAR(64);

    SET hashed_password = sha2(p_password, 0);

    IF hashed_password = (SELECT u.password
                          FROM users u
                          WHERE u.id = p_user_id)
    THEN
      SET is_valid = TRUE;
    ELSE
      SET is_valid = FALSE;
    END IF;

  END //
DELIMITER ;


#################################################################


DELIMITER //
DROP PROCEDURE IF EXISTS validate_dates //

CREATE PROCEDURE validate_dates(IN date_before DATETIME, IN date_after DATETIME, IN message VARCHAR(200))
  BEGIN
    DECLARE my_error CONDITION FOR SQLSTATE '45000';
    IF date_before > date_after
    THEN
      SIGNAL my_error
      SET MESSAGE_TEXT = message;
    END IF;
  END //
DELIMITER ;




DELIMITER //
DROP PROCEDURE IF EXISTS validate_affiliation_of_user_to_group //

CREATE PROCEDURE validate_affiliation_of_user_to_group(IN _user_id BIGINT(20), IN _group_id BIGINT(20))
  BEGIN
    DECLARE my_error CONDITION FOR SQLSTATE '45000';
    DECLARE message VARCHAR(200);
    DECLARE user_name VARCHAR(30);
    DECLARE group_name VARCHAR(30);

    IF (SELECT count(*)
        FROM users_groups ug
          JOIN member_status ms ON ug.member_status_id = ms.id
        WHERE ug.user_id = _user_id
              AND ug.group_id = _group_id
              AND ms.status IN ('MEMBER', 'MODERATOR', 'ADMIN')
       ) = 0
    THEN
      SET user_name = (SELECT username
                       FROM users
                       WHERE id = _user_id);
      SET group_name = (SELECT name
                        FROM groups
                        WHERE id = _group_id);
      SET message = coalesce(concat('user ', user_name, ' doesn''t belong to group ', group_name),
                             'image''s owner or group does not exists');

      SIGNAL my_error
      SET MESSAGE_TEXT = message;
    END IF;
  END //
DELIMITER ;



#**********************************************#
#                                              #
#             SEASON VALIDATORS:               #
#                                              #
#**********************************************#


DELIMITER //
DROP TRIGGER IF EXISTS seasons_date_validator__insert //

CREATE TRIGGER seasons_date_validator__insert
BEFORE INSERT ON seasons
FOR EACH ROW
  BEGIN
    CALL validate_dates(new.start_date, new.finish_date,
                        'start_date has to be earlier than finish_date');

  END //
DELIMITER ;



DELIMITER //
DROP TRIGGER IF EXISTS seasons_date_validator__update //

CREATE TRIGGER seasons_date_validator__update
BEFORE UPDATE ON seasons
FOR EACH ROW
  BEGIN
    DECLARE contests_start_date DATETIME;
    DECLARE contests_finish_date DATETIME;
    CALL validate_dates(new.start_date, new.finish_date,
                        'start_date has to be earlier than finish_date');

    SET contests_start_date = (SELECT min(c.start_date)
                               FROM contests c
                               WHERE c.season_id = new.id);

    CALL validate_dates(new.start_date, contests_start_date,
                        'start_date (season) has to be earlier than start_date (season''s contests)');


    SET contests_finish_date = (SELECT max(c.finish_voting_date)
                                FROM contests c
                                WHERE c.season_id = new.id);

    CALL validate_dates(contests_finish_date, new.finish_date,
                        'finish_voting_date (season''s contests) has to be earlier than finish_date (season)');
  END //
DELIMITER ;


#**********************************************#
#                                              #
#            CONTEST VALIDATORS:               #
#                                              #
#**********************************************#


DELIMITER //
DROP TRIGGER IF EXISTS contests_date_validator__insert //

CREATE TRIGGER contests_date_validator__insert
BEFORE INSERT ON contests
FOR EACH ROW
  BEGIN
    CALL validate_dates(new.start_date, new.finish_uploading_date,
                        'start_date has to be earlier than finish_uploading_date');
    CALL validate_dates(new.finish_uploading_date, new.finish_voting_date,
                        'finish_uploading_date has to be earlier than finish_voting_date');
  END //
DELIMITER ;


DELIMITER //
DROP TRIGGER IF EXISTS contests_date_validator__update //

CREATE TRIGGER contests_date_validator__update
BEFORE UPDATE ON contests
FOR EACH ROW
  BEGIN
    DECLARE season_start_date DATETIME;
    DECLARE season_finish_date DATETIME;

    CALL validate_dates(new.start_date, new.finish_uploading_date,
                        'start_date has to be earlier than finish_uploading_date');
    CALL validate_dates(new.finish_uploading_date, new.finish_voting_date,
                        'finish_uploading_date has to be earlier than finish_voting_date');

    SET season_start_date = (SELECT s.start_date
                             FROM seasons s
                             WHERE s.id = new.season_id);

    CALL validate_dates(season_start_date, new.start_date,
                        'start_date (season) has to be earlier than start_date (season''s contest)');


    SET season_finish_date = (SELECT s.finish_date
                              FROM seasons s
                              WHERE s.id = new.season_id);

    CALL validate_dates(new.finish_voting_date, season_finish_date,
                        'finish_voting_date (season''s contest) has to be earlier than finish_date (season)');
  END //
DELIMITER ;


#**********************************************#
#                                              #
#              IMAGE VALIDATORS:               #
#                                              #
#**********************************************#


DELIMITER //
DROP PROCEDURE IF EXISTS validate_uploading_image_date //

CREATE PROCEDURE validate_uploading_image_date(IN uploading_date DATETIME, IN contest_id BIGINT(20))
  BEGIN
    DECLARE contest_start_date DATETIME;
    DECLARE contest_finish_uploading_date DATETIME;

    SET contest_start_date = (SELECT c.start_date
                              FROM contests c
                              WHERE c.id = contest_id);

    CALL validate_dates(contest_start_date, uploading_date,
                        'you can''t upload image before contest start');

    SET contest_finish_uploading_date = (SELECT c.finish_uploading_date
                                         FROM contests c
                                         WHERE c.id = contest_id);

    CALL validate_dates(uploading_date, contest_finish_uploading_date,
                        'you can''t upload image after contest''s uploading phase');

  END //
DELIMITER ;


DELIMITER //
DROP TRIGGER IF EXISTS image_validator__insert //

CREATE TRIGGER image_validator__insert
BEFORE INSERT ON images
FOR EACH ROW
  BEGIN
    DECLARE group_id BIGINT(20);
    CALL validate_uploading_image_date(new.creation_date, new.contest_id);

    SET group_id = (SELECT s.group_id
                    FROM seasons s
                      JOIN contests c ON s.id = c.season_id
                    WHERE c.id = new.contest_id);

    CALL validate_affiliation_of_user_to_group(new.user_id, group_id);
  END //
DELIMITER ;



DELIMITER //
DROP TRIGGER IF EXISTS image_validator__update //

CREATE TRIGGER image_validator__update
BEFORE UPDATE ON images
FOR EACH ROW
  BEGIN
    DECLARE group_id BIGINT(20);
    CALL validate_uploading_image_date(new.creation_date, new.contest_id);

    SET group_id = (SELECT s.group_id
                    FROM seasons s
                      JOIN contests c ON s.id = c.season_id
                    WHERE c.id = new.contest_id);

    CALL validate_affiliation_of_user_to_group(new.user_id, group_id);
  END //
DELIMITER ;


#**********************************************#
#                                              #
#               VOTE VALIDATORS:               #
#                                              #
#**********************************************#


DELIMITER //
DROP PROCEDURE IF EXISTS validate_voting_date //

CREATE PROCEDURE validate_voting_date(IN voting_date DATETIME, IN image_id BIGINT(20))
  BEGIN
    DECLARE contest_finish_uploading_date DATETIME;
    DECLARE contest_finish_voting_date DATETIME;

    SET contest_finish_uploading_date = (SELECT c.finish_uploading_date
                                         FROM contests c
                                           JOIN images i ON c.id = i.contest_id
                                         WHERE i.id = image_id);

    CALL validate_dates(contest_finish_uploading_date, voting_date,
                        'you can''t vote before contest''s voting phase');

    SET contest_finish_voting_date = (SELECT c.finish_voting_date
                                      FROM contests c
                                        JOIN images i ON c.id = i.contest_id
                                      WHERE i.id = image_id);

    CALL validate_dates(voting_date, contest_finish_voting_date,
                        'you can''t vote after contest''s voting phase');

  END //
DELIMITER ;




DELIMITER //
DROP PROCEDURE IF EXISTS validate_rating //

CREATE PROCEDURE validate_rating(IN rating SMALLINT(6))
  BEGIN
    DECLARE my_error CONDITION FOR SQLSTATE '45000';
    IF rating NOT BETWEEN 1 AND 5
    THEN
      SIGNAL my_error
      SET MESSAGE_TEXT = 'rating must take a value between 1 and 5';
    END IF;
  END //
DELIMITER ;






DELIMITER //
DROP TRIGGER IF EXISTS vote_validator__insert //

CREATE TRIGGER vote_validator__insert
BEFORE INSERT ON votes
FOR EACH ROW
  BEGIN
    DECLARE group_id BIGINT(20);
    CALL validate_voting_date(new.creation_date, new.image_id);

    SET group_id = (SELECT s.group_id
                    FROM seasons s
                      JOIN contests c ON s.id = c.season_id
                      JOIN images i ON c.id = i.contest_id
                    WHERE i.id = new.image_id
    );

    CALL validate_affiliation_of_user_to_group(new.user_id, group_id);
    CALL validate_rating(new.rating);
  END //
DELIMITER ;


DELIMITER //
DROP TRIGGER IF EXISTS vote_validator__update //

CREATE TRIGGER vote_validator__update
BEFORE UPDATE ON votes
FOR EACH ROW
  BEGIN
    DECLARE group_id BIGINT(20);
    CALL validate_voting_date(new.creation_date, new.image_id);

    SET group_id = (SELECT s.group_id
                    FROM seasons s
                      JOIN contests c ON s.id = c.season_id
                      JOIN images i ON c.id = i.contest_id
                    WHERE i.id = new.image_id
    );

    CALL validate_affiliation_of_user_to_group(new.user_id, group_id);
    CALL validate_rating(new.rating);
  END //
DELIMITER ;

#**********************************************#
#                                              #
#              GROUP VALIDATORS:               #
#                                              #
#**********************************************#


DELIMITER //
DROP FUNCTION IF EXISTS count_user_in_group //

CREATE FUNCTION count_user_in_group(_group_id BIGINT(20))
  RETURNS INTEGER
  BEGIN
    RETURN coalesce((SELECT count(*)
                     FROM users_groups ug
                       JOIN member_status ms ON ug.member_status_id = ms.id
                     WHERE ug.group_id = _group_id
                           AND ms.status IN ('MEMBER', 'MODERATOR', 'ADMIN')),
                    0);
  END //
DELIMITER ;


DELIMITER //
DROP TRIGGER IF EXISTS safely_remove_users_from_group //

CREATE TRIGGER safely_remove_users_from_group
BEFORE DELETE ON groups
FOR EACH ROW
  BEGIN
    DECLARE admin_id BIGINT(20);
    IF count_user_in_group(old.id) > 0
    THEN
      SET admin_id = (SELECT ug.user_id
                      FROM users_groups ug
                        JOIN member_status ms ON ug.member_status_id = ms.id
                      WHERE ug.group_id = old.id AND ms.status LIKE 'ADMIN'
                      LIMIT 1);

      DELETE FROM users_groups
      WHERE group_id = old.id AND user_id <> admin_id;

      DELETE FROM users_groups
      WHERE group_id = old.id AND user_id = admin_id;
    END IF;
  END //
DELIMITER ;


DELIMITER //
DROP TRIGGER IF EXISTS prevent_last_admin_from_leaving_group //

CREATE TRIGGER prevent_last_admin_from_leaving_group
BEFORE DELETE ON users_groups
FOR EACH ROW
  BEGIN
    DECLARE my_error CONDITION FOR SQLSTATE '45000';

    IF
    'ADMIN' LIKE (SELECT ms.status
                  FROM member_status ms
                  WHERE ms.id = old.member_status_id)
    AND
    count_user_in_group(old.group_id) > 1
    AND
    1 = (SELECT COUNT(*)
         FROM users_groups ug
           JOIN member_status ms ON ug.member_status_id = ms.id
         WHERE ug.group_id = old.group_id AND ms.status LIKE 'ADMIN')
    THEN
      SIGNAL my_error
      SET MESSAGE_TEXT = 'the last admin of group can''t leave the group';
    END IF;
  END //
DELIMITER ;
