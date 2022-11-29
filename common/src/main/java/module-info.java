/**
 * @author songyide
 * @date 2022/11/27
 */
module com.syd.common {
    requires com.baomidou.mybatis.plus;
    requires com.baomidou.mybatis.plus.core;
    requires com.baomidou.mybatis.plus.extension;
    requires lombok;
    requires com.alibaba.fastjson2;
    requires swagger.annotations;
    requires spring.web;
    requires spring.core;
    requires java.validation;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.tomcat.embed.core;
    requires org.apache.commons.lang3;
    requires spring.beans;
    requires spring.data.redis;
    requires spring.context;
    requires hutool.all;
    requires spring.aop;

    exports com.syd.common.constant;
    exports com.syd.common.util;
    exports com.syd.common.exception;
}