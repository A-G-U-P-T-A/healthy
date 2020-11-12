package com.habitbuilder.HabitBuilder.Services;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

@Service
public class MongoService {

    public MongoClient mongoClient;
    private static final String dbName = "healthy";

    MongoService() {
        try {
            mongoClient = MongoClients.create(
                    "mongodb+srv://newuser:newuser@cluster0.7l3am.mongodb.net/healthy?retryWrites=true&w=majority");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEntry(String collectionName, Document entry) {
        mongoClient.getDatabase(dbName).getCollection(collectionName).insertOne(entry);
    }

    public long getCount(String collectionName, Bson filter) {
        return mongoClient.getDatabase(dbName).getCollection(collectionName).countDocuments(filter);
    }

    public FindIterable<Document> getData(String collectionName, Bson filter) {
        return mongoClient.getDatabase(dbName).getCollection(collectionName).find(filter);
    }

    public UpdateResult updateData(String collectionName, Bson filter, Bson update) {
        return mongoClient.getDatabase(dbName).getCollection(collectionName).updateOne(filter, update);
    }
}

