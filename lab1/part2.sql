use tddd43_lab1;

#A
SET @area_x = "SQL";
select distinct experts.name from experts, experties, topics
where topics.name = @area_x
AND topics.id = experties.topic_id
AND experts.id = experties.expert_id;


#B
SET @area_y = "Computer Science";
select name from topics
where parent_id = (
	select id from topics
	where name = @area_y
);


#C

SET @area_y = "Java";
SET @expert_x = "Jacob";
select name from experts, experties
where experts.id = (
	select recommendee_id from recommendations, experts
	where recommendations.recommender_id = experts.id
	and experts.name = @expert_x
)
and experties.expert_id = experts.id
and experties.topic_id = (
	select id from topics
	where name = @area_y
);


#D
SET @area_y = "Java";
SET @expert_x = "Jacob";
select name from experts, experties
where experts.id = (
	select recommendee_id from recommendations
	where recommendations.recommender_id = ANY(
		select recommendee_id from recommendations, experts
		where recommendations.recommender_id = experts.id
		and experts.name = @expert_x
	)
)
and experties.expert_id = experts.id
and experties.topic_id = (
	select id from topics
	where name = @area_y
);

#5 - Can't be created with query

/* Results
#1
'Jacob'

#2
'Java'
'SQL'

#3
'Oliver'

#4
'John'


