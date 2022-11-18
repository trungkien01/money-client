package com.credit.webcredit.config;

import com.credit.webcredit.enums.Role;
import com.credit.webcredit.entity.Users;
import com.credit.webcredit.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AdminAutoConfig implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (usersRepository.getUserByPhone("0123456789")==null){
            Users users = new Users();
            users.setEmail("abc@gmail.com");
            users.setPassword(passwordEncoder.encode("12345678"));
            users.setPhone("0123456789");
            users.setRole(Role.ROLE_PRIEMIUM);
            users.setCheckUser(true);
            usersRepository.save(users);
        }
    }
}
