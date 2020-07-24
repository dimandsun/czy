package com.czy.core.model;

import com.czy.core.enums.ActiveEnum;
import com.czy.util.model.Par;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;
import com.czy.util.io.FileUtil;

import java.util.Map;
import java.util.Optional;

/**
 * @author chenzy
 *  项目信息，在项目启动时实例化
 * @since 2020-04-07
 */
public class ProjectInfo {
    private static ProjectInfo instance=new ProjectInfo();
    public static ProjectInfo getInstance(){
        return instance;
    }
    protected ProjectInfo(){

    }
    private String projectName;
    private String projectGroupId;
    private String moduleDir;
    /**/
    private ActiveEnum active;

    public String getModuleDir() {
        return moduleDir;
    }

    public void setModuleDir(String moduleDir) {
        this.moduleDir = moduleDir;
    }

    public ActiveEnum getActive() {
        return active==null?ActiveEnum.Default:active;
    }

    public String getProjectGroupId() {
        return projectGroupId;
    }

    public void setProjectGroupId(String projectGroupId) {
        this.projectGroupId = projectGroupId;
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
