package com.czy.question.model.table;
import com.czy.core.annotation.db.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author chenzy
 * @date 2020-08-23
 *  作文表，暂时不用
 */
@Table("composition")
public class Composition {
	/**/
	private Integer id;
	/**/
	private String code;
	/**/
	private String name;
	/*英语作文、语文作文*/
	@JsonProperty("composition_type")
	private Integer compositionType;
	/*作文题干*/
	@JsonProperty("question_id")
	private Integer questionId;
	/*作文内容*/
	private String content;
	/*内容过长时存储为文件*/
	@JsonProperty("file_path")
	private String filePath;
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
	public Integer getCompositionType(){
		return compositionType;
	}
	public void setCompositionType(Integer compositionType){
		 this.compositionType=compositionType;
	}
	public Integer getQuestionId(){
		return questionId;
	}
	public void setQuestionId(Integer questionId){
		 this.questionId=questionId;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		 this.content=content;
	}
	public String getFilePath(){
		return filePath;
	}
	public void setFilePath(String filePath){
		 this.filePath=filePath;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}