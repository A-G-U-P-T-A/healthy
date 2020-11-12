package com.habitbuilder.HabitBuilder.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.habitbuilder.HabitBuilder.Constants.Frequency;
import com.habitbuilder.HabitBuilder.Objects.Habit;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class HabitService {

    @Autowired
    MongoService mongoService;

    public String createHabit(Habit habit) {
        ObjectId objectId = new ObjectId();
        mongoService.createEntry("habit", createOrderDocument(habit, objectId));
        return objectId.toString();
    }

    public ObjectNode getHabitsList(String userId) {
        Bson filter = Filters.eq("userid", userId);
        FindIterable<Document> getHabitsDocuments = mongoService.getData("habit", filter);
        ObjectNode objectNode = ObjectMapperService.getObjectMapper().createObjectNode();
        for(Document document:getHabitsDocuments) {
            Document dates = (Document) document.get("habitRep");
            Map<String, Integer>dateMap = new HashMap<>();
            for(String date: dates.keySet()) {
                dateMap.put(date, (Integer) dates.get(date));
            }
            JsonNode jsonNode = ObjectMapperService.getObjectMapper().convertValue(dateMap, JsonNode.class);
            objectNode.put(document.getString("name"), jsonNode);
        }
        return objectNode;
    }

    public Object updateHabitCompletion(String name, String date) {
        Bson filter = Filters.eq("name", name);
        Bson update = Filters.eq("habitRep."+date, 1);
        return mongoService.updateData("habit", filter, update);
    }

    private Document createOrderDocument(Habit habit, ObjectId objectId) {
        Document document = new Document();
        document.append("_id", objectId);
        document.append("userid", habit.getUserId());
        document.append("name", habit.getName());
        document.append("frequency", habit.getFrequency());
        document.append("hour", habit.hour);
        HashMap<String, Integer> habitRep = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(habit.getStartDate(),formatter);
        LocalDate endDate = LocalDate.parse(habit.getEndDate(),formatter);
        int frequency = getFrequency(habit);
        for (LocalDate itr = startDate; itr.isBefore(endDate); itr = itr.plusDays(frequency))
        {
            String date = itr.format(formatter);
            habitRep.put(date, 0);
        }
        document.append("habitRep", habitRep);
        return document;
    }

    private int getFrequency(Habit habit) {
        if(habit.getFrequency().equals(Frequency.DAILY))
            return 1;
        else if(habit.getFrequency().equals(Frequency.WEEKLY))
            return 7;
        return 1;
    }
}
