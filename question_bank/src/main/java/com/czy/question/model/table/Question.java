package com.czy.question.model.table;
import com.czy.core.annotation.db.Table;
import com.czy.question.model.enums.QuestionTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author chenzy
 * @date 2020-06-22
 *  题目，题目有多个选项(除判断对错)时关联表option
 */
@Table("question")
public class Question {
	/**/
	private Integer id;
	/**/
	private String code;
	/**/
	private String name;
	/*所属科目*/
	@JsonProperty("subject_id")
	private Integer subjectId;
	/*题干*/
	private String content;

	/*题型-填空、判断、单选、多选、不定项选择、阅读理解、完型填空、翻译、名词解释等*/
	@JsonProperty("question_type_id")
	private QuestionTypeEnum questionType;

	/*答案：作文在另一张表composition*/
	@JsonProperty("answer_content")
	private String answerContent;
	/*说明*/
	private String des;

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
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
	public Integer getSubjectId(){
		return subjectId;
	}
	public void setSubjectId(Integer subjectId){
		 this.subjectId=subjectId;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		 this.content=content;
	}
	public QuestionTypeEnum getQuestionType() {
		return questionType;
	}
	public void setQuestionType(QuestionTypeEnum questionType) {
		this.questionType = questionType;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}