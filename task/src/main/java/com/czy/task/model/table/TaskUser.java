package com.czy.task.model.table;
import com.czy.core.annotation.db.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
/**
 * @author task
 * @date 2020-07-09
 *  
 */
@Table("task_user")
public class TaskUser {
	/**/
	private Integer id;
	/**/
	private String name;
	/*完成次数*/
	private Integer num;
	/*task表主键*/
	@JsonProperty("task_id")
	private Integer taskId;
	/*用户id*/
	@JsonProperty("user_id")
	private Integer userId;
	/*1任务待完成/2任务已完成*/
	private Byte status;
	/*任务下次开启时间，开启后status改为待完成*/
	@JsonProperty("next_datetime")
	private Date nextDatetime;
	/*说明)create*/
	private String des;
	/*task_user主键*/
	@JsonProperty("task_user_id")
	private Integer taskUserId;
	/*任务开始时间*/
	@JsonProperty("begin_time")
	private Date beginTime;
	/*任务结束时间*/
	@JsonProperty("end_time")
	private Date endTime;

	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		 this.id=id;
	}
	public Integer getNum(){
		return num;
	}
	public void setNum(Integer num){
		 this.num=num;
	}
	public Integer getTaskId(){
		return taskId;
	}
	public void setTaskId(Integer taskId){
		 this.taskId=taskId;
	}
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		 this.userId=userId;
	}
	public Byte getStatus(){
		return status;
	}
	public void setStatus(Byte status){
		 this.status=status;
	}
	public Date getNextDatetime(){
		return nextDatetime;
	}
	public void setNextDatetime(Date nextDatetime){
		 this.nextDatetime=nextDatetime;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		 this.des=des;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		 this.name=name;
	}
	public Integer getTaskUserId(){
		return taskUserId;
	}
	public void setTaskUserId(Integer taskUserId){
		 this.taskUserId=taskUserId;
	}
	public Date getBeginTime(){
		return beginTime;
	}
	public void setBeginTime(Date beginTime){
		 this.beginTime=beginTime;
	}
	public Date getEndTime(){
		return endTime;
	}
	public void setEndTime(Date endTime){
		 this.endTime=endTime;
	}
}