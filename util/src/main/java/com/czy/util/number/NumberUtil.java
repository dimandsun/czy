package com.czy.util.number;

import java.util.Optional;

/**
 * @author chenzy
 * @date 2020-06-22
 */
public class NumberUtil {
    private NumberUtil(){}
    public static Integer byte2Int(Optional<Byte> optionalByte){
//        Optional<Integer> result=Optional.empty();
        return null;
    }
    public static byte[] int2Bytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte)((value >> 8 * i) & 0xff);
        }
        return b;
    }
}
