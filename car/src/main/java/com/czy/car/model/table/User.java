package com.czy.car.model.table;
import com.czy.core.annotation.db.Table;
import com.czy.util.SecretUtil;
import com.czy.util.StringUtil;
import com.czy.util.json.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author chenzy
 * @since 2020-06-17
 * @description 客户信息
 */
@Table("user")
public class User {
	/**/
	@JsonProperty("id")
	private Integer id;
	/*用户code*/
	@JsonProperty("code")
	private String code;
	/*用户名称*/
	@JsonProperty("name")
	private String name;
	/*加密后的密码*/
	@JsonProperty("ps")
	private String ps;
	/*原密码*/
	@JsonProperty("original_ps")
	private String originalPs;
	/*用户邮箱*/
	@JsonProperty("email")
	private String email;
	/*用户手机*/
	@JsonProperty("mobile")
	private String mobile;
	/*性别*/
	@JsonProperty("gender")
	private Integer gender;
	/*身份证*/
	@JsonProperty("id_card")
	private String idCard;
	/*描述*/
	@JsonProperty("des")
	private String des;
	/*原密码加密*/
	public void setPs() {
		if (StringUtil.isBlank(originalPs)){
			return;
		}
		setPs(SecretUtil.md5(originalPs));
	}

	@Override
	public String toString() {
		return JsonUtil.model2Str(this);
	}

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
	public String getPs(){
		return ps;
	}
	public void setPs(String ps){
		 this.ps=ps;
	}
	public String getOriginalPs(){
		return originalPs;
	}
	public void setOriginalPs(String originalPs){
		 this.originalPs=originalPs;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		 this.email=email;
	}
	public String getMobile(){
		return mobile;
	}
	public void setMobile(String mobile){
		 this.mobile=mobile;
	}
	public Integer getGender(){
		return gender;
	}
	public void setGender(Integer gender){
		 this.gender=gender;
	}
	public String getIdCard(){
		return idCard;
	}
	public void setIdCard(String idCard){
		 this.idCard=idCard;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}