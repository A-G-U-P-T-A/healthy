package com.habitbuilder.HabitBuilder.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.habitbuilder.HabitBuilder.Objects.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.xml.bind.DatatypeConverter;


@Service
public class UserService {

    @Autowired
    MongoService mongoService;

    public String createUser(User user) {
        ObjectId objectId = new ObjectId();
        mongoService.createEntry("user", createOrderDocument(user, objectId));
        return objectId.toString();
    }

    private Document createOrderDocument(User user, ObjectId objectId) {
        Document document = new Document();
        document.append("_id", objectId);
        document.append("password", user.getPassword());
        document.append("username", user.getUsername());
        document.append("stars", user.getStars());
        return document;
    }
}
