package ru.mtsbank.mc.repository;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vitaly
 * Date: 05/06/16
 * Time: 13:49
 */

public class Msg {
    String id;
    String m;
    Date expireAt;

    public Msg() {}

    public Msg(String id, String m, Long expireMinute) {
        this.id=id;
        this.m = m;
        Date now = new Date();
        if (expireMinute != 0) {
            now.setTime(now.getTime() + expireMinute * 60 * 1000);
            this.expireAt = now;
        }
        else this.expireAt=null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
}
