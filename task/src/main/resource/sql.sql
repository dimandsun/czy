
create table task(
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,task_type tinyint comment '任务类型：1紧急重要/2紧急不重要/3不紧急重要/4不紧急不重要'
	,frequency tinyint comment '任务循环频率:1每日/2每周/3每月/4每年/5工作日/6节假日/7非循环单次任务/8非循环任意任务/9其他'
	,remind_year nvarchar(10) comment '提醒年.非循环任务时使用'
	,remind_month nvarchar(10) comment '提醒月.从1开始，年度任务时使用'
	,remind_day nvarchar(10) comment '提醒日，每月的几号'
	,remind_week_day nvarchar(10) comment '1-周一/2-周二/3-周三/4-周四/5-周五/6-周六/7-周日'
	,remind_time nvarchar(10) comment '提醒时间/只包含时间/时分/所有任务都使用'
	,create_time datetime comment '任务创建时间'
	,create_code nvarchar(20) comment '任务创建者'
	,task_group_id int comment '任务分组：task_group表主键'
	,des nvarchar(20) comment '说明'
)COMMENT='任务';

create table task_group(
    id int primary key auto_increment
    ,parent_id int comment '父id'
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,des nvarchar(20) comment '说明'
)COMMENT='任务分组';

create table task_user(
     id int primary key auto_increment
	,name nvarchar(20) comment ''
	,num int comment '完成次数'
	,task_id int comment 'task表主键'
	,user_id int comment '用户id'
	,status tinyint comment '1任务待完成/2任务已完成'
	,next_datetime datetime comment '任务下次开启时间，开启后status改为待完成'
	,des nvarchar(20) comment '说明'
)
create table task_user_history(
    id int primary key auto_increment
	,name nvarchar(20) comment ''
	,task_user_id int comment 'task_user主键'
	,begin_time datetime comment '任务开始时间'
	,end_time datetime comment '任务结束时间'
	,des nvarchar(20) comment '说明'
)

