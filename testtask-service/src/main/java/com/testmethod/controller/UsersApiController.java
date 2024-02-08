package com.testmethod.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testmethod.common.ApiException;
import com.testmethod.module.UserModule;
import com.testmethod.api.UsersApi;
import com.testmethod.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-02-02T17:21:08.572357999+02:00[EET]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    @Autowired
    private UserModule userModule;
    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<User>> getUsers() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<User>>(userModule.getUsers(), HttpStatus.OK);
            } catch (ApiException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

}

