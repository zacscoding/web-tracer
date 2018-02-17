package com.zaccoding.tracer.agent;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
public class Configurer {

    public static Configurer INSTANCE = new Configurer();
    private boolean error = false;
    private ProxyConfigurer proxyConfigurer;

    private Configurer() {
        init();
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ProxyConfigurer getProxyConfigurer() {
        return proxyConfigurer;
    }

    private void init() {
        String configPath = System.getProperty("tracer.config.path");
        System.out.println("@@ configPath : " + configPath);
        LOGGER.println("@@ configPath : " + configPath);
        if (configPath == null || configPath.length() <= 0) {
            setError(true);
            return;
        }
        try {
            int dotIdx = configPath.lastIndexOf('.');
            String format = configPath.substring(dotIdx + 1).toLowerCase();
            if ("json".equals(format)) {
                final Type configType = new TypeToken<ProxyConfigurer>() {
                }.getType();
                Gson gson = new GsonBuilder().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
                JsonReader reader = new JsonReader(new FileReader(new File(configPath)));
                proxyConfigurer = gson.fromJson(reader, configType);
            } else if ("yaml".equals(format)) {
                Yaml yaml = new Yaml();
                proxyConfigurer = (ProxyConfigurer) yaml.load(new FileInputStream(new File(configPath)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            setError(true);
        }
    }
}