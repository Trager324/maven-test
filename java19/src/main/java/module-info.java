/**
 * @author songyide
 * @date 2022/10/30
 */
module com.syd.java {
    requires jdk.incubator.concurrent;
    requires jdk.incubator.vector;
    requires lombok;
    requires java.net.http;
    requires java.logging;
    requires com.alibaba.fastjson2;
    requires jdk.dynalink;
    requires druid.spring.boot.starter;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires jmh.core;
    requires com.syd.common;

    exports com.syd.java19.incubator;
    exports com.syd.java19;
}