package com.czy.car.model;
import com.czy.core.annotation.db.Table;
import java.util.Date;
/**
 * @author chenzy
 * @since 2020-06-09
 * @description 车辆信息
 */
@Table("car")
public class Car {
	/**/
	private Integer id;
	/**/
	private Integer carTypeId;
	/*编号*/
	private String code;
	/*车牌号*/
	private String plateNumber;
	/*品牌*/
	private String brand;
	/*颜色*/
	private String color;
	/*座位数*/
	private Integer sitCount;
	/*日租价格*/
	private Object rentalPriceDay;
	/*月租价格*/
	private Object rentalPriceMonth;
	/*日租超公里价格*/
	private Object rentalPriceMoreKm;
	/*租赁者*/
	private Integer userId;
	/*购入日期*/
	private Date purchaseDate;
	/*描述*/
	private String des;

	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		 this.id=id;
	}
	public Integer getCarTypeId(){
		return carTypeId;
	}
	public void setCarTypeId(Integer carTypeId){
		 this.carTypeId=carTypeId;
	}
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		 this.code=code;
	}
	public String getPlateNumber(){
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber){
		 this.plateNumber=plateNumber;
	}
	public String getBrand(){
		return brand;
	}
	public void setBrand(String brand){
		 this.brand=brand;
	}
	public String getColor(){
		return color;
	}
	public void setColor(String color){
		 this.color=color;
	}
	public Integer getSitCount(){
		return sitCount;
	}
	public void setSitCount(Integer sitCount){
		 this.sitCount=sitCount;
	}
	public Object getRentalPriceDay(){
		return rentalPriceDay;
	}
	public void setRentalPriceDay(Object rentalPriceDay){
		 this.rentalPriceDay=rentalPriceDay;
	}
	public Object getRentalPriceMonth(){
		return rentalPriceMonth;
	}
	public void setRentalPriceMonth(Object rentalPriceMonth){
		 this.rentalPriceMonth=rentalPriceMonth;
	}
	public Object getRentalPriceMoreKm(){
		return rentalPriceMoreKm;
	}
	public void setRentalPriceMoreKm(Object rentalPriceMoreKm){
		 this.rentalPriceMoreKm=rentalPriceMoreKm;
	}
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		 this.userId=userId;
	}
	public Date getPurchaseDate(){
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate){
		 this.purchaseDate=purchaseDate;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}