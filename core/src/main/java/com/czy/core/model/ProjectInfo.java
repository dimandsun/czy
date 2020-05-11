package com.czy.core.model;

import com.czy.core.enums.ActiveEnum;
import com.czy.util.FileUtil;

import java.util.Map;

/**
 * @author chenzy
 * @since 2020-04-07
 * @description 项目信息，在项目启动时实例化
 */
public class ProjectInfo {
    private static ProjectInfo instance=new ProjectInfo();
    public static ProjectInfo getInstance(){
        return instance;
    }
    protected ProjectInfo(){
        String proFileName = "application.yml";
        Map<String, Map<String, Object>> proMap = FileUtil.readConfigFileByYML(proFileName);
        if (proMap!=null){
            String active = proMap.get("profiles").get("active").toString();
            setActive(ActiveEnum.getEnum(active));
        }
    }
    private String projectName;
    private ActiveEnum active;

    public ActiveEnum getActive() {
        return active;
    }

    public void setActive(ActiveEnum active) {
        this.active = active;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
