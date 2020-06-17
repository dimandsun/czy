package com.czy.javase.study.test0_helloWorld;

/**
 * HelloWord是类名，可以自定义，自定义的文本不能为关键字。public和class是java中的关键字，
 * 一个类的内容由大括号包裹
 *
 * @author chenzy
 * @since 2020-06-12
 */
public class HelloWord {
    /**
     * main方法是java程序入口，main是方法名。
     * static和void是java关键字
     * 方法名后面接小括号(),括号内部是方法接收的参数，即输入信息
     *
     * @param args 是命令行传进来的参数，可以不传，通常不传，不传时为空。
     *             数据类型时字符串数组，
     *             字符串可以理解为文本。
     *             数组是集合的一种。可以理解为数组是个柜子，柜子里有一个个抽屉，一个抽屉可以放一个元素。
     *             字符串数组表示这个柜子的抽屉只能放字符串。
     */
    public static void main(String[] args) {
        //for循环，遍历数组，
        //for循环有两种形式，这是jdk8(?)加的一种新形式，在jdk低版本没有。
        //for循环的循环体通常以大括号包裹，但是循环体只有一条语句时可以不加大括号
        //arg是args的一个字符串，即字符串数组里的一个抽屉里的元素。
        //这个循环的作用就是把柜子的抽屉一个个打开，取出里面的内容
        for (String arg : args) {
            //每个语句以分号;结尾
            //这是个输出语句，这个语句执行了一个叫println的内置方法。
            //  这个方法的作用是在控制台打印一行文本，文本内容为括号内的东西
            System.out.println(arg);
        }
        //
        System.out.println("hello world!\n你好世界！");
    }
}
