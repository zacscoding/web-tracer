package org.web.controller;

import org.web.service.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
@RestController
public class FrontController {

    @Autowired
    private TempService tempService;

    @GetMapping("/index")
    public String index() {
        tempService.append("Test", 10);
        return "Test Web APP";
    }
}
