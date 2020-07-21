package com.czy.question.model.table;
import com.czy.core.annotation.db.Table;
import com.czy.question.server.model.enums.OptionTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author chenzy
 * @date 2020-06-22
 *  题目选项
 */
@Table("option")
public class Option {
	/**/
	private Integer id;
	/**/
	private String code;
	/**/
	private String name;

	/*选项类型-英文字母、阿拉伯数字、判断对错、文本、其他*/
	@JsonProperty("option_type")
	private OptionTypeEnum optionType;

	/**/
	private String content;
	/**/
	@JsonProperty("question_id")
	private String questionId;
	/*不是答案/是答案/有可能是答案*/
	@JsonProperty("answer_status")
	private Byte answerStatus;
	/*说明*/
	private String des;

	public OptionTypeEnum getOptionType() {
		return optionType;
	}

	public void setOptionType(OptionTypeEnum optionType) {
		this.optionType = optionType;
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
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		 this.content=content;
	}
	public String getQuestionId(){
		return questionId;
	}
	public void setQuestionId(String questionId){
		 this.questionId=questionId;
	}
	public Byte getAnswerStatus(){
		return answerStatus;
	}
	public void setAnswerStatus(Byte answerStatus){
		 this.answerStatus=answerStatus;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}