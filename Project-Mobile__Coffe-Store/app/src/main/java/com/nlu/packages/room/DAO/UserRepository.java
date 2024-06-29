package com.nlu.packages.room.DAO;

import androidx.room.*;
import com.nlu.packages.room.entity.User;

@Dao
public interface UserRepository {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);
}
