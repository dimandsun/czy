package com.czy.car.model;
import com.czy.core.annotation.db.Table;
/**
 * @author chenzy
 * @since 2020-06-09
 * @description 车辆类别信息-分类号，分类名
 */
@Table("car_type")
public class CarType {
	/**/
	private Integer id;
	/*编号*/
	private String code;
	/*名称*/
	private String name;
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
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}