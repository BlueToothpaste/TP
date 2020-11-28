package com.cjh.tp.sdk.eventbus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @program: tp
 * @description:
 * @author: chenjiehan
 * @create: 2020-10-29 16:54
 **/
public class SubscribePublish<M> {

    //订阅器名称
    private String name;
    //订阅器队列容量
    final int QUEUE_CAPACITY = 20;
    //订阅器存储队列
    private BlockingQueue<Msg> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    //订阅者
    private List<ISubcriber> subcribers = new ArrayList<>();

    public SubscribePublish(String name) {
        this.name = name;
    }

    public void publish(String publisher, M message, boolean isInstantMsg) {
        if (isInstantMsg) {
            update(publisher, message);
            return;
        }
        Msg<M> m = new Msg<M>(publisher, message);
        if (!queue.offer(m)) {
            update();
        }
    }

    public void subcribe(ISubcriber subcriber) {
        subcribers.add(subcriber);
    }

    public void unSubcribe(ISubcriber subcriber) {
        subcribers.remove(subcriber);
    }

    public void update() {
        Msg m = null;
        while ((m = queue.peek()) != null) {
            this.update(m.getPublisher(), (M) m.getMsg());
        }
    }

    public void update(String publisher, M Msg) {
        for (ISubcriber subcriber : subcribers) {
            subcriber.update(publisher, Msg);
        }
    }
}
