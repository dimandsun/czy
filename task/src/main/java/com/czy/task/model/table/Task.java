package com.czy.task.model.table;
import com.czy.core.annotation.db.Table;
import com.czy.task.model.enums.FrequencyEnum;
import com.czy.task.model.enums.TaskTypeEnum;
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
	/*任务类型：1紧急重要/2紧急不重要/3不紧急重要/4不紧急不重要*/
	@JsonProperty("task_type")
	private TaskTypeEnum taskType;
	/*任务循环频率:1每日/2每周/3每月/4每年/5工作日/6节假日/7非循环单次任务/8非循环任意任务/9其他*/
	private FrequencyEnum frequency;
	/*提醒年.非循环任务时使用*/
	@JsonProperty("remind_year")
	private String remindYear;
	/*提醒月.从1开始，年度任务时使用*/
	@JsonProperty("remind_month")
	private String remindMonth;
	/*提醒日，每月的几号*/
	@JsonProperty("remind_day")
	private String remindDay;
	/*1-周一/2-周二/3-周三/4-周四/5-周五/6-周六/7-周日*/
	@JsonProperty("remind_week_day")
	private String remindWeekDay;
	/*提醒时间/只包含时间/时分/所有任务都使用*/
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
	public TaskTypeEnum getTaskType(){
		return taskType;
	}
	public void setTaskType(TaskTypeEnum taskType){
		 this.taskType=taskType;
	}
	public FrequencyEnum getFrequency(){
		return frequency;
	}
	public void setFrequency(FrequencyEnum frequency){
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