package ru.mtsbank.mc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

    /**
     * Created with IntelliJ IDEA.
     * User: vitaly
     * Date: 28/05/16
     * Time: 20:59
     */
    public interface MsgRepository extends MongoRepository<Msg, String> {
        public Msg findById(String name);
    }
