package com.czy.httpcontainer;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public class BGThread {
    private Thread thread = null;
    private Boolean threadDone = false;
    private Container parent;

    private int delay = -1;

    public BGThread(Container parent, Thread thread) {
        this.parent = parent;
        this.thread = thread;
    }

    protected static class ContainerBackgroundProcessor implements Runnable {
        @Override
        public void run() {
            /*while (!threadDone) {
                try {
                    Thread.sleep(delay * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!threadDone) {
                    ClassLoader cl = parent.getLoader() == null ? Thread.currentThread().getContextClassLoader() : parent.getLoader().getClassLoader();
                    processChildren(parent, cl);
                }
            }*/
        }
        protected void processChildren(Container container, ClassLoader cl) {
            /*try {
                if (container.getLoader() != null) {
                    Thread.currentThread().setContextClassLoader
                            (container.getLoader().getClassLoader());
                }
                container.backgroundProcess();
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                log.error("Exception invoking periodic operation: ", t);
            } finally {
                Thread.currentThread().setContextClassLoader(cl);
            }
            Container[] children = container.findChildren();
            for (int i = 0; i < children.length; i++) {
                if (children[i].getBackgroundProcessorDelay() <= 0) {
                    processChildren(children[i], cl);
                }
            }*/
        }
    }
}
