# Web Trace !!
Check Methods by using javaagent ... working....

- called stack
- param & return values
- exceptions (working)
- excution time (working)

# How to start

1. add javaagent in vm arguments  

```
-javaagent:...jarpath../tracer-agent.jar
```  

2. add agent config path  

```
-Dtracer.config.path=..config path../webtracer-config.json
```  

3. config sample  

```
{
  "class-proxies": [
    {
      // check class name
      "class-name": "com/zaccoding/controller/FrontController",
      // check class type [startsWith, endsWith, equals, contains]
      "invoke-type": "equals",
      // return class-name.invoke(origin-class-name);
      "is-invoker": true,
      "method-proxies": [
        {
          "method-name": "*",
          "invoke-type": "",
          "is-invoker": false,
          "display-param-and-return": true,
          "display-execution-time": true
        }
      ]
    }
  ]
}
```  

4. result  

```
+--org/web/controller/FrontController::index()Ljava/lang/String;[0ms] : Test Web APP
|  +--org/web/service/AService::methodA(Ljava/lang/String;I)Ljava/lang/String;[0ms] : Test10
|   -- 1 : Test
|   -- 2 : 10
|  |  +--org/web/service/BService::methodB(Ljava/lang/String;I)Ljava/lang/String;[0ms] : Test10
|  |   -- 1 : Test
|  |   -- 2 : 10
|  |  |  +--org/web/service/CService::methodC(Ljava/lang/String;)Ljava/lang/String;[0ms] : Test
|  |  |   -- 1 : Test
|  |  |  +--org/web/service/DService::methodD(I)I[0ms] : 10
|  |  |   -- 1 : 10
|  +--org/web/service/EService::methodE()V[0ms] : void
```

## ref  

- https://github.com/scouter-project/scouter/tree/master/scouter.agent.java  
