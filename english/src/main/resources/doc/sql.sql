create table word(
  id int primary key auto_increment
	,code nvarchar(20) comment '单词'
	,name nvarchar(20) comment '中文'
	,des nvarchar(200) comment '描述'
	,type int comment '单词类型：动词、名词、形容词、副词'
	,tense int comment '时态：十六种'

)comment='单词';


create table (
  id int primary key auto_increment
	,code nvarchar(20) comment '编号'
	,name nvarchar(20) comment '名称'
	,des nvarchar(200) comment '描述'
)comment='';



