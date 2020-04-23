package com.czy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 * @author chenzy
 * @date 2019.12.19
 */
public class StringUtil {
    private final static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    private StringUtil() {
    }

    public static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
            return inString;
        }
        int index = inString.indexOf(oldPattern);
        if (index == -1) {
            // no occurrence -> can return input as-is
            return inString;
        }

        int capacity = inString.length();
        if (newPattern.length() > oldPattern.length()) {
            capacity += 16;
        }
        StringBuilder sb = new StringBuilder(capacity);

        int pos = 0;  // our position in the old string
        int patLen = oldPattern.length();
        while (index >= 0) {
            sb.append(inString, pos, index);
            sb.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }

        // append any characters to the right of a match
        sb.append(inString, pos, inString.length());
        return sb.toString();
    }

    /**
     * 多个空格转成一个空格
     *
     * @param str
     * @return
     */
    public static String trimSpace(String str) {
        Pattern p = Pattern.compile("\\s+");
        Matcher m = p.matcher(str);
        return m.replaceAll(" ");
    }

    /**
     * 十进制数转十六进制数，不够指定位数就在前面补零。超过位数不截取
     *
     * @param num    十进制数
     * @param digits 位数，转成多少位的十六进制数
     * @return
     */
    public static String decimal2Hex(Integer num, int digits) {
        return String.format("%0" + digits + "x", num);
    }

    /**
     * 十六进制数转十进制数，不够指定位数就在前面补零。超过位数不截取
     *
     * @param hexStr
     * @param digits 位数，转成多少位的十进制数
     * @return
     */
    public static String hex2Decimal(String hexStr, int digits) {
        return String.format("%0" + digits + "d", new BigInteger(hexStr, 16), true);
    }

    /**
     * 十六进制数转十进制数，不够指定位数就在前面补零。
     * 如果不允许超出位数，则返回0
     *
     * @param hexStr
     * @param digits
     * @param allowExceedDigits 为false且超出长度将返回指定位数的0
     * @return
     */
    public static String hex2Decimal(String hexStr, int digits, boolean allowExceedDigits) {
        String result = String.format("%0" + digits + "d", new BigInteger(hexStr, 16));
        if (!allowExceedDigits && result.length() > digits) {
            return String.format("%0" + digits + "d", 0);
        }
        return result;
    }

    public static String trim(String str) {
        if (str == null || str.length() < 1) {
            return str;
        }
        /**
         * 去除左边空格
         */
        Integer index = str.indexOf(" ");
        //左边可能存在多个空格
        while (index == 0) {
            //str为" "
            if (str.length() == 1) {
                return "";
            } else {
                str = str.substring(1);
            }
            index = str.indexOf(" ");
        }
        //去除右边空格
        index = str.lastIndexOf(" ");
        while (index == str.length() - 1) {
            if (str.length() == 1) {
                return "";
            } else {
                str = str.substring(0, index);
            }
            index = str.lastIndexOf(" ");
        }
        return str;
    }

    public static String spaceReplaceLR(final String msg) {
        if (msg == null || msg.equals(""))
            return msg;
        char[] char_msg = msg.toCharArray();

        int left_space = 0;
        // 左边空格部分
        for (int i = 0; i < char_msg.length; i++) {
            // 是空格的处理过程
            if (char_msg[i] == ' ') {
                left_space++;
                continue;
            } else
                break;
        }
        // 右边空格部分
        int right_space = 0;
        for (int i = char_msg.length - 1; i >= 0; i--) {
            // 是空格的处理过程
            if (char_msg[i] == ' ') {
                right_space++;
                continue;
            } else
                break;
        }
        // 大部分情况下，认为是不需要取空格可以直接返回的
        if (left_space == 0 && right_space == 0)
            return msg;
        // 取需要获取的值
        char[] retVal = new char[msg.length() - (left_space + right_space)];

        for (int i = 0; i < retVal.length; i++) {
            retVal[i] = char_msg[left_space + i];
        }

        return String.valueOf(retVal);
    }

    /**
     * 随机生成6位数(字母加数字)
     */
    public static String random6Num() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        str += str.toLowerCase();
        str += "0123456789";
        StringBuffer sb = new StringBuffer(6);
        for (int i = 0; i < 6; i++) {
            char ch2 = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch2);
        }
        return sb.toString();
    }

    /**
     * 都不为空
     *
     * @param objs
     * @return
     */
    public static boolean isNotBlank(Object... objs) {
        if (objs == null || objs.length < 1) {
            return false;
        }
        for (Object obj : objs) {
            if (isBlank(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 都不为空
     *
     * @param strs
     * @return
     */
    public static boolean isNotBlank(String... strs) {
        if (strs == null || strs.length < 1) {
            return false;
        }
        for (String str : strs) {
            if (isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(Object object) {
        return !isBlank(object);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(Object obj) {
        return obj == null ? true : isBlank(obj.toString());
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        str = str.trim();
        if (str.length() == 0) {
            return true;
        }
        final CharSequence cs = str;
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(String... strs) {
        if (strs == null || strs.length < 1) {
            return true;
        }
        for (String str : strs) {
            if (isNotBlank(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(Object... objs) {
        if (objs == null || objs.length < 1) {
            return true;
        }
        for (Object obj : objs) {
            if (isNotBlank(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 用于处理将字符集改换成map 例:
     *
     * @param msg
     */
    public static Map<String, String> getMap(String msg, String splitStr) {
        try {
            Map<String, String> result = null;
            String[] array = getSplitArray(msg, splitStr);
            for (String temp : array) {
                String[] key_val = temp.split("=");
                if (key_val.length < 2) {
                    result.put(key_val[0], null);
                } else {
                    result.put(key_val[0], key_val[1]);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用于处理字符串中多个分隔符的方法
     *
     * @param msg      原始信息
     * @param splitStr 分隔字符串
     * @return
     */
    public static String[] getSplitArray(String msg, String splitStr) {

        int cursor = 0;
        char[] compare = splitStr.toCharArray();
        ArrayList<String> matchList = new ArrayList<String>();
        StringBuffer result = new StringBuffer();
        while (cursor < msg.length()) {
            char nextChar = msg.charAt(cursor);
            int compareflag = 0;

            // //////////////////////////////////////////////////////////////////////////////////////////////////////////
            // 比较是否为相同的内容
            for (char temp : compare) {
                if (nextChar != temp)
                    break;
                else {
                    compareflag++;
                    // 需要获得数据内容
                    if (compare.length == compareflag) {
                        matchList.add(result.toString());
                        result = new StringBuffer();
                    }
                }
                cursor++;
                if (cursor >= msg.length())
                    break;

                nextChar = msg.charAt(cursor);
            }
            // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
            result.append(nextChar);
            cursor++;
        }
        // 最后一个分隔是不需要加分隔符的
        if (false == result.toString().equals("")) {
            matchList.add(result.toString());
        }
        String[] arrayResult = new String[matchList.size()];
        return matchList.toArray(arrayResult);
    }

    /**
     * studentid=140000000000389509*_typeid=6*_schoolid=1#?managerflag=0*_machineid=9912120600000339*_machinetypeid=02
     * 把字符串转换为标准json字符串
     */
    public static String getStandardJsonString(String data) {
        if (isBlank(data)) {
            return null;
        }
        data = data.replaceAll("\\*_", "\",\"").replaceAll("#\\?", "\",\"").replaceAll("=", "\":\"");
        data = "\"" + data + "\"";
        if (!data.startsWith("{")) {
            data = "{" + data;
        }
        if (!data.endsWith("}")) {
            data += "}";
        }
        return data;
    }

    /**
     * 转换boolean 1表示true 其他false
     * 字符串true表示true 其他false
     *
     * @param value
     * @return
     */
    public static Boolean getBoolean(String value) {
        if (StringUtil.isBlank(value)) {
            return false;
        }
        return value.equals("1") || value.toLowerCase().equals("true");
    }

    /**
     * 转换成boolean值 1表示true 其他false
     * 字符串true表示true 其他false
     *
     * @param value
     * @return
     */
    public static Boolean getBoolean(Object value) {
        return getBoolean(value, false);
    }

    /**
     * 转换成boolean值 1表示true 其他false
     * 字符串true表示true 其他false
     *
     * @param value
     * @return
     */
    public static Boolean getBoolean(Object value, Boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return getBoolean(value.toString());
    }


    /**
     * boolean值转int
     * true=》 1 false=》0
     *
     * @param value
     * @return
     */
    public static Integer getInt(Boolean value) {
        return value ? 1 : 0;
    }

    /**
     * 对象转Integer,null返回null
     *
     * @param value
     * @return
     */
    public static Integer getInt(Object value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return Integer.valueOf(value.toString());
    }

    /**
     * 得到毫秒值
     * 将1000ms转成1000
     *
     * @return
     */
    public static Long getLongByMS(Object value, Long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        String temp = value.toString().toLowerCase();
        if (temp.endsWith("ms")) {
            temp = temp.replace("ms", "");
        }
        return Long.valueOf(temp);
    }
    public static Long getLong(Object value, Long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Long){
            return (Long) value;
        }
        String temp = value.toString().toLowerCase();
        return Long.valueOf(temp);
    }
    public static String getStr(Object value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value.toString();
    }

    public static String[] getStrlist(Object value, String[] defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof String) {
            return new String[]{value.toString()};
        }
        return (String[]) value;
    }

    /**
     * 金额转换
     * 0.22 转换22
     * 即0.22元换成22分
     *
     * @return
     */
    public static Integer strMoney2Int(String money) {
        String[] ss = money.split("\\.");
        Integer result = 0;
        if (ss.length == 1) {
            result = Integer.valueOf(money) * 100;
        } else if (ss.length == 2) {
            result = Integer.valueOf(ss[0]) * 100 + Integer.valueOf(ss[1]);
        }
        return result;
    }

    /**
     * 批量判断相邻对象是否相等，全部相等才返回true
     * o[0] o[1]是否相等
     * o[2] o[3]是否相等
     *
     * @param objects
     * @return
     */
    public static Boolean isEqual(Object... objects) {
        if (objects == null || objects.length == 0 || objects.length % 2 != 0) {
            return false;
        }
        for (int i = 0; i < objects.length - 1; i += 2) {
            if (isEqual(objects[i], objects[i + 1])) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两个对象是否相等
     *
     * @param o1
     * @param o2
     * @return
     */
    public static Boolean isEqual(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        if (o2 == null) {
            return o1 == null;
        }
        if (o1.getClass() == o2.getClass()) {
            return o1.equals(o2);
        }
        return o1.toString().equals(o2.toString());
    }

    /**
     * 字符串首字母大写
     *
     * @param str
     * @return
     */
    public static String upFirst(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        if (cs[0] >= 'a' && cs[0] <= 'z') {
            cs[0] -= 32;
            return String.valueOf(cs);
        } else {
            return str;
        }
    }

    /**
     * 字符串首字母小写
     *
     * @param str
     * @return
     */
    public static String lowFirst(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        if (cs[0] >= 'A' && cs[0] <= 'Z') {
            cs[0] += 32;
            return String.valueOf(cs);
        } else {
            return str;
        }
    }

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /***
     * 验证字符串是否是指定格式
     * @param str 待验证的字符串
     * @param patternS 正则表达式
     * @return
     */
    public static boolean matcher(String str, String patternS) {
        Pattern pattern = Pattern.compile(patternS);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 验证字符串是否是数字，包括负数，小数
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        //Pattern pattern = Pattern.compile("^-?[0-9]+"); //这个也行
        return matcher(str, "^-?\\d+(\\.\\d+)?$");
    }

    /**
     * 验证字符串是否是正整数
     *
     * @param str
     * @return
     */
    public static boolean isPositiveInt(String str) {
        return matcher(str, "^[0-9]*$");
    }

    /**
     * 截取字符串，截取从0开始到子字符串第n次出现的位置。若子字符串没有出现，则返回原字符串
     *
     * @return
     */
    public static String subStrByIndex(String str, String childStr, Integer n) {
        if (isBlank(str) || str.indexOf(childStr) < 0) {
            return str;
        }
        Matcher mat = Pattern.compile(childStr).matcher(str);
        Integer index = 0;
        while (mat.find()) {
            index++;
            //第N次出现位置
            if (index.equals(n)) {
                return str.substring(0, mat.start());
            }
        }
        return str;
    }


    public static void main(String[] args) {

    }


}
