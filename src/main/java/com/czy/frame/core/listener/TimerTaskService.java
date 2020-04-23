package com.czy.frame.core.listener;

import java.util.TimerTask;

/**
 * @author czy
 * @date 2019/7/8
 * @description
 */
public class TimerTaskService extends TimerTask {
    @Override
    public void run() {
        //要执行的任务逻辑写在这里
        test();
    }
    public void test(){
        /*long startTime = System.currentTimeMillis();
        DataSourceEnum dsEnum=DataSourceEnum.SQLSERVER;
        String sql = "select sp.Card_No  emp_no,sp.Person_Name emp_name,kd.Reason remark" +
                ",kd.ID_KEY kq_id,kd.Worktime_Date rec_date" +
                ",kd.Brush_Data rec_times from ST_Person sp " +
                "INNER JOIN KQ_Daily kd on sp.Person_ID=kd.Person_ID" +
                " WHERE isnull(kd.Brush_Data,'')!=''";
        DBCPUtil.setConn(dsEnum);
        System.out.println(startTime+"执行sql:["+sql+"]");
        List<Mapping<String,Object>> kqmaps = DBCPUtil.getListMap(sql);
        List<Mapping<String,Object>> result = new ArrayList<>();
        Mapping<String,Object> tempMap = null;
        for (Mapping<String,Object> kqmap: kqmaps) {
            String rec_times = StringUtil.trim(kqmap.get("rec_times"));
            if (rec_times.length()<=1){
                System.out.println("未打卡");
                continue;
            }
            String[] rec_timess = rec_times.split(",");
            for (String rec_time: rec_timess) {
                tempMap=new HashMap<>();
                tempMap.putAll(kqmap);
                tempMap.put("rec_times",rec_time);
                result.add(tempMap);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(""+endTime);
        System.out.println(endTime-startTime);*/
    }
}
