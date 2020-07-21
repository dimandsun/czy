create table school(
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,des nvarchar(20) comment '说明'
)COMMENT='学校';
create table subject(
	id int primary key auto_increment
	,code nvarchar(20) comment ''
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
create table [option](
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,option_type tinyint comment '选项类型-英文字母、阿拉伯数字、判断对错、文本、其他'
	,content nvarchar(200) comment ''
	,question_id int comment ''
	,answer_status tinyint comment '不是答案/是答案/有可能是答案'
	,des nvarchar(20) comment '说明'
)COMMENT='题目选项';
create table question(
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,subject_id int comment '所属科目'
	,question_group_stem int comment '题干的题干，如阅读理解关联的是阅读理解的文章'
	,content nvarchar(20) comment '题干'
	,question_type_id int comment '题型'
	,answer_content nvarchar(500) comment '答案：作文在另一张表composition'
	,des nvarchar(20) comment '说明'
)COMMENT='题目，题目有多个选项(除判断对错)时关联表option';

create table composition(
	id int primary key auto_increment
	,code nvarchar(20) comment ''
	,name nvarchar(20) comment ''
	,composition_type tinyint comment '英语作文、语文作文'
	,question_id int comment '作文题干'
	,content nvarchar(50) comment '作文内容'
	,file_path nvarchar(100) comment '内容过长时存储为文件'
	,des nvarchar(20) comment '说明'
)COMMENT='作文';



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