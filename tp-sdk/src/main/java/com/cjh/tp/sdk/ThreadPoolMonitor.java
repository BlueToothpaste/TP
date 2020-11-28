package com.cjh.tp.sdk;


import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.InitializingBean;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: tp
 * @description:
 * @author: chenjiehan
 * @create: 2020-10-26 17:46
 **/

public class ThreadPoolMonitor{


    private static final String EXECUTOR_NAME = "ThreadPoolMonitorSample";
    private static final Iterable<Tag> TAG = Collections.singletonList(Tag.of("thread.pool.name", EXECUTOR_NAME));
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();


    LinkedBlockingQueue<ThreadPoolExecutor> arrayBlockingQueue;

    public LinkedBlockingQueue<ThreadPoolExecutor> getArrayBlockingQueue() {
        return arrayBlockingQueue;
    }

    public void setArrayBlockingQueue(LinkedBlockingQueue<ThreadPoolExecutor> arrayBlockingQueue) {
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    public void addThreadPoolExecutor(ThreadPoolExecutor executor){
        arrayBlockingQueue.add(executor);
    }

    private Runnable monitor = () -> {
        //这里需要捕获异常,尽管实际上不会产生异常,但是必须预防异常导致调度线程池线程失效的问题
        for (ThreadPoolExecutor executor :
                arrayBlockingQueue) {
            try {
                Metrics.gauge("thread.pool.core.size", TAG, executor, ThreadPoolExecutor::getCorePoolSize);
                Metrics.gauge("thread.pool.largest.size", TAG, executor, ThreadPoolExecutor::getLargestPoolSize);
                Metrics.gauge("thread.pool.max.size", TAG, executor, ThreadPoolExecutor::getMaximumPoolSize);
                Metrics.gauge("thread.pool.active.size", TAG, executor, ThreadPoolExecutor::getActiveCount);
                Metrics.gauge("thread.pool.thread.count", TAG, executor, ThreadPoolExecutor::getPoolSize);
                // 注意如果阻塞队列使用无界队列这里不能直接取size
                Metrics.gauge("thread.pool.queue.size", TAG, executor, e -> e.getQueue().size());
            } catch (Exception e) {
                //ignore
            }
        }
    };



}
