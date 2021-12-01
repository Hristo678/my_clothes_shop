package com.example.clothes_shop.web.schedulers;

import com.example.clothes_shop.models.entities.UserEntity;
import com.example.clothes_shop.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class UserScheduler {

    private static final Logger logger = Logger.getLogger(UserScheduler.class.getName());
    private UserService userService;

    public UserScheduler(UserService userService) {
        this.userService = userService;
    }
    //Set the views of all users to 0 every day!
    @Scheduled(fixedDelay = 86400000, initialDelay = 10000)
    public void cleanUserViews(){
        logger.info("schedule");
        List<UserEntity> allUsers = userService.findAll();
        for (UserEntity user : allUsers) {
            user.setViewsCount(0);
            userService.saveUser(user);
        }

    }
}
