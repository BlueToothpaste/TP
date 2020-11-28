package com.cjh.tp.sdk;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: tp
 * @description:
 * @author: chenjiehan
 * @create: 2020-10-26 17:44
 **/
public class ThreadPoolFactory {
    private static final Iterable<Tag> TAG = Collections.singletonList(Tag.of("thread.pool.name", "ThreadPoolMonitor"));
    private static final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    //监控池
    private static final ConcurrentHashMap<String, ThreadPoolExecutor> monitorPool = new ConcurrentHashMap<>(100);
    private static final Runnable monitor = () -> {
        //这里需要捕获异常,尽管实际上不会产生异常,但是必须预防异常导致调度线程池线程失效的问题
        for (String s :
                monitorPool.keySet()) {
            ThreadPoolExecutor executor = monitorPool.get(s);
            try {
                Metrics.gauge("appId:" + s + "thread.pool.core.size", TAG, executor, ThreadPoolExecutor::getCorePoolSize);
                Metrics.gauge("appId:" + s + "thread.pool.largest.size", TAG, executor, ThreadPoolExecutor::getLargestPoolSize);
                Metrics.gauge("appId:" + s + "thread.pool.max.size", TAG, executor, ThreadPoolExecutor::getMaximumPoolSize);
                Metrics.gauge("appId:" + s + "thread.pool.active.size", TAG, executor, ThreadPoolExecutor::getActiveCount);
                Metrics.gauge("appId:" + s + "thread.pool.thread.count", TAG, executor, ThreadPoolExecutor::getPoolSize);
                // 注意如果阻塞队列使用无界队列这里不能直接取size
                Metrics.gauge("appId:" + s + "thread.pool.queue.size", TAG, executor, e -> e.getQueue().size());
            } catch (Exception e) {
                //ignore
            }
        }
    };

    static {
        scheduledExecutor.scheduleWithFixedDelay(monitor, 0, 5, TimeUnit.SECONDS);
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(String appId) {
        if (monitorPool.get(appId) != null) {
            return monitorPool.get(appId);
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                4,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                r -> {
                    final AtomicInteger counter = new AtomicInteger();
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    thread.setName("thread-pool-" + appId + ":" + counter.getAndIncrement());
                    return thread;
                }, new ThreadPoolExecutor.AbortPolicy());
        monitorPool.put(appId, threadPoolExecutor);
        return threadPoolExecutor;
    }


}
