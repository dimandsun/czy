package com.czy.task.model.table;
import com.czy.core.annotation.db.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
/**
 * @author task
 * @date 2020-07-09
 *  任务
 */
@Table("task")
public class Task {
	/**/
	private Integer id;
	/**/
	private String code;
	/**/
	private String name;
	/*任务类型：1紧急重要*/
	@JsonProperty("task_type")
	private Byte taskType;
	/*任务循环频率:*/
	private Byte frequency;
	/*提醒年*/
	@JsonProperty("remind_year")
	private String remindYear;
	/*提醒月*/
	@JsonProperty("remind_month")
	private String remindMonth;
	/*提醒日*/
	@JsonProperty("remind_day")
	private String remindDay;
	/*1-周一*/
	@JsonProperty("remind_week_day")
	private String remindWeekDay;
	/*提醒时间*/
	@JsonProperty("remind_time")
	private String remindTime;
	/*任务创建时间*/
	@JsonProperty("create_time")
	private Date createTime;
	/*任务创建者*/
	@JsonProperty("create_code")
	private String createCode;
	/*任务分组：task_group表主键*/
	@JsonProperty("task_group_id")
	private Integer taskGroupId;
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
	public Byte getTaskType(){
		return taskType;
	}
	public void setTaskType(Byte taskType){
		 this.taskType=taskType;
	}
	public Byte getFrequency(){
		return frequency;
	}
	public void setFrequency(Byte frequency){
		 this.frequency=frequency;
	}
	public String getRemindYear(){
		return remindYear;
	}
	public void setRemindYear(String remindYear){
		 this.remindYear=remindYear;
	}
	public String getRemindMonth(){
		return remindMonth;
	}
	public void setRemindMonth(String remindMonth){
		 this.remindMonth=remindMonth;
	}
	public String getRemindDay(){
		return remindDay;
	}
	public void setRemindDay(String remindDay){
		 this.remindDay=remindDay;
	}
	public String getRemindWeekDay(){
		return remindWeekDay;
	}
	public void setRemindWeekDay(String remindWeekDay){
		 this.remindWeekDay=remindWeekDay;
	}
	public String getRemindTime(){
		return remindTime;
	}
	public void setRemindTime(String remindTime){
		 this.remindTime=remindTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		 this.createTime=createTime;
	}
	public String getCreateCode(){
		return createCode;
	}
	public void setCreateCode(String createCode){
		 this.createCode=createCode;
	}
	public Integer getTaskGroupId(){
		return taskGroupId;
	}
	public void setTaskGroupId(Integer taskGroupId){
		 this.taskGroupId=taskGroupId;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
}