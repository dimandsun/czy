package com.czy.core.model;

import com.czy.core.CoreContainer;
import com.czy.core.db.config.DataSourceEnum;
import com.czy.core.db.config.DataSourceHolder;

/**
 * @author chenzy
 * @since 2020-04-07
 * @description 项目信息，在项目启动时实例化
 */
public class CoreProject {
    private static CoreProject instance=new CoreProject();
    public static CoreProject getInstance(){
        return instance;
    }
    protected CoreProject(){
    }
    public void init(){
        addProjectGroupId();
        CoreContainer.getInstance().initProject();
        createDataSourceHolder();
    }
    protected void createDataSourceHolder(){
        DataSourceHolder.createInstance(DataSourceEnum.class,DataSourceEnum.DEFAULT);
    }

    private final String projectName="czy_core";
    private final String groupId = "com.czy.core";

    public void addProjectGroupId(){
        var coreContainer = CoreContainer.getInstance();
        coreContainer.addProjectGroupId(groupId);
    }

    public String getGroupId() {
        return groupId;
    }
    public String getProjectName() {
        return projectName;
    }
}
