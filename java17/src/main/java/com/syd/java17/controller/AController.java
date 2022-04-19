package com.syd.java17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SYD
 * @description
 * @date 2022/3/29
 */
@RestController
public class AController {
    AController() {}
    AController(@Autowired BController bController) {
        this.bController = bController;
    }
    BController bController;
}
