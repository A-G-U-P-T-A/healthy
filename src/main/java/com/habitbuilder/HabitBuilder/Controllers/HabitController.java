package com.habitbuilder.HabitBuilder.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.habitbuilder.HabitBuilder.Objects.Habit;
import com.habitbuilder.HabitBuilder.Services.HabitService;
import com.habitbuilder.HabitBuilder.Services.ObjectMapperService;
import com.habitbuilder.HabitBuilder.Validations.HabitValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HabitController {

    @Autowired
    HabitService habitService;

    @Autowired
    HabitValidation habitValidation;

    @CrossOrigin
    @PostMapping(value = "/createhabit")
    public @ResponseBody Object createHabit(@RequestBody Habit habit) {

        List<String> validation = habitValidation.validate(habit);
        if(validation.size()!=0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation);
        }
        String habitId = habitService.createHabit(habit);;
        ObjectNode objectNode = ObjectMapperService.getObjectMapper().createObjectNode();
        objectNode.put("habitId", habitId);
        return objectNode;
    }

    @CrossOrigin
    @GetMapping(value = "/getuserhabit")
    public @ResponseBody Object listHabits(@RequestParam String userid) {
        return habitService.getHabitsList(userid);
    }

    @CrossOrigin
    @GetMapping(value = "/markdone")
    public @ResponseBody Object updateHabit(@RequestParam String name, @RequestParam String date) {
        return habitService.updateHabitCompletion(name, date);
    }
}
