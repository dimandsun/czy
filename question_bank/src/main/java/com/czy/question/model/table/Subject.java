package com.czy.question.model.table;
import com.czy.core.annotation.db.Table;
/**
 * @author chenzy
 * @date 2020-08-23
 *  科目
 */
@Table("subject")
public class Subject {
	/**/
	private Integer id;
	/*科目代码，使用全国统一专业代码*/
	private String code;
	/**/
	private String name;
	/*说明*/
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