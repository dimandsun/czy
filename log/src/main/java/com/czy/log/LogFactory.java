package com.czy.log;

import com.czy.util.ThreadUtil;
import com.czy.util.io.FileUtil;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.SettingFile;
import com.czy.util.text.StringUtil;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenzy
 * @date 2020-08-06
 */
public class LogFactory {
    private static ThreadPoolExecutor executor;
    private static ReentrantLock lock = new ReentrantLock();

    protected static ExecutorService executor() {
        lock.lock();
        /*锁确保不会生成多个线程池*/
        if (executor == null) {
            var threadNum = ThreadUtil.getThreadNum();
            executor = new ThreadPoolExecutor(threadNum, threadNum * 2, 10, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(threadNum), new LogThreadFactory(), new LogHandler());
        }
        lock.unlock();
        return executor;
    }


    private static class LogThreadFactory implements ThreadFactory {
        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "日志线程-" + mThreadNum.getAndIncrement());
        }
    }

    private static class LogHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.err.println(r.toString() + " 任务被忽略了！");
        }
    }

    public static void close() {
        if (executor != null) {
            executor.shutdown();
            while (!executor.isTerminated()) {
                /*任务未完成，则等待*/
            }
        }
        LogInfo.instance().close();
    }

    private LogFactory() {
    }

    public static Log getLog(String logName) {
        return LogInfo.instance().get(logName);
    }

    public static Log getLog(Class c) {
        return getLog(c.getSimpleName());
    }
    public static void initLog(){
        initLog(new SettingFile(null,"log.yml"));
    }
    public static void initLog(SettingFile settingFile) {
        Objects.requireNonNull(settingFile);
        var file = FileUtil.getResourceFile(settingFile.moduleDir(), settingFile.fileName());
        FileUtil.readYML(file).ifPresent(map -> {
            Map<String, Object> temp = (Map<String, Object>) map.get("logInfo");
            LogInfo.instance().usePool(StringUtil.getBoolean(temp.get("usePool"), false));
            ((List<Map<String, Object>>) temp.get("logs")).forEach(logMap -> {
                var logSetting = JsonUtil.map2Model(logMap, LogSetting.class);
                LogInfo.instance().add(logSetting.logName(), new Log(logSetting));
            });
        });

    }

}
