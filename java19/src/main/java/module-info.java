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
    requires spring.beans;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires jmh.core;
    requires com.syd.common;
    requires com.baomidou.mybatis.plus.core;
    requires swagger.annotations;
    requires spring.data.commons;
    requires org.slf4j;

    exports com.syd.java19.incubator;
    exports com.syd.java19;
}