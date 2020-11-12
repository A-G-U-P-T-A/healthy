package com.habitbuilder.HabitBuilder.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.habitbuilder.HabitBuilder.Objects.User;
import com.habitbuilder.HabitBuilder.Services.ObjectMapperService;
import com.habitbuilder.HabitBuilder.Services.UserService;
import com.habitbuilder.HabitBuilder.Validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserValidation userValidation;

    @CrossOrigin
    @PostMapping(value = "/createuser")
    public @ResponseBody Object createUser(@RequestBody User user) {
        List<String> validation = userValidation.validateUserCreate(user);
        if(validation.size()!=0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation);
        }
        String userId = userService.createUser(user);
        ObjectNode objectNode = ObjectMapperService.getObjectMapper().createObjectNode();
        objectNode.put("userId", userId);
        return objectNode;
    }

    @CrossOrigin
    @PostMapping(value = "/loginuser")
    public @ResponseBody Object loginUser(@RequestBody User user) {
        List<String> validation = userValidation.validateUserLogin(user);
        if(validation.size()!=0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation);
        }
        String userId = userService.loginUser(user);
        if(userId == null) {
            validation.add("Could not login user does not exist");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(validation);
        }
        ObjectNode objectNode = ObjectMapperService.getObjectMapper().createObjectNode();
        objectNode.put("userId", userId);
        return objectNode;
    }


}
