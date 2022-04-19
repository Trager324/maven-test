package com.syd.java17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SYD
 * @description
 * @date 2022/3/29
 */
@RestController
public class BController {
    BController() {}
    BController(@Autowired AController aController) {
        this.aController = aController;
    }
    AController aController;

}
