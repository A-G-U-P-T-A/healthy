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
        List<String> validation = userValidation.validate(user);
        if(validation.size()!=0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation);
        }
        String userId = userService.createUser(user);
        ObjectNode objectNode = ObjectMapperService.getObjectMapper().createObjectNode();
        objectNode.put("userId", userId);
        return objectNode;
    }


}
