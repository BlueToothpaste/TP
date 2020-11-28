package com.cjh.tp.manage.service.notify.source;

import com.cjh.tp.manage.service.notify.entity.Event;
import com.cjh.tp.manage.service.notify.listener.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: tp
 * @description: 配置文件更新 事件源
 * @author: chenjiehan
 * @create: 2020-11-27 16:13
 **/
public class ConfigDataChangeSource implements Source {

    protected final List<Subscriber> subscribers = new ArrayList<>();

    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public boolean publish(Event event) {
        receiveEvent(event);
        return true;
    }

    @Override
    public void notifySubscriber(Subscriber subscriber, Event event) {
        final Runnable job = () -> subscriber.onEvent(event);
        job.run();
    }

    void receiveEvent(Event event) {
        for (Subscriber subscriber : subscribers) {
            notifySubscriber(subscriber, event);
        }
    }
}
