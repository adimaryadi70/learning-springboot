package com.learning.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.learning.model.authentication.AuthenticationModel;
import com.learning.tools.Utility;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    Utility utility = new Utility();


    @ResponseBody
    @RequestMapping(value = "/sign",method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> authentication(@RequestBody AuthenticationModel userLogin) {
        try {
            ResponseEntity<Map<String, Object>> response = new ResponseEntity<>(utility.responseSuccess(userLogin),HttpStatus.OK);
            return response;
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(utility.responseError(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
