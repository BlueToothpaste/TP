package com.cjh.tp.manage.service.notify;


import com.cjh.tp.manage.service.notify.entity.ConfigDataChangeEvent;
import com.cjh.tp.manage.service.notify.listener.Subscriber;
import com.cjh.tp.manage.service.notify.source.ConfigDataChangeSource;

/**
 * @program: tp
 * @description: 工具类，用来给 订阅者订阅事件，发布者发布事件
 * @author: chenjiehan
 * @create: 2020-11-27 16:05
 **/
public class NotifyCenter {

    /**
     * 确保是单例
     */
    private static final NotifyCenter INSTANCE = new NotifyCenter();

    /**
     * 事件源
     */
    private ConfigDataChangeSource eventPublisher = new ConfigDataChangeSource();

    /**
     * 事件源发布事件-》代表着事件发生了，告诉订阅者要做点啥
     *
     * @param event
     * @return
     */
    public static boolean publishEvent(ConfigDataChangeEvent event) {
        return INSTANCE.eventPublisher.publish(event);
    }

    /**
     * 注册一个订阅者到这个事件
     *
     * @param consumer
     */
    public static void registerSubscriber(final Subscriber consumer) {
        INSTANCE.eventPublisher.addSubscriber(consumer);
    }
}
