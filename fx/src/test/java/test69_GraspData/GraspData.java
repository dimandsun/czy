package test69_GraspData;

import com.czy.fx.test.FXUtil;
import com.czy.util.io.FileUtil;
import com.czy.util.json.JsonUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenzy
 * @since 2020-06-11
 */
public class GraspData  extends Application {
    //7735393
    public static List<BulletChat> getGraspDataList(String roomId) {
        String url = "https://api.live.bilibili.com/ajax/msg";
        var list = new ArrayList<BulletChat>();
        try {
            var url1 = new URL(url);
            var connection = (HttpURLConnection) url1.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();

            var writer = new OutputStreamWriter(connection.getOutputStream());
            var postValue = "roomid=" + URLEncoder.encode(roomId, "UTF-8")
                    + "&csrf_token=" + URLEncoder.encode("8bc6f1dc9e2e9e0e38ccc36e45da3dfa", "UTF-8")
                    + "&csrf=" + URLEncoder.encode("8bc6f1dc9e2e9e0e38ccc36e45da3dfa", "UTF-8")
                    + "&visit_id=" + URLEncoder.encode("", "UTF-8");
            writer.write(postValue);
            writer.flush();
            writer.close();

            var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                var jsonMap=JsonUtil.str2Map(line);
                var data = (Map) jsonMap.get("data");
                ((List<Map<String,Object>>) data.get("admin")).forEach(map -> list.add(JsonUtil.map2Model(map,BulletChat.class)));
                ((List<Map<String,Object>>) data.get("room")).forEach(map -> list.add(JsonUtil.map2Model(map,BulletChat.class)));
            }
            return list;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var fxmlLoader=new FXMLLoader();
        var file = FileUtil.getCodeFile("fx","test69_GraspData.GraspData.fxml");
        AnchorPane anchorPane=fxmlLoader.load(new FileInputStream(file));
        primaryStage.setTitle("抓取弹幕");
        primaryStage.setAlwaysOnTop(true);
        FXUtil.setDefaultValue(primaryStage,anchorPane);

    }
}
