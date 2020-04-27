package com.czy.util.enums;


import com.czy.core.enums.ResCodeLevelEnum;
import com.czy.util.IEnum;

/**
 * 响应码
 * <p>
 * -1到-100 系统后台分配值
 * -101到-200 APP/小程序使用值
 * -301到-400 公用CODE值
 * CODE=0时，正常操作
 * CODE>0时，控制流程值
 * <p>
 * 新增 SecretKeyError，UnknownExce
 * @author chenzy
 * @date 2019.12.16
 */
public enum ResCodeEnum implements IEnum<Integer> {
    Normal(0, ResCodeLevelEnum.Normal, "成功")
    /*************************Info*******************************************/
    ,BusInfo(1, ResCodeLevelEnum.Info, "业务提示")
    /*************************错误*******************************************/
    ,DBError(2, ResCodeLevelEnum.Lev1, "数据库系统错误")
    , DataExce(3, ResCodeLevelEnum.Lev3, "数据异常")
    , UserSerError(4, ResCodeLevelEnum.Lev1, "用户管理中心错误")
    , ForceLogout2(5, ResCodeLevelEnum.SysProtect, "强制下线")//系统保护，需要客户端强制用户下线，重新登录
    , SecretKeyError(6, ResCodeLevelEnum.Lev3, "密钥错误")//数据验证时加密信息错误
    , ArgAnalyExce(7, ResCodeLevelEnum.Debug, "参数解析异常")
    , ArgEmpty(8, ResCodeLevelEnum.Debug, "参数为空")
    , ArgNoCorrect(9, ResCodeLevelEnum.Debug, "参数值不正确")
    , BusInExce(10, ResCodeLevelEnum.Debug, "内部业务异常")
    , UnknownExce(11, ResCodeLevelEnum.Debug, "未知异常")
    , UnknownCodeExce(12, ResCodeLevelEnum.Debug, "未知响应码")
    ;
    public boolean isNotNormal() {
        return !isNormal();
    }
    public boolean isNormal() {
        return this.codeLevelEnum.equals(ResCodeLevelEnum.Normal);
    }

    ResCodeEnum(Integer code, ResCodeLevelEnum codeLevelEnum, String msg) {
        this.code = code;
        this.codeLevelEnum = codeLevelEnum;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
    private ResCodeLevelEnum codeLevelEnum;

    @Override
    public String toString() {
        return getValue().toString();
    }

    @Override
    public Integer getValue() {
        return this.code;
    }

    public String getMSG() {
        return this.msg;
    }
}

