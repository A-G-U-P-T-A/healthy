package com.habitbuilder.HabitBuilder.Objects;

import com.habitbuilder.HabitBuilder.Constants.Frequency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Habit {
    public String userId;
    public String name;
    public String frequency = Frequency.DAILY;
    public String startDate;
    public String endDate;
    public Integer hour = -1;
}
