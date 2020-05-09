package com.czy.fx.test;

import com.czy.util.ClassUtil;
import com.czy.util.StringUtil;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzy
 * @description
 * @since 2020/5/8
 */
public class FXUtil {
    private FXUtil() {
    }

    public static<T extends Node> List<T> getNodeList(Class<T> nodeClass,String... nodeNames) {
        if (nodeClass==null||nodeNames == null || nodeNames.length == 0) {
            return null;
        }
        var nodeList = new ArrayList<T>(nodeNames.length);
        for (String nodeName : nodeNames) {
            try {
                nodeList.add(nodeClass.getDeclaredConstructor(String.class).newInstance(nodeName));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return nodeList;
    }
    public static List<Button> getButtonList(String... btnNames) {
        if (btnNames == null || btnNames.length == 0) {
            return null;
        }
        var buttonList = new ArrayList<Button>(btnNames.length);
        for (String btnName : btnNames) {
            buttonList.add(new Button(btnName));
        }
        return buttonList;
    }

    /**
     * 设置控件内边距
     * ,对齐方式
     * ，子控件间距
     * ，布局方向
     * ，控件外边距
     *
     * @param pane
     * @param padding
     */
    public static Pane setStyle(Pane pane, Double padding, Pos pos, Double spacing, Orientation orientation, Node marginNode, Insets margin) {
        /*设置控件内边距*/
        if (padding != null) {
            pane.setPadding(new Insets(padding));
        }
        /*对齐方式：*/
        if (pos != null) {
            invoke(pane, "setAlignment", pos);
        }
        /*设置子控件间距*/
        if (spacing != null) {
            double temp = spacing;
            if (!invoke(pane, "setSpacing", temp)) {
                /*流式布局：水平间距*/
                if (invoke(pane, "setHgap", temp)) {
                    /*流式布局：垂直间距*/
                    invoke(pane, "setVgap", temp);
                }
            }
        }
        /*流式布局:布局方向*/
        if (orientation != null) {
            invoke(pane, "setOrientation", orientation);
        }
        /*设置指定子控件外边距*/
        if (marginNode != null && margin != null) {
            invoke(pane, "setMargin", marginNode, margin);
        }
        return pane;
    }

    /*设置组件颜色*/
    public static Pane setColor(Pane pane, String RGB) {
        pane.setStyle("-fx-background-color: " + RGB + ";" + pane.getStyle());
        return pane;
    }

    private static Boolean invoke(Object o, String methodName, Object... pars) {
        if (o == null || StringUtil.isBlank(methodName)) {
            return false;
        }
        Class[] parClasss = null;
        if (pars != null && pars.length > 0) {
            parClasss = new Class[pars.length];
            for (int i = 0; i < pars.length; i++) {
                if (pars[i] instanceof Node){
                    parClasss[i]=Node.class;
                }else {
                    parClasss[i] = ClassUtil.getBasicType(pars[i].getClass());
                }
            }
        }
        var c = o.getClass();
        try {
            var m = c.getMethod(methodName, parClasss);
            m.invoke(o, pars);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Class c = GridPane.class;
        for (var m : c.getMethods()) {
            if (m.getName().equals("setMargin")) {
                System.out.println(m);
            }
        }

    }
}