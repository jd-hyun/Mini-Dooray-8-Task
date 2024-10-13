CREATE TABLE `projects` (
	`project_id`	bigint	NOT NULL AUTO_INCREMENT,
	`project_title`	varchar(100)	NOT NULL,
	`project_state`	varchar(10)	NOT NULL	DEFAULT 'ACTIVE',
	`author_id`	varchar(50)	NOT NULL,
    PRIMARY KEY (`project_id`)
);

CREATE TABLE `tasks` (
	`task_id`	bigint	NOT NULL AUTO_INCREMENT,
	`project_id`	bigint	NOT NULL,
	`task_title`	varchar(100)	NOT NULL,
	`task_contents`	text	NOT NULL, 
    PRIMARY KEY (`task_id`),
    CONSTRAINT `FK_TASK_PROJECT` FOREIGN KEY (`project_id`) REFERENCES `projects`(`project_id`) ON DELETE CASCADE
);

CREATE TABLE `comments` (
	`comment_id`	bigint	NOT NULL AUTO_INCREMENT,
	`task_id`	bigint	NOT NULL,
	`comment_created_at`	datetime	NOT NULL,
	`comment_contents`	text	NOT NULL,
    `author_id`	varchar(50)	NOT NULL,
    PRIMARY KEY (`comment_id`),
    CONSTRAINT `FK_COMMENT_TASK` FOREIGN KEY (`task_id`) REFERENCES `tasks`(`task_id`) ON DELETE CASCADE
);

CREATE TABLE `milestones` (
	`milestone_id`	bigint	NOT NULL AUTO_INCREMENT,
	`project_id`	bigint	NOT NULL,
	`milestone_title`	varchar(100)	NOT NULL,
	`start_date`	datetime	NULL,
	`end_date`	datetime	NULL,
    PRIMARY KEY (`milestone_id`),
    CONSTRAINT `FK_MILESTONE_PROJECT` FOREIGN KEY (`project_id`) REFERENCES `projects`(`project_id`) ON DELETE CASCADE
);

CREATE TABLE `tags` (
	`tag_id`	bigint	NOT NULL AUTO_INCREMENT,
	`project_id`	bigint	NOT NULL,
	`tag_name`	varchar(100)	NOT NULL,
    PRIMARY KEY (`tag_id`),
    CONSTRAINT `FK_TAG_PROJECT` FOREIGN KEY (`project_id`) REFERENCES `projects`(`project_id`) ON DELETE CASCADE
);

CREATE TABLE `tasks_tags` (
	`task_tag_id`	bigint	NOT NULL AUTO_INCREMENT,
	`tag_id`	bigint	NOT NULL,
	`task_id`	bigint	NOT NULL,
    PRIMARY KEY (`task_tag_id`),
    CONSTRAINT `FK_TASKS_TAGS_TASK` FOREIGN KEY (`task_id`) REFERENCES `tasks`(`task_id`) ON DELETE CASCADE,
    CONSTRAINT `FK_TASKS_TAGS_TAG` FOREIGN KEY (`tag_id`) REFERENCES `tags`(`tag_id`) ON DELETE CASCADE
);

CREATE TABLE `projects_accounts` (
	`project_account_id`	bigint	NOT NULL AUTO_INCREMENT,
	`project_id`	bigint	NOT NULL,
	`author_id`	varchar(50)	NOT NULL,
	`role`	varchar(10)	NOT NULL	COMMENT 'ADMIN, USER',
    PRIMARY KEY (`project_account_id`),
    CONSTRAINT `FK_PROJECTS_ACCOUNTS_PROJECT` FOREIGN KEY (`project_id`) REFERENCES `projects`(`project_id`) ON DELETE CASCADE
);

CREATE TABLE `tasks_milestones` (
	`task_milestone_id`	bigint	NOT NULL AUTO_INCREMENT,
	`milestone_id`	bigint	NOT NULL,
	`task_id`	bigint	NOT NULL,
    PRIMARY KEY (`task_milestone_id`),
    CONSTRAINT `FK_TASK_MILESTONE_TASK` FOREIGN KEY (`task_id`) REFERENCES `tasks`(`task_id`) ON DELETE CASCADE,
    CONSTRAINT `FK_TASK_MILESTONE_MILESTONE` FOREIGN KEY (`milestone_id`) REFERENCES `milestones`(`milestone_id`) ON DELETE CASCADE
);