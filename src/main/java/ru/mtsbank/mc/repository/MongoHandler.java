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

/**
 * Created with IntelliJ IDEA.
 * User: vitaly
 * Date: 04/06/16
 * Time: 15:59
 */
@Component
public class MongoHandler {
    private static Log logger = LogFactory.getLog(MongoHandler.class);

    @Autowired
    RedisConnectionFactory connFactory;

    @Autowired
    MsgRepository mongo;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    Statistics st;

    @PostConstruct
    public void init() {
             logger.info("started");
    }

     public String put(String key,String msg, Long exp) {
         logger.debug("put record");
            Msg mm = new Msg(key,msg, exp);
            mongo.save(mm);
            return("ok");
        }

    public String get(String key) {
        logger.debug("get record");
        String rc;
        Msg mm = mongo.findById(key);
        if(mm!=null) {
            st.newInMongo();
            rc= mm.getM();
        }
        else rc = "";
        return rc;
    }

    public void delete(String key) {
        mongo.delete(key);
    }

    public long getCounter() {
        return mongo.count();
    }

    public void clear() {
        mongo.deleteAll();
    }

    public int redis2mongo() {
        int dbsize=0;
        Set<String> keys = redis.keys("*");
        Iterator<String> it = keys.iterator();

        while (it.hasNext()) {
            String key = it.next();
            String val = redis.opsForValue().get(key);
            Msg mout = new Msg(key,val,0L);
            mongo.save(mout);
            dbsize++;
        }
        return dbsize;
    }



}

