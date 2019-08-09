package com.ircnet.service.clis;

import com.ircnet.service.clis.persistence.PersistenceService;
import com.ircnet.service.library.IRCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private IRCService ircService;

    @Autowired
    private PersistenceService persistenceService;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.setLogStartupInfo(false);
        springApplication.run(args);
    }

    @Override
    public void run(String... args) {
        persistenceService.loadChannels();
        ircService.start();
    }
}
