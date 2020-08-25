create table school(
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,des nvarchar(20) comment '说明'
)COMMENT='学校';
create table subject(
	id int primary key auto_increment
	,code nvarchar(20) comment '科目代码，使用全国统一专业代码'
	,name nvarchar(20) comment ''
	,des nvarchar(20) comment '说明'
)COMMENT='科目';
create table question_type(
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,des nvarchar(20) comment '说明'
)COMMENT='题型-填空、判断、单选、多选、不定项选择、阅读理解、完型填空、翻译、名词解释';
create table question_group_stem(
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,content nvarchar(2000) comment '题干'
	,des nvarchar(20) comment '说明'
)COMMENT='多个题目的共同题干，如英语的阅读理解';
create table question(
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,subject_id int comment '所属科目'
	,question_group_stem int comment '题干的题干，如阅读理解关联的是阅读理解的文章'
	,content nvarchar(20) comment '题干'
	,question_type int comment '题型：填空、判断、单选、多选、不定项选择、阅读理解、完型填空、翻译、名词解释'
	,des nvarchar(20) comment '说明'
	,option_type int comment '选项类型-英文字母、阿拉伯数字、判断对错、文本、其他'
	,option1 nvarchar(200) comment '选项1'
	,option2 nvarchar(200) comment '选项2'
	,option3 nvarchar(200) comment '选项3'
	,option4 nvarchar(200) comment '选项4'
	,option5 nvarchar(200) comment '选项5'
	,option6 nvarchar(200) comment '选项6'
	,option7 nvarchar(200) comment '选项7'
	,option8 nvarchar(200) comment '选项8'
	,answer nvarchar(500) comment '答案'
)COMMENT='题目表';
create table composition(
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,composition_type int comment '英语作文、语文作文'
	,question_id int comment '作文题干'
	,content nvarchar(50) comment '作文内容'
	,file_path nvarchar(100) comment '内容过长时存储为文件'
	,des nvarchar(20) comment '说明'
)COMMENT='作文表，暂时不用';



create table (
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	, nvarchar(20) comment ''
	, nvarchar(50) comment ''
	, nvarchar(50) comment ''
	,des nvarchar(20) comment '说明'
)COMMENT='';

create table (
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	, nvarchar(20) comment ''
	, nvarchar(50) comment ''
	, nvarchar(50) comment ''
	,des nvarchar(20) comment '说明'
)COMMENT='';

create table (
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	, nvarchar(20) comment ''
	, nvarchar(50) comment ''
	, nvarchar(50) comment ''
	,des nvarchar(20) comment '说明'
)COMMENT='';

create table (
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	, nvarchar(20) comment ''
	, nvarchar(50) comment ''
	, nvarchar(50) comment ''
	,des nvarchar(20) comment '说明'
)COMMENT='';