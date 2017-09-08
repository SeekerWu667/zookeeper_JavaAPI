package zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @Description: Modified By:
 * Created by xueqin on 17/9/8.
 */
public class CreateSessionTest implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new CreateSessionTest());
        System.out.println(zk.getState());
        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            System.out.println("zk session established.");
        }

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("  接受到事件："+watchedEvent);
        if (KeeperState.SyncConnected == watchedEvent.getState()) {
           connectedSemaphore.countDown();
        }
    }
}
