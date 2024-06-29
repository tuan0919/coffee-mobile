package com.nlu.packages.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity(tableName = "users")
@FieldDefaults(level = AccessLevel.PUBLIC)
public class User {
    @PrimaryKey
    Long id;
    String username;
    String email;
    String name;
    String token;
}
