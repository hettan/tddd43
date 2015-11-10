INSERT INTO experts (name, email)
VALUES ('Jacob', 'jacho391@student.liu.se');
SET @expert_jacob = last_insert_id();

INSERT INTO experts (name, email)
VALUES ('Oliver', 'olist125@student.liu.se');
SET @expert_oliver = last_insert_id();

INSERT INTO experts (name, email)
VALUES ('John', 'john@doe.se');
SET @expert_john = last_insert_id();

insert INTO descriptions (text, expert_id)
VALUES ("Header1", @expert_jacob);
SET @header1 = last_insert_id();

insert INTO descriptions (text, parent_subsection_id, expert_id)
VALUES ("I'm a professor!", @header1, @expert_jacob);

insert INTO descriptions (text, expert_id)
VALUES ("Header2", @expert_oliver);
SET @header2 = last_insert_id();

insert INTO descriptions (text, parent_subsection_id, expert_id)
VALUES ("I'm a student!", @header2, @expert_oliver);

insert INTO descriptions (text, expert_id)
VALUES ("Im the third guy!", @expert_john);

INSERT INTO topics (name, text, originator_id)
VALUES ("Computer Science", "This is fun!", @expert_oliver);
SET @cs_topic = last_insert_id();

INSERT INTO experties (topic_id, expert_id)
VALUES (@cs_topic, @expert_oliver);

INSERT INTO topics (name, text, originator_id, parent_id)
VALUES ("Java", "I like object oriented languages!", @expert_oliver, @cs_topic);
SET @java_topic = last_insert_id();

INSERT INTO experties (topic_id, expert_id)
VALUES (@java_topic, @expert_oliver);
INSERT INTO experties (topic_id, expert_id)
VALUES (@java_topic, @expert_jacob);
INSERT INTO experties (topic_id, expert_id)
VALUES (@java_topic, @expert_john);

INSERT INTO topics (name, text, originator_id, parent_id)
VALUES ("SQL", "I like the databases!", @expert_jacob, @cs_topic);
SET @sql_topic = last_insert_id();

INSERT INTO experties (topic_id, expert_id)
VALUES (@sql_topic, @expert_jacob);

INSERT INTO recommendations (recommender_id, recommendee_id, text)
VALUES (@expert_jacob, @expert_oliver, "He likes BM");

INSERT INTO recommendations (recommender_id, recommendee_id, text)
VALUES (@expert_oliver, @expert_john, "GGWP");
