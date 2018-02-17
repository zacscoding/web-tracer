package org.web.service;

import org.springframework.stereotype.Service;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
@Service
public class TempService {

    public String append(String name, int age) {
        return name + age;
    }
}
