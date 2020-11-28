package com.cjh.tp.manage.service.notify.source;

import com.cjh.tp.manage.service.notify.entity.Event;
import com.cjh.tp.manage.service.notify.listener.Subscriber;

/**
 * @program: tp
 * @description: 通用事件源
 * @author: chenjiehan
 * @create: 2020-11-27 16:07
 **/
public interface Source {

    /**
     * 添加订阅者
     *
     * @param subscriber
     */
    void addSubscriber(Subscriber subscriber);

    /**
     * 剔除订阅者
     *
     * @param subscriber
     */
    void removeSubscriber(Subscriber subscriber);

    /**
     * 发布事件
     *
     * @param event
     * @return
     */
    boolean publish(Event event);

    /**
     * 通知订阅者
     *
     * @param subscriber
     * @param event
     */
    void notifySubscriber(Subscriber subscriber, Event event);


}
