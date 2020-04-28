package com.czy.english.enums;

import com.czy.util.IEnum;

/**
 * @author chenzy
 * @since 2020-04-28
 * @description 动词时态：三种时，四种态: 过去时、现在时、将来时 一般式、进行式、完成式、进行完成式
 十二种时态
一般现在时（do），一般过去时（did），一般将来时（will do)
，现在进行时(is/am/are doing)，过去进行时(was/were doing)，将来进行时(可与一般将来时换用，will be doing)
，现在完成时(have/has done) 过去完成时(had done)，发生在一般过去时的动作之前），将来完成时(will have done)
，现在完成进行时（have/has been doing)，过去完成进行时(had been doing)，将来完成进行时(will have been doing)
+以下四种时态
，过去将来时(would do)，过去将来进行时(would be doing)，过去将来完成时(would have done)，过去将来完成进行时(would have been doing)
 *
 */
public enum TenseEnum implements IEnum<Integer> {
    CommonPresent(1,"一般现在时"),CommonPast(2,"一般过去时"),CommonFuture(3,"一般将来时")
    ,InPresent(4,"现在进行时"),InPast(5,"过去进行时"),InFuture(6,"将来进行时")
    ,AlreadyPresent(7,"现在完成时"),AlreadyPast(8,"过去完成时"),AlreadyFuture(9,"将来完成时")
    ,InAlreadyPresent(10,"现在完成进行时"),InAlreadyPast(11,"过去完成进行时"),InAlreadyFuture(12,"将来完成进行时")
    ,PastFuture(13,"过去将来时"),PastFutureIn(14,"过去将来进行时"),PastFutureAlready(15,"过去将来完成时"),PastFutureInAlready(16,"过去将来完成进行时")
    ;
    TenseEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    private Integer id;
    private String msg;

    @Override
    public Integer getValue() {
        return this.id;
    }
}
