create table user(
	id int primary key auto_increment
	,code nvarchar(20) comment '用户code'
	,name nvarchar(20) comment '用户名称'
	,ps nvarchar(20) comment '加密后的密码'
	,original_ps nvarchar(50) comment '原密码'
	,email nvarchar(50) comment '用户邮箱'
	,mobile nvarchar(20) comment '用户手机'
);






create table doc_mode(
	id int primary key auto_increment
	,name nvarchar(200) comment '文档模块名称'
	,parent_id int comment '父模块'
	,level int comment '级别'
	,server_uri nvarchar(200) comment '服务器地址.一级分类才有值，ip:端口'	
)COMMENT='文档模块';

create table doc(
	id int primary key auto_increment
	,name nvarchar(200) comment '文档名称'
	,des nvarchar(2000) comment '文档说明'
	,url nvarchar(1000) comment '请求地址'
	,route nvarchar(1000) comment '路由'
	,quest_example nvarchar(2000) comment '请求参数实例'
	,result_example1 nvarchar(2000) comment '返回数据实例1'
	,result_example2 nvarchar(2000) comment '返回数据实例2'
	,doc_type int comment '文档类型'
	,doc_mode_id int comment '文档模块id'
	,test_level int comment '测试等级'
	,notes nvarchar(2000) comment '文档其他信息'
	,file_name nvarchar(100) comment 'md文档名：xx.html'
)comment='文档';

create table doc_par(
	id int primary key auto_increment
	,doc_id int comment '文档id'
	,name nvarchar(200) comment '参数名称'
	,des nvarchar(500) comment '参数说明'
	,notes nvarchar(500) comment '备注'
	,data_type nvarchar(20) comment '参数数据类型：'
	,length int comment '参数长度'
	,is_must int comment '是否必填：0 否，1是'
	,par_type int comment '参数类型0 请求参数，1请求返回数据，2 数据库表字段'
)comment='文档参数';

create table doc_update_log(
	id int primary key auto_increment
	,version nvarchar(10) comment '记录版本，从1.0开始'
	,log_date date comment '修订日期 yyyy-MM-dd'
	,log_code nvarchar(20) comment '修订者code'
	,log_des nvarchar(1000) comment '修订描述'
)comment='接口文档修改记录';
create table doc_rule(
	id int primary key auto_increment
	,name nvarchar(20) comment '规则名称'
	,des nvarchar(2000) comment '规则描述'
)comment='文档规则';


create table response_code(
	id int primary key auto_increment
	,code nvarchar(10) comment '响应码'
	,name nvarchar(50) comment '响应码名称'
	,des nvarchar(2000) comment '响应码描述'
	,level_code nvarchar(20) comment '响应码级别'
)comment='响应码';

create table response_level(
	id int primary key auto_increment
	,code nvarchar(10) comment '响应级别'
);