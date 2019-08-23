CREATE DATABASE `myblog` DEFAULT CHARACTER SET utf8;
USE `myblog`;


drop table if exists `person`;
create table `person`(
    `id` int(11) not null auto_increment,
    `username` varchar(255) not null comment'用户名',
    `job` varchar(255) not null comment '工作',
    `age` int(3) not null comment '年龄',
    primary key (`id`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;

insert into `person` values( 1,'zrj','全栈工程师',24);

drop table if exists `blog`;
create table `blog`(
    `blogId` int(11) not null auto_increment,
    `title` varchar(255) not null comment '标题',
    `content` text not null comment '内容',
    `blogType` int(1) not null comment '类型',
    `tags` varchar(255) not null comment'标签',
    `date` DATE not null comment'创建日期',
    `view_count` int(10) not null default 0 comment'浏览数',
    `like_count` int(10)not null default 0 comment'点赞数',
    primary key (`blogId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists `addblog`;
create table `addblog`(
    `id` int(11) not null auto_increment,
    `pid` int(11) default null comment '用户名',
    `bid` int(11) default null comment '博客ID',
    primary key (`id`),
    constraint `person_blog_1` foreign key (`pid`) references `person`(`id`),
    constraint `person_blog_2` foreign key (`bid`) references `blog` (`blogId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



drop table if exists `user_post_like`;
create table `user_post_like`(
    `id` int(11) not null auto_increment,
    `user_id` int(11) not null,
    `post_id` int(11) not null,
    primary key(`id`),
    constraint `user_post_like_1` foreign key(`user_id`) references `person`(`id`),
    constraint `user_post_like_2` foreign key(`post_id`) references `blog`(`blogId`)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;




