package com.cjh.tp.sdk.eventbus;

/**
 * @program: tp
 * @description:
 * @author: chenjiehan
 * @create: 2020-10-29 16:52
 **/
public interface IPublisher<M> {
    public void publish(SubscribePublish subscribePublish, M message, Boolean isInstantMsg);
}
