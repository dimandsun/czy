import com.czy.util.model.StringMap;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-15
 */
public class TestJDBC {
    static String url="jdbc:mysql://106.54.230.187:3306/czy?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrievalx=true";
    static String userName = "root";
    static String password="chen";
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void jdbcInsert() throws SQLException {
        Connection conn = DriverManager.getConnection(url, userName, password);
        Statement s = conn.createStatement();
        String sql = """
        insert into test(name)values('abc')
        """;
        int result = s.executeUpdate(sql);
        System.out.println(result);
    }
    @Test
    public void jdbcInsert2() throws SQLException {
        String sql = """
        insert into test(name)values(?);
        """;
        try (var conn = DriverManager.getConnection(url, userName, password);
             var s = conn.prepareStatement(sql);
        ){
            s.setString(1,"heheie");
            int result = s.executeUpdate();
            System.out.println(s.toString());
            System.out.println(result);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Test
    public void jdbcSelect() {
        try(var conn = DriverManager.getConnection(url, userName, password);
            var s = conn.createStatement();) {
            var sql = """
        select * from test;
        """;
            var resultSet= s.executeQuery(sql);
            var metaData=resultSet.getMetaData();
            var columnCount=metaData.getColumnCount();//列数
            var result = new ArrayList<StringMap>();
            while (resultSet.next()){
                var map = new StringMap(columnCount);
                /*下标从1开始*/
                for (int i = 1; i <= columnCount; i++) {
                    var columnName=metaData.getColumnName(i);
                    map.add(columnName,resultSet.getObject(i));
                }
                result.add(map);
            }
            result.forEach(System.out::println);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
