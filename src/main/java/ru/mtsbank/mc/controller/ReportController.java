package ru.mtsbank.mc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mtsbank.mc.repository.Statistics;

/**
 * Created with IntelliJ IDEA.
 * User: vitaly
 * Date: 11/06/16
 * Time: 20:27
 */
@RestController
public class ReportController {
    @Autowired
    Statistics st;


    @RequestMapping( value={"/stat"}, method = RequestMethod.POST )
    public @ResponseBody String getStat() {

        return st.getResult();
    }


}
