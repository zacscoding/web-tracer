package org.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
@Service
public class BService {

    @Autowired
    private CService cService;
    @Autowired
    private DService dService;

    public String methodB(String str, int val) {
        return cService.methodC(str) + dService.methodD(val);
    }

}
