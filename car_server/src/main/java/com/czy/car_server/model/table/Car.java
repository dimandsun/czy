package com.czy.car_server.model.table;
import com.czy.core.annotation.db.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author chenzy
 * @since 2020-06-17
 * @description 车辆信息
 */
@Table("car")
public class Car {
	/**/
	@JsonProperty("id")
	private Integer id;
	/*车辆类别*/
	@JsonProperty("car_type_id")
	private Integer carTypeId;
	/*编号*/
	@JsonProperty("code")
	private String code;
	/*车牌号*/
	@JsonProperty("plate_number")
	private String plateNumber;
	/*品牌*/
	@JsonProperty("brand")
	private String brand;
	/*颜色*/
	@JsonProperty("color")
	private String color;
	/*座位数*/
	@JsonProperty("sit_count")
	private Integer sitCount;
	/*日租价格*/
	@JsonProperty("rental_price_day")
	private BigDecimal rentalPriceDay;
	/*月租价格*/
	@JsonProperty("rental_price_month")
	private BigDecimal rentalPriceMonth;
	/*日租超公里价格*/
	@JsonProperty("rental_price_more_km")
	private BigDecimal rentalPriceMoreKm;
	/*租赁者*/
	@JsonProperty("user_id")
	private Integer userId;
	/*购入日期*/
	@JsonProperty("purchase_date")
	private Date purchaseDate;
	/*描述*/
	@JsonProperty("des")
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
	public BigDecimal getRentalPriceDay(){
		return rentalPriceDay;
	}
	public void setRentalPriceDay(BigDecimal rentalPriceDay){
		 this.rentalPriceDay=rentalPriceDay;
	}
	public BigDecimal getRentalPriceMonth(){
		return rentalPriceMonth;
	}
	public void setRentalPriceMonth(BigDecimal rentalPriceMonth){
		 this.rentalPriceMonth=rentalPriceMonth;
	}
	public BigDecimal getRentalPriceMoreKm(){
		return rentalPriceMoreKm;
	}
	public void setRentalPriceMoreKm(BigDecimal rentalPriceMoreKm){
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