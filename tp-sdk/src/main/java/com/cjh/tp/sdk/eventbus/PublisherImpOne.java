package com.cjh.tp.sdk.eventbus;

/**
 * @program: tp
 * @description:
 * @author: chenjiehan
 * @create: 2020-10-29 16:57
 **/
public class PublisherImpOne<M> implements IPublisher<M> {
    private String name;

    public PublisherImpOne(String name) {
        super();
        this.name = name;
    }

    @Override
    public void publish(SubscribePublish subscribePublish, M message, Boolean isInstantMsg) {
        subscribePublish.publish(this.name, message, isInstantMsg);
    }
}
