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

create table word(
  id int primary key auto_increment
	,code nvarchar(20) comment '单词'
	,name nvarchar(20) comment '中文'
	,des nvarchar(200) comment '描述'
	,type int comment '单词类型：动词、名词、形容词、副词'
	,tense int comment '时态：十六种'
)comment='单词';



create table user(
	id int primary key auto_increment
	,code nvarchar(20) comment '用户code'
	,name nvarchar(20) comment '用户名称'
	,ps nvarchar(20) comment '加密后的密码'
	,original_ps nvarchar(50) comment '原密码'
	,email nvarchar(50) comment '用户邮箱'
	,mobile nvarchar(20) comment '用户手机'
);
create table order(
	id int primary key auto_increment
	,code nvarchar(20) comment '订单编号'
	,name nvarchar(200) comment '订单名称'
	,des nvarchar(200) comment '订单描述'
	,type TINYINT comment '订单类型'
	,state tinyint comment '订单状态'
	,moeny nvarchar(200) comment '订单金额'
	,create_time date comment '订单创建时间'
)COMMENT='订单';


create table city(
  id int primary key auto_increment
	,code nvarchar(20) comment '城市编号'
	,name nvarchar(20) comment '城市名称'
	,des nvarchar(200) comment '城市描述'
  ,targe_type tinyint comment '城市目标类型：用于生成不同的任务。普通，旅游，工业，商业，资源，高科技'
  ,money int comment '城市账面金额，为0则城市倒闭'
  ,create_time date comment '城市开始时间'
  ,total_time nvarchar(20) comment '在此城市游戏总时长'
)comment='城市';

create table building(
  id int primary key auto_increment
	,code nvarchar(20) comment '建筑编号'
	,name nvarchar(20) comment '建筑名称'
	,des nvarchar(200) comment '建筑描述'
	,type tinyint comment '建筑类型：居住、商业、工厂、服务、公共建筑(市政府)'
	,hour_cost int comment '每小时成本'
	,spend int comment '建造花费'
	,max_citizen_num commnet '最大居民数'
	,max_worker_num comment '最大工作人数'
	,remove_money int comment '拆迁补偿'
	,levle int commnet '等级：最高三级'
	,next_level_money int comment '升级费用'
)comment='建筑';

create table citizen(
  id int primary key auto_increment
	,code nvarchar(20) comment '编号'
	,name nvarchar(20) comment '名称'
	,gender tinyint comment '性别，0男 1女'
	,des nvarchar(200) comment '描述'
	,age int commnet '年龄'
	,address int comment '居住地址，关联建筑id'
	,work_address int comment '工作地址，关联建筑id，学习期为学校，学龄前为家'
	,skill int comment '技能'
  ,role int comment '角色：学龄前，幼儿园，小学，初中，高中，大专，本科，硕士，博士，博士后，工作者'
  ,hour_wage int comment '时薪'
  ,moeny int comment '现金'
  ,role_time_type comment '角色扮演时间'
  ,work_type int comment '工作类型：'
)comment='市民';


create table (
  id int primary key auto_increment
	,code nvarchar(20) comment '编号'
	,name nvarchar(20) comment '名称'
	,des nvarchar(200) comment '描述'
)comment='';


create table (
  id int primary key auto_increment
	,code nvarchar(20) comment '编号'
	,name nvarchar(20) comment '名称'
	,des nvarchar(200) comment '描述'
)comment='';





create table (
  id int primary key auto_increment
	,code nvarchar(20) comment '编号'
	,name nvarchar(20) comment '名称'
	,des nvarchar(200) comment '描述'
)comment='';

create table response_level(
	id int primary key auto_increment
	,code nvarchar(10) comment '响应级别'
);