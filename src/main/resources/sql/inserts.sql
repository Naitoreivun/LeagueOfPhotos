INSERT INTO app_roles VALUES
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_USER');

INSERT INTO group_types VALUES
  (1, 'PRIVATE'),
  (2, 'PUBLIC');


INSERT INTO groups VALUES
  (1, now(), 'radom', 'grupaNr1-prywatna', 1),
  (2, now(), 'lodz', 'grupaNr2-publcizna', 2),
  (3, now(), 'sosnowiec', 'grupaNr3-publcizna', 2);

INSERT INTO seasons VALUES
  (1, now(), 'sezon 2016 wiosna', '2016-08-05 18:19:03', 'WIOSNA', now(), 2),
  (2, now(), 'XDDDD', '2016-08-05 18:19:03', 'test', now(), 2),
  (3, now(), 'wakacjee', '2016-08-05 18:19:03', 'lato', now(), 2),
  (4, now(), 'snieg', '2016-08-05 18:19:03', 'zima', now(), 2);

INSERT INTO users VALUES
  (1, now(), 'user1@email.com', '\\H', 'naitoreivun', 1), # XD
  (2, now(), 'user2@email.com', 'wxijer', 'stefan', 2), # stefan
  (3, now(), 'user3@email.com', 'lewps567', 'opos3', 2), # haslo123
  (4, now(), 'user4@email.com', 'lewps567', 'opos4', 2), # haslo123
  (5, now(), 'user5@email.com', 'lewps567', 'opos5', 2), # haslo123
  (6, now(), 'a6@email.com', 'e', 'a', 2), # a
  (7, now(), 'user7@email.com', 'lewps567', 'opos7', 2), # haslo123
  (8, now(), 'user8@email.com', 'lewps567', 'opos8', 2); # haslo123

INSERT INTO member_status VALUES
  (1, 'MEMBER'),
  (2, 'ADMIN'),
  (3, 'MODERATOR'),
  (4, 'BANNED'),
  (5, 'LEAVER'),
  (6, 'REQUESTER');

INSERT INTO users_groups VALUES
  (2, 1, 1),
  (3, 1, 2),
  (4, 1, 3),
  (5, 1, 1),
  (6, 2, 2),
  (7, 2, 4),
  (8, 2, 6),
  (2, 2, 6),
  (2, 3, 2),
  (5, 3, 1),
  (6, 3, 6),
  (8, 3, 5);
