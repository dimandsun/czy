package com.czy.core.model;

import com.czy.core.enums.ActiveEnum;
import com.czy.util.BeanUtil;
import com.czy.util.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author chenzy
 *  项目信息，在项目启动时实例化
 * @since 2020-04-07
 */
public class ProjectInfo {
    private static ProjectInfo instance=new ProjectInfo();
    private File settingFile;

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

    public static void init(File resourceFile) {
        FileUtil.readYML(resourceFile).ifPresent(proMap->{
            Map profiles= (Map) proMap.get("profiles");
            var active=ActiveEnum.getEnum(profiles.get("active").toString());
            profiles.put("active",active);
            BeanUtil.map2Model(profiles,instance);
            String path = null;
            try {
                path = resourceFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            var realPath = path.substring(0,path.lastIndexOf("."))+"-"+active+path.substring(path.lastIndexOf("."));
            instance.setSettingFile(new File(realPath));
        });
    }

    public String getModuleDir() {
        return moduleDir;
    }

    public void setModuleDir(String moduleDir) {
        this.moduleDir = moduleDir;
    }

    public ActiveEnum getActive() {
        return active==null?ActiveEnum.other:active;
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


    public File getSettingFile() {
        return settingFile;
    }

    public void setSettingFile(File settingFile) {
        this.settingFile = settingFile;
    }
}
