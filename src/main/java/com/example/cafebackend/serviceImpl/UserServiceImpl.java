package com.example.cafebackend.serviceImpl;

import com.example.cafebackend.constents.CafeConstent;
import com.example.cafebackend.dao.UserDao;
import com.example.cafebackend.model.User;
import com.example.cafebackend.service.UserService;
import com.example.cafebackend.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("inside signup" + requestMap);
        try{
        if(validateSignupMap(requestMap)){
            User user = userDao.findByEmailId(requestMap.get("email"));
            if(Objects.isNull(user)){
                userDao.save(getUserFromMap(requestMap));
                return CafeUtils.getResponseEntity("Sign up Success.",HttpStatus.OK);
            }else{
                return CafeUtils.getResponseEntity("Email already Exist",HttpStatus.BAD_REQUEST);
            }
        }else{
            return CafeUtils.getResponseEntity(CafeConstent.VALIDATION_WENT_WRONG, HttpStatus.BAD_REQUEST);
        }}catch (Exception err){
            err.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstent.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Boolean validateSignupMap(Map<String,String> requestMap){
        if(requestMap.containsKey("name") &&
        requestMap.containsKey("contactNumber") &&
        requestMap.containsKey("email") &&
        requestMap.containsKey("password") )
            return true;
        else
            return false;
    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumebr(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");

        return user;
    }
}
