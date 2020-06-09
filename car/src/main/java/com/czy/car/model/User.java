package com.czy.car.model;
import com.czy.core.annotation.db.Table;
import com.czy.util.SecretUtil;
import com.czy.util.StringUtil;

/**
 * @author chenzy
 * @since 2020-06-09
 * @description 客户信息
 */
@Table("user")
public class User {
	/**/
	private Integer id;
	/*用户code*/
	private String code;
	/*用户名称*/
	private String name;
	/*加密后的密码*/
	private String ps;
	/*原密码*/
	private String originalPS;
	/*用户邮箱*/
	private String email;
	/*用户手机*/
	private String mobile;
	/*性别*/
	private Integer gender;
	/*身份证*/
	private String idCard;
	/*描述*/
	private String des;
	/*原密码加密*/
	public void setPS() {
		if (StringUtil.isBlank(originalPS)){
			return;
		}
		setPs(SecretUtil.md5(originalPS));
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
	public String getOriginalPS() {
		return originalPS;
	}

	public void setOriginalPS(String originalPS) {
		this.originalPS = originalPS;
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