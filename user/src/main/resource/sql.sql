create table user(
	id int primary key auto_increment
	,code nvarchar(20) comment '用户code'
	,name nvarchar(20) comment '用户姓名'
	,nickname nvarchar(20) comment '用户昵称'
	,ps nvarchar(50) comment '加密后的密码'
	,original_ps nvarchar(20) comment '原密码'
	,email nvarchar(50) comment '用户邮箱'
	,mobile nvarchar(20) comment '用户手机'
	,id_card nvarchar(50) comment '身份证号'
	,gender int comment '性别'
	,des nvarchar(200) comment '描述'
)COMMENT='用户信息';