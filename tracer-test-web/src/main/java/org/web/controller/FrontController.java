package org.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web.service.AService;
import org.web.service.EService;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
@RestController
public class FrontController {

    @Autowired
    private AService aService;
    @Autowired
    private EService eService;

    @GetMapping("/index")
    public String index() {
        aService.methodA("Test", 10);
        eService.methodE();
        return "Test Web APP";
    }
}
