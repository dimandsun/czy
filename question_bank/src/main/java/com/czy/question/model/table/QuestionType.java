package com.czy.question.model.table;
import com.czy.core.annotation.db.Table;
/**
 * @author chenzy
 * @date 2020-08-23
 *  题型-填空、判断、单选、多选、不定项选择、阅读理解、完型填空、翻译、名词解释
 */
@Table("question_type")
public class QuestionType {
	/**/
	private Integer id;
	/**/
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