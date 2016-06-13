package ru.mtsbank.mc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mtsbank.mc.repository.MongoHandler;
import ru.mtsbank.mc.repository.RedisHandler;

import javax.annotation.PostConstruct;

@RestController
public class MainController {

    private static Log logger = LogFactory.getLog(MainController.class);

    @Autowired
    MongoHandler mh;

    @Autowired
    RedisHandler rh;

    @PostConstruct
    public void init() {
        logger.info("started");
    }


    @RequestMapping( value={"/echo"}, method = RequestMethod.POST )
    public @ResponseBody String echo(
            @RequestParam(value="key", required=true, defaultValue="none") String key,
            @RequestParam(value="exp", required=true, defaultValue="0") Long exp,
            @RequestBody String body
    )
    {
        logger.debug("ECHO key:["+key + "] exp:["+exp+ "] body:["+body+"] (hello I am echo-test, please check params)");
        return "ECHO key:["+key + "] exp:["+exp+ "] body:["+body+"] (hello I am echo-test, please check params)";
    }


    @RequestMapping( value={"/put"}, method = RequestMethod.POST )
    public @ResponseBody String insert (
            @RequestParam(value="key", required=true, defaultValue="none") String key,
            @RequestParam(value="exp", required=true, defaultValue="0") Long exp,
            @RequestBody String body )
    {
        logger.debug("insert key:"+key+" exp:"+exp);
        String answer = rh.put(key,body,exp);
        mh.put(key,body,exp);
        return answer;
    }

    @RequestMapping( value={"/get"}, method = RequestMethod.POST )
    public @ResponseBody String find(  @RequestParam(value="key", required=true, defaultValue="?") String key)
    {
        logger.debug("request for key:"+key);
        String answer = rh.get(key);
        if(answer.length()==0) {
            logger.info("key not found in redis repository, try in mongo");
            answer = mh.get(key);
            if(answer.length()!=0) {
                rh.put(key,answer,1L);
            }
        }
        logger.debug("result for get key:["+key+"] return value sz:"+answer.length());
        return answer;
    }

    @RequestMapping( value={"/clear"}, method = RequestMethod.POST )
    public @ResponseBody String delAll( )
    {
        logger.info("Redis Cache is empty now!");
        rh.clear();
        mh.clear();
        return "ok";
    }

    @RequestMapping(value = {"/redis2mongo"}, method = RequestMethod.POST)
    public
    @ResponseBody
    String redis2mongo() {
        logger.info("begin copy redis to mongo");
        int rc = mh.redis2mongo();
        logger.info("end of copy redis to mongo sz:" + rc);
        return "sz=" + rc;
    }




}


/*
curl -H "Accept: text/plain;charset=UTF-8" -X POST 'localhost:8888/echo?p2=xxxx&p1=ТЕСТ'


 */

