package com.czy.task.model.table;
import com.czy.core.annotation.db.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author task
 * @date 2020-07-09
 *  任务分组
 */
@Table("task_group")
public class TaskGroup {
	/**/
	private Integer id;
	/*父id*/
	@JsonProperty("parent_id")
	private Integer parentId;
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
	public Integer getParentId(){
		return parentId;
	}
	public void setParentId(Integer parentId){
		 this.parentId=parentId;
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