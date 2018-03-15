package com.whyuan.$6utils.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class ZKHandle {
    private final static Logger logger = LoggerFactory.getLogger(ZKHandle.class);
    private ZooKeeper zoo;
    private String zkhost;
    final CountDownLatch downLatch = new CountDownLatch(1);


    public ZKHandle(String connectString, int sessionTimeout) throws IOException, InterruptedException {
        this.zoo = new ZooKeeper(connectString, sessionTimeout, new Default());
        this.zkhost = connectString;
        downLatch.await();
    }

    public void create(String path,String data,CreateMode createMode) {
        try {
            zoo.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
        } catch (KeeperException|InterruptedException e) {
            logger.error("", e);
        }
    }

    public List<String> getChilds(String path) {
        try {
            return zoo.getChildren(path, true);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getChildsData(String path, Watcher watcher) {
        Map<String, String> files = new HashMap<>();
        try {
            List<String> childrens = zoo.getChildren(path, true);
            for (String children : childrens) {
                byte[] data = zoo.getData((path.length() > 1 ? path : "") + "/" + children, watcher, new Stat());
                files.put(children, new String(data));
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return files;
    }

    public byte[] getData(String path, Watcher watcher) throws KeeperException, InterruptedException {
        return zoo.getData(path, watcher, new Stat());
    }
    public byte[] getData(String path, Stat stat) throws KeeperException, InterruptedException {
        return zoo.getData(path,false,stat);
    }

    public void trace(String path, Action action) {
        try {
            Trace trace = new Trace();
            trace.setAction(action);
            zoo.exists(path, trace);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    class Default implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            if (Event.KeeperState.SyncConnected == event.getState()) {
                logger.info("Zookeeper host={} Connection successful!", zkhost);
                downLatch.countDown();
            }
        }
    }

    class Trace implements Watcher {
        private Action action;

        public void setAction(Action action) {
            this.action = action;
        }

        @Override
        public void process(WatchedEvent event) {
            logger.debug("trace path={}, type={}, state={}", event.getPath(), event.getType(), event.getState());
            if (Event.KeeperState.SyncConnected == event.getState()) {
                if (event.getPath() != null) {
                    try {
                        Stat stat = zoo.exists(event.getPath(), this);

                        if (action != null) {
                            action.call(event, stat);
                        }

                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static interface Action {
        public void call(WatchedEvent event, Stat stat);
    }
}
