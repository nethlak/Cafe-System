package com.example.cafebackend.JWT;

import com.example.cafebackend.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUsersDetailsSevice implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private com.example.cafebackend.model.User userDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //we parse the email as user name
        log.info("Inside loadUseByName {}",username );
        userDetails = userDao.findByEmailId(username);
        if(!Objects.isNull(userDetails))
            return new User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>());
        else
            throw  new UsernameNotFoundException("User Not Found !!");
    }

    public com.example.cafebackend.model.User getUserDetails(){
        return userDetails;
    }
}
