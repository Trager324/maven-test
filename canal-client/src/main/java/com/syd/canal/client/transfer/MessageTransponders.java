package com.syd.canal.client.transfer;

/**
 * @author jigua
 * @date 2018/3/23
 */
public class MessageTransponders {

    public static TransponderFactory defaultMessageTransponder() {
        return new DefaultTransponderFactory();
    }

}

