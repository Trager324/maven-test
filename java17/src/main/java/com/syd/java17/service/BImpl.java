package com.syd.java17.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author SYD
 * @description
 * @date 2022/3/29
 */
@Service
public class BImpl implements B {
    @Autowired
    A a;
}
