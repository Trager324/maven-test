/**
 * @author songyide
 * @date 2022/11/27
 */
module org.behappy.common {
    requires com.baomidou.mybatis.plus;
    requires com.baomidou.mybatis.plus.core;
    requires com.baomidou.mybatis.plus.extension;
    requires lombok;
    requires com.alibaba.fastjson2;
    requires swagger.annotations;
    requires spring.web;
    requires spring.core;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.tomcat.embed.core;
    requires org.apache.commons.lang3;
    requires spring.beans;
    requires spring.data.redis;
    requires spring.context;
    requires hutool.all;
    requires spring.aop;
    requires jakarta.validation;
    requires org.slf4j;
    requires spring.boot.autoconfigure;
    requires com.fasterxml.jackson.databind;
    requires com.baomidou.mybatis.plus.annotation;
    requires org.mybatis.spring;
    requires springfox.oas;
    requires springfox.spring.web;
    requires springfox.spi;
    requires springfox.core;
    requires swagger.models;
    requires fastjson2.extension;
    requires org.aspectj.weaver;

    exports org.behappy.common.bean;
    exports org.behappy.common.constant;
    exports org.behappy.common.util;
    exports org.behappy.common.exception;
}