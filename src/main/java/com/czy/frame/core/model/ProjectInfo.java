package com.czy.frame.core.model;

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

    }
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
