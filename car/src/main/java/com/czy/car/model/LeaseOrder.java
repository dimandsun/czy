package com.czy.car.model;
import com.czy.core.annotation.db.Table;
import java.util.Date;
/**
 * @author chenzy
 * @since 2020-06-09
 * @description 租赁订单
 */
@Table("lease_order")
public class LeaseOrder {
	/**/
	private Integer id;
	/*订单编号*/
	private String code;
	/*订单名称*/
	private String name;
	/*汽车id*/
	private Integer carId;
	/*订单创建时间*/
	private Date createDate;
	/*订单创建者*/
	private Integer createId;
	/*订单修改时间*/
	private Date updateDate;
	/*订单修改者*/
	private Integer updateId;
	/*订单支付时间*/
	private Date payDate;
	/*订单支付者*/
	private Integer payId;
	/*订单取消支付时间*/
	private Date payCancelDate;
	/*订单取消支付者*/
	private Integer payCancelId;
	/*租赁开始时间*/
	private Date leaseBeginDate;
	/*租赁开始时间*/
	private Date leaseEndDate;
	/*订单金额，不包括逾期金额*/
	private Object payMoney;
	/*逾期金额*/
	private Object overdueMoney;
	/*订单总金额，包括逾期金额*/
	private Object payTotalMoney;
	/*创建、租赁中、订单状态-待支付、已支付、取消支付、完成、中止、作废、逾期未归还*/
	private Integer orderState;
	/*描述*/
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
	public Object getPayMoney(){
		return payMoney;
	}
	public void setPayMoney(Object payMoney){
		 this.payMoney=payMoney;
	}
	public Object getOverdueMoney(){
		return overdueMoney;
	}
	public void setOverdueMoney(Object overdueMoney){
		 this.overdueMoney=overdueMoney;
	}
	public Object getPayTotalMoney(){
		return payTotalMoney;
	}
	public void setPayTotalMoney(Object payTotalMoney){
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