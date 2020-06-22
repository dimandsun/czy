package com.czy.car_server.model.table;
import com.czy.core.annotation.db.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author chenzy
 * @since 2020-06-17
 *  车辆类别信息-分类号，分类名
 */
@Table("car_type")
public class CarType {
	/**/
	@JsonProperty("id")
	private Integer id;
	/*编号*/
	@JsonProperty("code")
	private String code;
	/*名称*/
	@JsonProperty("name")
	private String name;
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
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}