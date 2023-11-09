package com.usermanagment.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserFacadeConfig {

    @Bean
    UserFacade userFacade(UserRepository repository){
        UserValidationService service = new UserDataValidator(repository);
        return new UserFacade(repository, service, new BCryptPasswordEncoder());
    }
}
