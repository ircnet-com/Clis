package com.ircnet.service.clis;

import com.ircnet.library.common.IRCTaskService;
import com.ircnet.library.service.IRCServiceTask;
import com.ircnet.service.clis.persistence.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private IRCTaskService ircTaskService;

    @Autowired
    private IRCServiceTask ircServiceTask;

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

        Thread ircServiceThread = new Thread() {
            @Override
            public void run() {
                ircTaskService.run(ircServiceTask);
            }
        };

        ircServiceThread.start();
    }
}
