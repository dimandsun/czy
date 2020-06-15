
create table user(
	id int primary key auto_increment
	,code nvarchar(20) comment '用户code'
	,name nvarchar(20) comment '用户名称'
	,ps nvarchar(20) comment '加密后的密码'
	,original_ps nvarchar(50) comment '原密码'
	,email nvarchar(50) comment '用户邮箱'
	,mobile nvarchar(20) comment '用户手机'
	,gender int comment '性别'
	,id_card nvarchar(50) comment '身份证'
	,des nvarchar(200) comment '描述'
)COMMENT='客户信息';

create table car(
    id int primary key auto_increment
    ,car_type_id int '车辆类别'
	,code nvarchar(20) comment '编号'
	,plate_number nvarchar(20) comment '车牌号'
	,brand nvarchar(200) comment '品牌'
	,color nvarchar(20) comment '颜色'
	,sit_count int comment '座位数'
	,rental_price_day decimal(10,4) comment '日租价格'
	,rental_price_month decimal(10,4) comment '月租价格'
	,rental_price_more_km decimal(10,4) comment '日租超公里价格'
	,user_id int comment '租赁者'
	,purchase_date date comment '购入日期'
	,des nvarchar(200) comment '描述'
)comment='车辆信息';

create table car_type(
  id int primary key auto_increment
	,code nvarchar(20) comment '编号'
	,name nvarchar(20) comment '名称'
	,des nvarchar(200) comment '描述'
)comment='车辆类别信息-分类号，分类名';

create table lease_order(
  id int primary key auto_increment
	,code nvarchar(20) comment '订单编号'
	,name nvarchar(20) comment '订单名称'
	,car_id int comment '汽车id'
	,create_date datetime comment '订单创建时间'
	,create_id int comment '订单创建者'
	,update_date datetime comment '订单修改时间'
	,update_id int comment '订单修改者'
	,pay_date datetime comment '订单支付时间'
	,pay_id int comment '订单支付者'
	,pay_cancel_date datetime comment '订单取消支付时间'
	,pay_cancel_id int comment '订单取消支付者'
	,lease_begin_date datetime comment '租赁开始时间'
    ,lease_end_date datetime comment '租赁开始时间'
	,pay_money decimal(10,4) comment '订单金额，不包括逾期金额'
	,overdue_money decimal(10,4) comment '逾期金额'
	,pay_total_money decimal(10,4) comment '订单总金额，包括逾期金额'
	,order_state int comment '创建、租赁中、订单状态-待支付、已支付、取消支付、完成、中止、作废、逾期未归还'
	,des nvarchar(200) comment '描述'

)comment='租赁订单';