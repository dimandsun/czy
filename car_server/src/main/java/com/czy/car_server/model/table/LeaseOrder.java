package com.czy.car_server.model.table;
import com.czy.core.annotation.db.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author chenzy
 * @since 2020-06-17
 *  租赁订单
 */
@Table("lease_order")
public class LeaseOrder {
	/**/
	@JsonProperty("id")
	private Integer id;
	/*订单编号*/
	@JsonProperty("code")
	private String code;
	/*订单名称*/
	@JsonProperty("name")
	private String name;
	/*汽车id*/
	@JsonProperty("car_id")
	private Integer carId;
	/*订单创建时间*/
	@JsonProperty("create_date")
	private Date createDate;
	/*订单创建者*/
	@JsonProperty("create_id")
	private Integer createId;
	/*订单修改时间*/
	@JsonProperty("update_date")
	private Date updateDate;
	/*订单修改者*/
	@JsonProperty("update_id")
	private Integer updateId;
	/*订单支付时间*/
	@JsonProperty("pay_date")
	private Date payDate;
	/*订单支付者*/
	@JsonProperty("pay_id")
	private Integer payId;
	/*订单取消支付时间*/
	@JsonProperty("pay_cancel_date")
	private Date payCancelDate;
	/*订单取消支付者*/
	@JsonProperty("pay_cancel_id")
	private Integer payCancelId;
	/*租赁开始时间*/
	@JsonProperty("lease_begin_date")
	private Date leaseBeginDate;
	/*租赁开始时间*/
	@JsonProperty("lease_end_date")
	private Date leaseEndDate;
	/*订单金额，不包括逾期金额*/
	@JsonProperty("pay_money")
	private BigDecimal payMoney;
	/*逾期金额*/
	@JsonProperty("overdue_money")
	private BigDecimal overdueMoney;
	/*订单总金额，包括逾期金额*/
	@JsonProperty("pay_total_money")
	private BigDecimal payTotalMoney;
	/*创建、租赁中、订单状态-待支付、已支付、取消支付、完成、中止、作废、逾期未归还*/
	@JsonProperty("order_state")
	private Integer orderState;
	/*描述*/
	@JsonProperty("des")
	private String des;

	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		 this.id=id;
	}
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		 this.code=code;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		 this.name=name;
	}
	public Integer getCarId(){
		return carId;
	}
	public void setCarId(Integer carId){
		 this.carId=carId;
	}
	public Date getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Date createDate){
		 this.createDate=createDate;
	}
	public Integer getCreateId(){
		return createId;
	}
	public void setCreateId(Integer createId){
		 this.createId=createId;
	}
	public Date getUpdateDate(){
		return updateDate;
	}
	public void setUpdateDate(Date updateDate){
		 this.updateDate=updateDate;
	}
	public Integer getUpdateId(){
		return updateId;
	}
	public void setUpdateId(Integer updateId){
		 this.updateId=updateId;
	}
	public Date getPayDate(){
		return payDate;
	}
	public void setPayDate(Date payDate){
		 this.payDate=payDate;
	}
	public Integer getPayId(){
		return payId;
	}
	public void setPayId(Integer payId){
		 this.payId=payId;
	}
	public Date getPayCancelDate(){
		return payCancelDate;
	}
	public void setPayCancelDate(Date payCancelDate){
		 this.payCancelDate=payCancelDate;
	}
	public Integer getPayCancelId(){
		return payCancelId;
	}
	public void setPayCancelId(Integer payCancelId){
		 this.payCancelId=payCancelId;
	}
	public Date getLeaseBeginDate(){
		return leaseBeginDate;
	}
	public void setLeaseBeginDate(Date leaseBeginDate){
		 this.leaseBeginDate=leaseBeginDate;
	}
	public Date getLeaseEndDate(){
		return leaseEndDate;
	}
	public void setLeaseEndDate(Date leaseEndDate){
		 this.leaseEndDate=leaseEndDate;
	}
	public BigDecimal getPayMoney(){
		return payMoney;
	}
	public void setPayMoney(BigDecimal payMoney){
		 this.payMoney=payMoney;
	}
	public BigDecimal getOverdueMoney(){
		return overdueMoney;
	}
	public void setOverdueMoney(BigDecimal overdueMoney){
		 this.overdueMoney=overdueMoney;
	}
	public BigDecimal getPayTotalMoney(){
		return payTotalMoney;
	}
	public void setPayTotalMoney(BigDecimal payTotalMoney){
		 this.payTotalMoney=payTotalMoney;
	}
	public Integer getOrderState(){
		return orderState;
	}
	public void setOrderState(Integer orderState){
		 this.orderState=orderState;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}