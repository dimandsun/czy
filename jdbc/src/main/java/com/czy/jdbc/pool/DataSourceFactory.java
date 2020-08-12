package com.czy.jdbc.pool;

import com.czy.jdbc.exception.DataSourceException;
import com.czy.util.io.FileUtil;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.SettingFile;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

import java.sql.SQLException;
import java.util.*;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class DataSourceFactory {
    private static SettingFile settingFile;
    private static Map<String, SimpleDataSource> dataSourceMap = Collections.synchronizedMap(new HashMap<>());

    private DataSourceFactory() {
    }

    public static SimpleDataSource getDataSource(String dataSourceKey) {
        return dataSourceMap.get(dataSourceKey);
    }

    public static void loadDriver(String dirverClassName) {
        try {
            Class.forName(dirverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void createDataSource(DataSourceSetting sourceSetting) throws SQLException {
        if (StringUtil.isBlankOr(sourceSetting.dataSourceKey(), sourceSetting.url(), sourceSetting.userName())) {
            throw new DataSourceException("数据源创建失败");
        }
        dataSourceMap.put(sourceSetting.dataSourceKey(), new SimpleDataSource(sourceSetting));
        if (DataSourceHolder.getInstance().getDefaultKey()==null){
            DataSourceHolder.getInstance().setDefaultKey(sourceSetting.dataSourceKey());
        }
    }

    private static void reloadSetting() {
        if (settingFile == null) {
            settingFile = new SettingFile("", "jdbc.yml");
        }
        var file = FileUtil.getResourceFile(settingFile.moduleDir(), settingFile.fileName());
        init(FileUtil.readYML(file));
    }
    public static void reloadSetting(String moduleDir, String fileName) {
        settingFile = new SettingFile(moduleDir, fileName);
        reloadSetting();
    }

    public static void clear() {
        if (dataSourceMap.isEmpty()) {
            return;
        }
        dataSourceMap.values().forEach(dataSource -> dataSource.clear());
    }
    public static void init(Optional<StringMap<Object>> optional) {
        optional.ifPresent(dataSourcesMap-> ((List<Map<String, Object>>) dataSourcesMap.get("dataSources")).forEach(map->{
            var sourceSetting = JsonUtil.map2Model(map, DataSourceSetting.class);
            if (StringUtil.isBlank(sourceSetting.driverClassName())) {
                return;
            }
            if (dataSourceMap.containsKey(sourceSetting.dataSourceKey())) {
                return;
            }
            try {
                loadDriver(sourceSetting.driverClassName());
                createDataSource(sourceSetting);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
    }
}
