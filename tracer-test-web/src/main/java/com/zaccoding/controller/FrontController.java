package com.zaccoding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
@RestController
public class FrontController {
    @GetMapping("/index")
    public String index() {
        return "Test Web APP";
    }
}
