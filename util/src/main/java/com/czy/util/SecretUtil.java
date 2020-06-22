package com.czy.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author chenzy
 *
 * @since 2020-04-29
 */
public class SecretUtil {
    public static String md5(String msg) {
        if (msg==null){
            return null;
        }
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(msg.getBytes("UTF-8"));
            int i;
            StringBuilder sb = new StringBuilder();
            for (int offset = 0; offset < digest.length; offset++) {
                i = digest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    sb.append(0);
                sb.append(Integer.toHexString(i));//通过Integer.toHexString方法把值变为16进制
            }
            return sb.toString().substring(0, 32);//从下标0开始，length目的是截取多少长度的值
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
