package com.czy.util.text;


import com.czy.util.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 * @author chenzy
 * @date 2019.12.19
 */
public class StringUtil {
    private StringUtil() {
    }

    public static Character[] getArray(String str) {
        if (isBlank(str)) {
            return null;
        }
        var chars = str.toCharArray();
        var result = new Character[chars.length];
        for (var i = 0; i < chars.length; i++) {
            result[i] = chars[i];
        }
        return result;
    }
    public static String replaceALL(String str, String pattern, List values) {
        if (values==null||values.isEmpty()||isBlank(str)){
            return str;
        }
        for (Object value:values){
            str=str.replaceFirst(pattern,value+"");
        }
        return str;
    }
    public static String filterHtml(String htmlStr) {
        if (isBlank(htmlStr)) {
            return "";
        }
        var sb = new StringBuffer();
        var matcher = Pattern.compile("<([^>]*)>").matcher(htmlStr);
        while (matcher.find()) {
            matcher.appendReplacement(sb, Line.separator);
        }
        return sb.toString();
    }

    /*jdk序列化*/
    public static <T> byte[] serializeJDK(T object) {
        try (var byteStrem = new ByteArrayOutputStream();
             var outStrem = new ObjectOutputStream(byteStrem);
        ) {
            outStrem.writeObject(object);
            return byteStrem.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object derializerJDK(byte[] value) {
        if (value == null || value.length < 1) {
            return null;
        }
        try (var in = new ObjectInputStream(new ByteArrayInputStream(value))) {
            return in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * msg中的{}用pars代替
     * @param msg
     * @param pars
     * @return
     */
    public static String join(String msg, Object... pars) {
        if (msg == null || !msg.contains("{}") || pars == null || pars.length == 0) {
            return msg;
        }
        for (var par : pars) {
            var temp = par == null ? "" : par.toString();
            if (temp.contains("{}")) {
                /*obj.toString中可能包含{}*/
                temp = temp.replaceAll("\\{}", "");
            }
            msg = msg.replaceFirst("\\{}", temp);
        }
        return msg;
    }

    public static String println(String msg, Object... pars) {
        var result = join(msg, pars);
        System.out.println(result);
        return result;
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
        var sb = new StringBuilder(capacity);

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
     * 擦除首尾空格，文本内部多个空格转成一个空格
     * @param str
     * @return
     */
    public static String eraseSpace(String str) {
        return Pattern.compile("\\s+").matcher(str.strip()).replaceAll(" ");
    }

    /**
     * 擦除中文
     * @param str
     * @return
     */
    public static String eraseChinese(String str) {
        return Pattern.compile("[\u4e00-\u9fa5]").matcher(str).replaceAll("");
    }

    /**
     * 十进制数转十六进制数
     *
     * @param num
     * @return
     */
    public static Integer decimal2Hex(Integer num) {
        return Integer.parseInt("ff", num);
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

    public static <T> boolean isBlank(List<T> objList) {
        return objList == null || objList.isEmpty();
    }

    public static boolean isBlank(Object obj) {
        return obj == null ? true : isBlank(obj.toString());
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        str = str.strip();
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
     * 判断参数是否都为空
     */
    public static boolean isBlankAnd(String... strs) {
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

    /**
     * 判断字符是否是字母(a-z或者A-Z)
     *
     * @return
     */
    public static Boolean isLetter(Character c) {
        return Character.isLowerCase(c) || Character.isUpperCase(c);
    }

    /**
     * 判断字符是否都是字母(a-z或者A-Z)
     *
     * @return
     */
    public static Boolean isLetterAnd(Character... cs) {
        if (cs == null) {
            return false;
        }
        for (Character c : cs) {
            if (!isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断参数是否存在空
     */
    public static boolean isBlankOr(String... strs) {
        if (strs == null || strs.length < 1) {
            return true;
        }
        for (String str : strs) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlankAnd(Object... objs) {
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

    public static boolean isBlankOr(Object... objs) {
        if (objs == null || objs.length < 1) {
            return true;
        }
        for (Object obj : objs) {
            if (isBlank(obj)) {
                return true;
            }
        }
        return false;
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

    public static Integer getInt(Object value) {
        return getInt(value, null);
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
        if (value instanceof Integer integer) {
            return integer;
        } else if (value instanceof Long l) {
            return l.intValue();
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

    public static Long getLong(Object value) {
        return getLong(value, null);
    }

    public static Long getLong(Object value, Long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Long l) {
            return l;
        }
        return Long.valueOf(value.toString());
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
        if (value instanceof String s) {
            return new String[]{s};
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
        return Pattern.compile(patternS).matcher(str).matches();
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

    /*根据正则表达式获取字符串，返回第一次出现的子串，若正则表达式没有出现，则返回""*/
    public static String subStr(String str, String regex) {
        return subStr(str, regex, 1);
    }

    /**
     * 根据正则表达式获取字符串，返回第n次出现的子串，若正则表达式没有出现，则返回""
     *
     * @return
     */
    public static String subStr(String str, String regex, Integer n) {
        if (isBlankOr(str, regex)) {
            return "";
        }
        Matcher mat = Pattern.compile(regex).matcher(str);
        Integer index = 0;
        while (mat.find()) {
            index++;
            //第N次出现位置
            if (index.equals(n)) {
                return str.substring(mat.start(), mat.end());
            }
        }
        return "";
    }


    public static void main(String[] args) {
    }


    public static String concat(String... msgs) {
        if (msgs == null) {
            return "";
        }
        String result = "";
        for (int i = 0; i < msgs.length; i++) {
            result = result.concat(msgs[i]);
        }
        return result;
    }

    /**
     * 字符串多倍复制，以参数三为分隔符
     *
     * @param multiples
     * @param separator
     * @return
     */
    public static String copy(String str, int multiples, String separator) {
        if (StringUtil.isBlank(str) || multiples == 0) {
            return "";
        }
        if (StringUtil.isBlank(separator)) {
            separator = ",";
        }
        var result = str;
        for (int i = 1; i < multiples; i++) {
            result += separator + str;
        }
        return result;
    }
}
