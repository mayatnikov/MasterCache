package ru.mtsbank.mc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mtsbank.mc.repository.MongoHandler;

import javax.annotation.PostConstruct;

@RestController
public class MongoController {

    private static Log logger = LogFactory.getLog(MongoController.class);

    @Autowired
    MongoHandler mh;

    @PostConstruct
    public void init() {
        logger.info("started");
    }


    @RequestMapping(value = {"/mongo/put"}, method = RequestMethod.POST)
    public @ResponseBody  String mput(
            @RequestParam(value = "key", required = false) String key,
            @RequestBody String val,
            @RequestParam(value = "exp", required = false, defaultValue="0") Long exp

    ) {
        String rc;
        if (key != null && val != null) {
              logger.debug("insert key:" + key + " value:" + val);
            rc= mh.put(key, val, exp);
        }
        else rc="err key:"+key+" val:";
        return rc;
    }

    @RequestMapping(value = {"/mongo/get"}, method = RequestMethod.POST)
    public
    @ResponseBody
    String mget(@RequestParam(value = "key", required = true, defaultValue = "none") String key) {
        logger.debug("start search for key:" + key);
        String answer = mh.get(key);
        logger.debug("result for get key:["+key+"] return value sz:"+answer.length());
        return answer;
    }

    @RequestMapping(value = {"/mongo/clear"}, method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteAll() {
        logger.info("start clean");
        mh.clear();
        logger.info("Mongo Cache is empty now!");
        return "ok";
    }

}


/*
curl -H "Accept: text/plain;charset=UTF-8" -X POST 'localhost:8888/echo?=xxxx&p1=ТЕСТ'


 */

