package com.habitbuilder.HabitBuilder.Validations;

import com.habitbuilder.HabitBuilder.Objects.User;
import com.habitbuilder.HabitBuilder.Services.MongoService;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserValidation {

    @Autowired
    MongoService mongoService;

    public List<String> validate(User user) {
        List<String> failedValidations = new ArrayList<>();
        if(user.getPassword()==null||user.getPassword().trim().length()==0)
            failedValidations.add("password is null");
        if(user.getUsername()==null||user.getUsername().trim().length()==0)
            failedValidations.add("username is null");
        Bson filter = createUsernameFilter(user);
        long count = mongoService.getCount("user", filter);
        if(count>0)
            failedValidations.add("choose a different username");
        return failedValidations;
    }

    private Bson createUsernameFilter(User user) {
        Bson filter = Filters.eq("username", user.getUsername());
        return filter;
    }
}
