package com.czy.user;

import com.czy.core.CoreContainer;
import com.czy.core.model.CoreProject;

/**
 * @author chenzy
 * @description
 * @since 2020-04-23
 */
public class UserProject extends CoreProject {
    private static UserProject instance=new UserProject();
    public static UserProject getInstance(){
        return instance;
    }
    protected UserProject(){
    }
    @Override
    public void addProjectGroupId(){
        super.addProjectGroupId();
        var coreContainer = CoreContainer.getInstance();
        coreContainer.addProjectGroupId(groupId);
    }
    private final String projectName="czy_user";
    private final String groupId = "com.czy.user";
    public String getGroupId() {
        return groupId;
    }
    public String getProjectName() {
        return projectName;
    }
}
