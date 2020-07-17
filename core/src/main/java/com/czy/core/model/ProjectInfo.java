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
    public ProjectInfo() {
    }
    protected Optional<Map<String, Object>> initPro(String moduleDir) {
        Optional<StringMap<Map<String, Object>>> optional =FileUtil.readConfigFileByYML(FileUtil.getResourceFile(moduleDir, "application.yml"));
        Par<Map<String, Object>> result = new Par<>();
        optional.ifPresent(proMap->{
            var profileMap= proMap.get("profiles");
            String active = StringUtil.getStr(profileMap.get("active"), "dev");
            String groupId = StringUtil.getStr(profileMap.get("groupId"), "com.czy.core");
            ProjectInfo.this.moduleDir = StringUtil.getStr(profileMap.get("moduleDir"), moduleDir);
            setActive(ActiveEnum.getEnum(active));
            setGroupId(groupId);
            setModuleDir(moduleDir);
            result.set(profileMap);
        });
        return Optional.ofNullable(result.get());
    }
    public ProjectInfo init(String moduleDir){
        initPro(moduleDir);
        return this;
    }
    private ActiveEnum active;
    private String groupId;
    private String moduleDir;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getModuleDir() {
        return moduleDir;
    }

    public void setModuleDir(String moduleDir) {
        this.moduleDir = moduleDir;
    }

    public ActiveEnum getActive() {
        return active;
    }

    public void setActive(ActiveEnum active) {
        this.active = active;
    }

}
