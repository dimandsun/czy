package com.czy.swing.entrance;

import com.czy.core.CoreContainer;
import com.czy.core.model.CoreProject;

/**
 * @author chenzy
 * @description
 * @since 2020-04-29
 */
public class SwingProject extends CoreProject {
    private static SwingProject instance=new SwingProject();
    public static SwingProject getInstance(){
        return instance;
    }
    protected SwingProject(){
    }
    public void init(){
        super.init();
    }

    @Override
    public void addProjectGroupId(){
        super.addProjectGroupId();
        var coreContainer = CoreContainer.getInstance();
        coreContainer.addProjectGroupId(groupId);
        coreContainer.addProjectGroupId("com.czy.user");
    }
    private final String projectName="czy_swing";
    private final String groupId = "com.czy.swing";
    public String getGroupId() {
        return groupId;
    }
    public String getProjectName() {
        return projectName;
    }
}
