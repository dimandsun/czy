import com.czy.util.text.StringUtil;
import com.czy.util.io.FileUtil;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author chenzy
 * @since 2020-06-22
 */
public class T {
    @Test
    public void getText() {
        var file = new File("C:\\Users\\Samsung\\Desktop\\北师大网络教育心理测量学离线作业及答案 - 百度文库.html");
        file = new File("C:\\Users\\Samsung\\Desktop\\a.txt");
        var htmlStr = FileUtil.readFile(Optional.ofNullable(file));
        var s = StringUtil.filterHtml(Optional.of(htmlStr));
        System.out.println(s);
    }

    @Test
    public void testa() {
        System.out.println('a' - 'A');
        System.out.println(Integer.valueOf(Character.valueOf('a')));
        System.out.println(Integer.valueOf(Character.valueOf('A')));
    }

    @Test
    public void aa() {
        Integer result = Character.valueOf('a').compareTo(Character.valueOf('b'));
        System.out.println(result);
    }

    @Test public void sortString() {
        String[] result=reverseSortString("abc","aBC","a0c","123","1a");
        /*String[] result = sortString("123", "1a");*/
        Arrays.stream(result).forEach(System.out::println);
    }

    public String[] sortString(String... strings) {
        if (strings == null) {
            return null;
        }
        Arrays.sort(strings, new MyComparator());
        return strings;
    }

    public String[] reverseSortString(String... strings) {
        if (strings == null) {
            return null;
        }
        Arrays.sort(strings, new MyComparator().reversed());
        return strings;
    }

    /*自定义对字符串的排序*/
    class MyComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            if (StringUtil.isBlank(s1) && StringUtil.isNotBlank(s2)) {
                /*s1为空(包括"")，s2不为空，则s2比s1大*/
                return -1;
            } else if (StringUtil.isBlankAnd(s1, s2)) {
                /*s1为空，s2为空，则两者相等*/
                return 0;
            } else if (StringUtil.isNotBlank(s1) && StringUtil.isBlank(s2)) {
                /*s1不为空，s2为空，则s1比s2大*/
                return 1;
            } else {
                /*s1 s2都不为空*/
                int length1 = s1.length();
                int length2 = s2.length();
                int length = length1 > length2 ? length2 : length1;/*取小值*/
                for (int i = 0; i < length; i++) {
                    Character c1 = s1.charAt(i);
                    Character c2 = s2.charAt(i);
                    int compare = new MyChar(c1).compareTo(new MyChar(c2));
                    /*compare不等于0说明字符不相等，s1、s2比较完成*/
                    if (compare != 0) {
                        return compare;
                    }
                }
                /*到这里，说明前length个字符都相等，需要比较length个之后的字符*/
                if (length1 > length) {
                    /*s1长度更大，说明s1比s2大*/
                    return 1;
                } else if (length2 > length) {
                    /*s2长度更大，说明s2比s1大*/
                    return -1;
                } else {
                    /*s1 s2相等*/
                    return 0;
                }
            }
        }
    }

    /*自定义对字符的排序*/
    class MyChar implements Comparable<MyChar> {
        Character c;

        @Override
        public int compareTo(MyChar c2) {
            /*排序规则: 字母>数字>符号, 若字母相同 大写优先于小写*/
            /*(优先级: A > a > B > b > …. > 0 > 1 >!)*/
            /*都是字母*/
            if (StringUtil.isLetterAnd(c, c2.c)) {
                /*a=97 A=65*/
                if (c - c2.c == 32) {
                   /* c和c2是相同的字母，c是小写字母，c2是大写字母。c2更大，返回-1*/
                    return -1;
                } else if (c - c2.c == -32) {
                    /*c和c2是相同的字母，c是大写字母，c2是小写字母。c更大，返回1*/
                    return 1;
                } else {
                    /*转化为小写字母后 可以用字符自带的比较器比较*/
                    return Character.valueOf(Character.toLowerCase(c)).compareTo(Character.toLowerCase(c2.c));
                }
            } else if (StringUtil.isLetter(c)) {
                /*c是字母，c2不是*/
                return 1;
            } else if (StringUtil.isLetter(c2.c)) {
               /* c2是字母，c不是*/
                return -1;
            } else if (Character.isDigit(c) && Character.isDigit(c2.c)) {
                /*都是数字,注意 0大于1，所以用c2的比较器*/
                return Character.valueOf(Character.toLowerCase(c2.c)).compareTo(Character.toLowerCase(c));
            } else if (Character.isDigit(c)) {
                /*c是数字，c2不是*/
                return 1;
            } else if (Character.isDigit(c2.c)) {
               /* c2是数字，c不是*/
                return -1;
            } else {
                /*其他符号没有自定义规则，按照默认规则排序*/
                return Character.valueOf(Character.toLowerCase(c)).compareTo(Character.toLowerCase(c2.c));
            }
        }

        public MyChar(Character c) {
            this.c = c;
        }
    }
}
