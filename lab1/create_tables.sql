use tddd43_lab1;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS experts;
DROP TABLE IF EXISTS topics;
DROP TABLE IF EXISTS descriptions;
DROP TABLE IF EXISTS recommendations;
DROP TABLE IF EXISTS experties;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE experts
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(50),
	email varchar(50),
	PRIMARY KEY(id)
);

CREATE TABLE topics
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(50),
	text varchar(255),
	parent_id int,
	originator_id int NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (parent_id) REFERENCES topics(id),
	FOREIGN KEY (originator_id) REFERENCES experts(id)
);

CREATE TABLE descriptions
(
	id int NOT NULL AUTO_INCREMENT,
	text varchar(255) NOT NULL,
	parent_subsection_id int,
	expert_id int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (parent_subsection_id) REFERENCES descriptions(id),
	FOREIGN KEY (expert_id) REFERENCES experts(id)
);

CREATE TABLE recommendations
(
	recommender_id int NOT NULL,
	recommendee_id int NOT NULL,
	text varchar(255),
	FOREIGN KEY (recommender_id) REFERENCES experts(id),
	FOREIGN KEY (recommendee_id) REFERENCES experts(id)
);

CREATE TABLE experties
(
	topic_id int NOT NULL,
	expert_id int NOT NULL,
	FOREIGN KEY (topic_id) REFERENCES topics(id),
	FOREIGN KEY (expert_id) REFERENCES experts(id)
);