package com.czy.question.model.table;
import com.czy.core.annotation.db.Table;
/**
 * @author chenzy
 * @date 2020-06-22
 *  多个题目的共同题干，如英语的阅读理解
 */
@Table("question_group_stem")
public class QuestionGroupStem {
	/**/
	private Integer id;
	/**/
	private String code;
	/**/
	private String name;
	/*题干*/
	private String content;
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
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		 this.content=content;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}