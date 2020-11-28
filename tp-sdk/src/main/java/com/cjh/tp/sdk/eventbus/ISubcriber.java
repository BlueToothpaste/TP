package com.cjh.tp.sdk.eventbus;

/**
 * @program: tp
 * @description:
 * @author: chenjiehan
 * @create: 2020-10-29 16:53
 **/
public interface ISubcriber<M> {

    public void subcribe(SubscribePublish subscribePublish);

    public void unSubcribe(SubscribePublish subscribePublish);

    public void update(String publisher, M message);
}
