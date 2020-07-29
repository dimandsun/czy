package test69_GraspData;

import com.czy.util.json.JsonUtil;

/**
 * @author chenzy
 * @since 2020-06-11
 * 弹幕
 */
public class BulletChat {
    private String text;
    private String nickname;
    private String uname_color;
    private String uid;
    private String timeline;
    private Boolean isadmin;
    private Integer vip;
    private Integer svip;

    @Override
    public String toString() {
        return JsonUtil.model2Str(this);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUname_color() {
        return uname_color;
    }

    public void setUname_color(String uname_color) {
        this.uname_color = uname_color;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public Boolean getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(Boolean isadmin) {
        this.isadmin = isadmin;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getSvip() {
        return svip;
    }

    public void setSvip(Integer svip) {
        this.svip = svip;
    }
}
