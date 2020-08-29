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
    , DBError(10, ResCodeLevelEnum.Error, "数据库错误")
    , DBWarn(11, ResCodeLevelEnum.Warn, "数据异常")
    , DBInfo(12, ResCodeLevelEnum.Warn, "数据异常")

    , DevError(20,ResCodeLevelEnum.Error,"开发时错误:程序漏洞弹出")

    , BusExce(30, ResCodeLevelEnum.Warn, "业务异常")
    , BusInfo(31, ResCodeLevelEnum.Info, "业务提示")
    , ArgExce(32, ResCodeLevelEnum.Warn, "参数异常")
    , NotFound(33, ResCodeLevelEnum.Warn,"找不到相关资源")
    , FuncNoOpen(34, ResCodeLevelEnum.Info,"功能暂未开放")

    , CashError(40, ResCodeLevelEnum.Error,"缓存出错")

    , UnknownExce(-10, ResCodeLevelEnum.Debug, "未知异常")
    , UnknownCodeExce(-11, ResCodeLevelEnum.Debug, "未知响应码")
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

