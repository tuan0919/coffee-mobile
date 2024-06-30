package com.nlu.packages.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.nlu.packages.room.entity.User;


@Database(entities = {User.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase { ;
}