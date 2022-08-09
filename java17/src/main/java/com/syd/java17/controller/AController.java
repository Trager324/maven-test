package com.syd.java17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syd
 * @date 2022/3/29
 */
@RestController
@RequestMapping("/a")
public class AController {
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
