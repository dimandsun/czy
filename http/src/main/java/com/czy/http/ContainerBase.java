package com.czy.http;

import com.czy.http.exception.LifecycleException;
import com.czy.log.Log;
import com.czy.log.LogFactory;
import com.czy.util.model.StringMap;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenzy
 * @date 2020-07-25
 */
public abstract class ContainerBase implements Container{
    private static Log log = LogFactory.getLog(ContainerBase.class);
    private ThreadPoolExecutor executor;
    private int threadNum = 1;
    private String name = null;
    private StringMap children = new StringMap<Container>();
    private Container parent;
    private BGThread bgThread;
    private PropertyChangeSupport support;
    private List<ContainerListener> listeners = new CopyOnWriteArrayList<>();
    protected Loader loader = null;
    private void addChild(Container child) {
        log.debug("Add child " + child + " " + this);
        synchronized(children) {
            if (children.containsKey(child.getName()))
                throw new IllegalArgumentException("addChild:  Child name '" + child.getName() + "' is not unique");
            child.setParent(this);
            children.add(child.getName(), child);
        }
        fireContainerEvent(ADD_CHILD_EVENT, child);
    }
    @Override public void fireContainerEvent(String type, Object data) {
        if (listeners.isEmpty()){
            return;
        }
        var event = new ContainerEvent(this, type, data);
        listeners.forEach(listener-> listener.containerEvent(event));
    }
    @Override
    public void setParent(Container container) {
        Container oldParent = this.parent;
        this.parent = container;
        support.firePropertyChange("parent", oldParent, this.parent);

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getThreadNum() {
        return threadNum;
    }
    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
        if (executor != null) {
            int newThreadNum = getThreadNumInternal();
            executor.setMaximumPoolSize(newThreadNum);
            executor.setCorePoolSize(newThreadNum);
        }
    }
    private int getThreadNumInternal() {
        int result = getThreadNum();
        if (result > 0) {
            return result;
        }
        result = Runtime.getRuntime().availableProcessors() + result;
        if (result < 1) {
            result = 1;
        }
        return result;
    }

    public void createPool(){
        int threadNum = getThreadNumInternal();
        executor = new ThreadPoolExecutor(
                threadNum,
                threadNum, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue(),
                new StartStopThreadFactory(getName() + "-startStop-"));
        executor.allowCoreThreadTimeOut(true);//允许超时
    }
    public void startInternal() throws LifecycleException {
        var results = new ArrayList<Future<Void>>();
        findChildren().forEach(container -> results.add(executor.submit(new StartChild(container))));
        boolean fail = false;
        for (Future<Void> result : results) {
            try {
                result.get();
            } catch (Exception e) {
                log.error("containerBase.threadedStartFailed", e);
                fail = true;
            }
        }
        if (fail) {
            throw new LifecycleException("containerBase.threadedStartFailed");
        }
        if (bgThread==null){
            bgThread=new BGThread(this,new Thread(() -> {

            }));
        }


    }
    @Override
    public Loader getLoader() {
        if (loader != null){
            return loader;
        }
        if (parent != null){
            return parent.getLoader();
        }
        return null;
    }
    @Override
    public List<Container> findChildren() {
        synchronized (children) {
            return List.of((Container[])children.values().toArray());
        }

    }

    public void destroy(){
        if (executor != null) {
            executor.shutdownNow();
        }
    }
    private static class StartChild implements Callable<Void> {
        private Container child;
        public StartChild(Container child) {
            this.child = child;
        }
        @Override
        public Void call() throws LifecycleException {
            child.start();
            return null;
        }
    }
    private static class StartStopThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public StartStopThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(group, r, namePrefix + threadNumber.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    }
}
