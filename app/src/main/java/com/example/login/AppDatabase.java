package com.example.login;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, SudokuPuzzle.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract com.example.login.UserDao userDao();
    public abstract com.example.login.SudokuPuzzleDao sudokuPuzzleDao();


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // No need to do anything here since we're just updating the version
        }
    };


}