package ru.mtsbank.mc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mtsbank.mc.repository.RedisHandler;

import javax.annotation.PostConstruct;

@RestController
public class RedisController {

    private static Log logger = LogFactory.getLog(RedisController.class);

    @Autowired
    RedisHandler rh;

    @PostConstruct
    public void init() {
        logger.info("started");
    }

@RequestMapping( value={"/redis/put"}, method = RequestMethod.POST )
    public @ResponseBody String insert (
            @RequestParam(value="key", required=true, defaultValue="none") String key,
            @RequestParam(value="exp", required=true, defaultValue="0") Long exp,
            @RequestBody String body )
    {
        logger.debug("insert key:"+key+" exp:"+exp);
        String answer = rh.put(key,body,exp);
        return answer;
    }

@RequestMapping( value={"/redis/get"}, method = RequestMethod.POST )
    public @ResponseBody String find(  @RequestParam(value="key", required=true, defaultValue="?") String key)
    {
        logger.debug("request for key:"+key);
        String answer = rh.get(key);
        logger.debug("result for get key:["+key+"] return value sz:"+answer.length());
        return answer;
    }

    @RequestMapping( value={"/redis/clear"}, method = RequestMethod.POST )
    public @ResponseBody String delAll( )
    {
        logger.info("Redis Cache is empty now!");
        rh.clear();
        return "ok";
    }
}


/*
curl -H "Accept: text/plain;charset=UTF-8" -X POST 'localhost:8888/echo?p2=xxxx&p1=ТЕСТ'


 */

