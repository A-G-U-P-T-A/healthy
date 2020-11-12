package com.habitbuilder.HabitBuilder.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public String username;
    public String password;
    public Integer stars = 0;
}
