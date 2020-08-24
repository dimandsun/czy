import com.czy.util.enums.ResCodeLevelEnum;
import com.czy.util.io.FileUtil;
import com.czy.util.io.office.OfficeFileUtil;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;
import com.czy.util.time.TimeUtil;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenzy
 * 
 * @since 2020-05-14
 */
public class T {
    @Test
    public void readWord() {
        var result=OfficeFileUtil.readWord(new File("/czy/file/a.doc"));
        System.out.println(result);
    }

    @Test
    public void readExcel() {
        {
            var result =OfficeFileUtil.readExcel(new File("/czy/file/test.xlsx"));
            System.out.println(result);
        }
        var result =OfficeFileUtil.readExcel(new File("/czy/file/b.xls"));
        System.out.println(result);
    }

    @Test
    public void wrteExcel() {
        var head1=new ArrayList<String>(){{
            add("姓名");add("年龄");
        }};
        var head2=new ArrayList<String>(){{
            add("姓名2");add("年龄2");
        }};
        var data1=new ArrayList<List<Object>>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            data1.add(new ArrayList(){{
                add("陈志源"+ finalI);add(finalI+20);
            }});
        }
        var data2=new ArrayList<List<Object>>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            data2.add(new ArrayList(){{
                add("afds"+ finalI);add(finalI+20);
            }});
        }
        var  dataMap=new StringMap<>(2,"aa",data1).add("bb",data2);
        var headMap=new StringMap<List<String>>(2,"aa",head1).add("bb",head2);
        OfficeFileUtil.writeExcel(new File("/czy/file/test.xlsx"),dataMap,headMap);
    }

    @Test
    public void testEnum() {
        var result=JsonUtil.str2Model("1", ResCodeLevelEnum.class);
        System.out.println(result);
    }

    @Test
    public void option() {

        Map<String, Map<String,Map<String,Object>>> map = new HashMap<>();
       /* map.put("a", 123);
        map.put("a22", 222);*/
/*        Optional.of(map).map(new Function<Map<String, Object>, Object>() {

            @Override
            public Object apply(Map<String, Object> stringObjectMap) {
                return null;
            }

            @Override
            public <V> Function<V, Object> compose(Function<? super V, ? extends Map<String, Object>> before) {
                return null;
            }

            @Override
            public <V> Function<Map<String, Object>, V> andThen(Function<? super Object, ? extends V> after) {
                return null;
            }
        });*/

    }
    @Test public void testMac(){
        String s= "/.*";
        var result =StringUtil.matcher("/start",s);
        System.out.println(result);
    }
    @Test
    public void time(){
        Long s=1596281053967L;
        var result=TimeUtil.long2Str(s,TimeUtil.yyyyMMddHHmmssSSS);
        System.out.println(result);
    }

    @Test
    public void testBoolean() {
        Boolean b1=null;
        boolean b2=b1;
        System.out.println(b2);
    }

    @Test
    public void fielTest() {
        var f=new File("/a/bc/abc.log");
        FileUtil.createFile(f);
    }
}
