package com.czy.util.enums;


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
    ,DBError(1, ResCodeLevelEnum.Lev1, "数据库系统错误")
    , SysDataExce(2, ResCodeLevelEnum.Lev3, "系统数据异常")
    , LoginError(2, ResCodeLevelEnum.Lev1, "用户登录接口错误")
    , ForceLogout1(3, ResCodeLevelEnum.SysProtect, "强制下线")//系统保护，需要客户端强制用户下线，重新登录
    , BusInfo(4, ResCodeLevelEnum.Info, "业务提示")
    , SecretKeyError(5, ResCodeLevelEnum.Lev3, "密钥错误")//数据验证时加密信息错误
    , ArgAnalyExce(6, ResCodeLevelEnum.Debug, "参数解析异常")
    , BusInExce(7, ResCodeLevelEnum.Debug, "内部业务异常")
    , UnknownExce(8, ResCodeLevelEnum.Debug, "未知异常")
    , UnknownCodeExce(9, ResCodeLevelEnum.Debug, "未知响应码")
    , NotFound(10, ResCodeLevelEnum.Lev3,"找不到相关资源")
    , DBExce(11, ResCodeLevelEnum.Lev3,"数据库异常")
    , CashExce(12, ResCodeLevelEnum.Lev3,"缓存出错")
    , FuncNoOpen(13, ResCodeLevelEnum.Info,"功能暂未开放")
    ;
    public boolean isError() {
        return !isNotError();
    }
    public boolean isNotError() {
        return this.codeLevelEnum.equals(ResCodeLevelEnum.Normal)||this.codeLevelEnum.equals(ResCodeLevelEnum.Process);
    }
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

