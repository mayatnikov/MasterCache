package ru.mtsbank.mc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: vitaly
 * Date: 11/06/16
 * Time: 20:24
 */
@Component
public class Statistics {
    @Autowired
            MongoHandler mh;
    @Autowired
            RedisHandler rh;

    Long requests;
    Long inRedis;
    Long inMongo;
    Long notFound;

    @PostConstruct
    public void clear() {
        this.requests = 0L;
        this.inMongo = 0L;
        this.inRedis = 0L;
        this.notFound = 0L;
    }

    public Long newInRedis() {
        inRedis++;
        requests++;
        return inRedis;
    }

    public Long newInMongo() {
        inMongo++;
        requests++;
        return inMongo;
    }

    public Long newInNotFound() {
        notFound++;
        requests++;
        return notFound;
    }


    public Long getRequests() {
        return requests;
    }

    public void setRequests(Long requests) {
        this.requests = requests;
    }

    public Long getInRedis() {
        return inRedis;
    }

    public void setInRedis(Long inRedis) {
        this.inRedis = inRedis;
    }

    public Long getInMongo() {
        return inMongo;
    }

    public void setInMongo(Long inMongo) {
        this.inMongo = inMongo;
    }

    public Long getNotFound() {
        return notFound;
    }

    public void setNotFound(Long notFound) {
        this.notFound = notFound;
    }

    public String getResult() {

        long mongoRecords = mh.getCounter();


        String rc= "Counters: req="+ requests+
                " redis="+ inRedis+
                " mongo="+inMongo+
                " notFound="+notFound+
                " Mongo records="+mongoRecords;


        return rc;

    }
}
