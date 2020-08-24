package com.czy.question.model.table;
import com.czy.core.annotation.db.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author chenzy
 * @date 2020-08-23
 *  题目表
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
	/*题干的题干，如阅读理解关联的是阅读理解的文章*/
	@JsonProperty("question_group_stem")
	private Integer questionGroupStem;
	/*题干*/
	private String content;
	/*题型：填空、判断、单选、多选、不定项选择、阅读理解、完型填空、翻译、名词解释*/
	@JsonProperty("question_type_id")
	private Integer questionTypeId;
	/*说明*/
	private String des;
	/*选项类型-英文字母、阿拉伯数字、判断对错、文本、其他*/
	@JsonProperty("option_type")
	private Integer optionType;
	/*选项1*/
	private String option1;
	/*选项2*/
	private String option2;
	/*选项3*/
	private String option3;
	/*选项4*/
	private String option4;
	/*选项5*/
	private String option5;
	/*选项6*/
	private String option6;
	/*选项7*/
	private String option7;
	/*选项8*/
	private String option8;
	/*答案*/
	private String answer;

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
	public Integer getQuestionGroupStem(){
		return questionGroupStem;
	}
	public void setQuestionGroupStem(Integer questionGroupStem){
		 this.questionGroupStem=questionGroupStem;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		 this.content=content;
	}
	public Integer getQuestionTypeId(){
		return questionTypeId;
	}
	public void setQuestionTypeId(Integer questionTypeId){
		 this.questionTypeId=questionTypeId;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
	public Integer getOptionType(){
		return optionType;
	}
	public void setOptionType(Integer optionType){
		 this.optionType=optionType;
	}
	public String getOption1(){
		return option1;
	}
	public void setOption1(String option1){
		 this.option1=option1;
	}
	public String getOption2(){
		return option2;
	}
	public void setOption2(String option2){
		 this.option2=option2;
	}
	public String getOption3(){
		return option3;
	}
	public void setOption3(String option3){
		 this.option3=option3;
	}
	public String getOption4(){
		return option4;
	}
	public void setOption4(String option4){
		 this.option4=option4;
	}
	public String getOption5(){
		return option5;
	}
	public void setOption5(String option5){
		 this.option5=option5;
	}
	public String getOption6(){
		return option6;
	}
	public void setOption6(String option6){
		 this.option6=option6;
	}
	public String getOption7(){
		return option7;
	}
	public void setOption7(String option7){
		 this.option7=option7;
	}
	public String getOption8(){
		return option8;
	}
	public void setOption8(String option8){
		 this.option8=option8;
	}
	public String getAnswer(){
		return answer;
	}
	public void setAnswer(String answer){
		 this.answer=answer;
	}
}