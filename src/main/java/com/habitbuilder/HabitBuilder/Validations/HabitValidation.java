package com.habitbuilder.HabitBuilder.Validations;


import com.habitbuilder.HabitBuilder.Objects.Habit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabitValidation {
    public List<String> validate(Habit habit) {
        List<String> failedValidations = new ArrayList<>();
        if(habit.getName()==null||habit.getName().trim().length()==0)
            failedValidations.add("name is null");
        if(habit.getEndDate()==null||habit.getEndDate().trim().length()==0)
            failedValidations.add("end date is null");
        if(habit.getStartDate()==null||habit.getStartDate().trim().length()==0)
            failedValidations.add("start date is null");
        if(habit.getHour()==-1)
            failedValidations.add("hour not mentioned for repetition");
        return failedValidations;
    }
}
