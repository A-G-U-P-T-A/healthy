package com.habitbuilder.HabitBuilder.Services;

import com.habitbuilder.HabitBuilder.Objects.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    MongoService mongoService;

    public String createUser(User user) {
        ObjectId objectId = new ObjectId();
        mongoService.createEntry("user", createOrderDocument(user, objectId));
        return objectId.toString();
    }

    public String loginUser(User user) {
        FindIterable<Document>documents = mongoService.getData("user", loginUserFilter(user));
        String id = null;
        Document userInfo = null;
        if(documents==null)
            return id;
        for(Document document: documents) {
            userInfo = document;
            break;
        }
        if(userInfo==null)
            return id;
        id = userInfo.getObjectId("_id").toString();
        return id;
    }

    private Bson loginUserFilter(User user) {
        Bson filter = Filters.and(Filters.eq("password", user.getPassword()), Filters.eq("username", user.getUsername()));
        return filter;
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
