package ru.mtsbank.mc.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: vitaly
 * Date: 04/06/16
 * Time: 15:59
 */
@Component
public class RedisHandler {
    private static Log logger = LogFactory.getLog(RedisHandler.class);

    @Autowired
    RedisConnectionFactory connFactory;


    @Autowired
    Statistics st;


    @Autowired
    private StringRedisTemplate redis;

    @PostConstruct
    public void init() {
        redis.setConnectionFactory(connFactory);
        logger.info("started");
    }

    public String put(String key, String val, Long exp) {
        logger.debug("put record");
        if (exp.equals(0L))
            redis.opsForValue().set(key, val);
        else
            redis.opsForValue().set(key, val, exp, TimeUnit.MINUTES);
        return "ok";
    }

    public String get(String key) {
        logger.debug("get record");
        String rc =redis.opsForValue().get(key);
        if(rc!=null) {
            st.newInRedis();
        }
        else {
            rc="";
            st.newInNotFound();
        }
//        redis.o
        return rc;
    }

    public void delete(String key) {
        redis.opsForValue().getOperations().delete(key);
    }

    public void clear() {
        Set<String> keys = redis.keys("*");
        Iterator<String> it = keys.iterator();

        while (it.hasNext()) {
            String key = it.next();
            delete(key);
        }
    }


}
