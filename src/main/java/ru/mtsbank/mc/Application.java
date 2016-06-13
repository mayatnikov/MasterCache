package ru.mtsbank.mc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ComponentScan
@EnableAutoConfiguration
@Configuration
public class Application {
    private static Log logger = LogFactory.getLog(Application.class);


    @Value("${spring.profiles}")
    String profileName;   // проверка от куда взяли конфиг

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

@PostConstruct
  public void init() {
           logger.info("Start configuration from: ["+profileName+"] section");
}

}
