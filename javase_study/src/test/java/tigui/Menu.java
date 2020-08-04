package tigui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-08-04
 */
public class Menu {
    private Integer id;
    //0表示没有上级
    private Integer parentId;
    private String name;
    private Integer sort;
    private List<Menu> childList=new ArrayList<>();

    public Menu() {
    }

    public Menu(int id, int parentId, String name, int sort) {
        setId(id);
        setParentId(parentId);
        setName(name);
        setSort(sort);
    }
    public void sort(){
        childList.sort(Comparator.comparingInt(Menu::getSort));
    }
    /**
     * 返回前排序
     * @return
     */
    public List<Menu> getChildList() {
        sort();
        return childList;
    }
    public void addChild(Menu child) {
        if (child==null){
            return;
        }
        this.childList.add(child);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
