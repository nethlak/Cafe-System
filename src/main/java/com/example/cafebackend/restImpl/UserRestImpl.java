package com.example.cafebackend.restImpl;

import com.example.cafebackend.constents.CafeConstent;
import com.example.cafebackend.rest.UserRest;
import com.example.cafebackend.service.UserService;
import com.example.cafebackend.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception err) {
            err.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstent.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
