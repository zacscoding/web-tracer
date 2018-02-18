package org.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
@Service
public class AService {

    @Autowired
    BService bService;

    public String methodA(String str, int intVal) {
        return bService.methodB(str, intVal);
    }

}
