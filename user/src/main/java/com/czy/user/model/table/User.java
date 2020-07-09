package com.czy.user.model.table;
import com.czy.core.annotation.db.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author user
 * @date 2020-07-09
 *  用户信息
 */
@Table("user")
public class User {
	/**/
	private Integer id;
	/*用户code*/
	private String code;
	/*用户姓名*/
	private String name;
	/*用户昵称*/
	private String nickname;
	/*加密后的密码*/
	private String ps;
	/*原密码*/
	@JsonProperty("original_ps")
	private String originalPs;
	/*用户邮箱*/
	private String email;
	/*用户手机*/
	private String mobile;
	/*身份证号*/
	@JsonProperty("id_card")
	private String idCard;
	/*性别*/
	private Integer gender;
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
	public String getNickname(){
		return nickname;
	}
	public void setNickname(String nickname){
		 this.nickname=nickname;
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
	public String getIdCard(){
		return idCard;
	}
	public void setIdCard(String idCard){
		 this.idCard=idCard;
	}
	public Integer getGender(){
		return gender;
	}
	public void setGender(Integer gender){
		 this.gender=gender;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}