package tigui;

import net.sf.cglib.core.CollectionUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenzy
 * @date 2020-08-04
 */
public class MenuUtil {
    private MenuUtil(){}
    public static List<Menu> getTree(List<Menu> menuList) throws Exception {
        return getTree(menuList,0);
    }
    public static List<Menu> getTree(List<Menu> menuList,Integer rootId) throws Exception {
        if (menuList==null||menuList.isEmpty()){
            return null;
        }
        if (rootId==null){
            rootId=0;
        }
        //根节点
        List<Menu> result = new ArrayList<>();
        for (var menu:menuList){
            if (rootId.equals(menu.getParentId())){
                menu.addChild(getChild(menu,menuList));
                result.add(menu);
            }
        }
        return result;
    }
    private static Menu getChild(Menu rootMenu, List<Menu> menuList) {
        for (var menu:menuList){
            if (rootMenu.getId().equals(menu.getParentId())){
                menu.addChild(getChild(menu,menuList));
                rootMenu.addChild(menu);
            }
        }
        return rootMenu;
    }

    public static void main(String[] args) {
        List<Menu> menuList=new ArrayList<>();
        menuList.add(new Menu(1,0,"审计计划管理",99));
        menuList.add(new Menu(2,1,"年度计划编制",1));
//        menuList.add(new Menu(3,1,"年度计划调整",2));
//        menuList.add(new Menu(4,1,"年度计划审批",3));
//        menuList.add(new Menu(5,1,"年度计划汇总",4));
//        menuList.add(new Menu(6,1,"年度计划查询",5));
//        menuList.add(new Menu(7,0,"审计项目管理",99));
//        menuList.add(new Menu(8,7,"项目启动",1));
//        menuList.add(new Menu(9,7,"审计工作台",2));
        try {
            var menuVOList=getTree(menuList);
            System.out.println(menuVOList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
