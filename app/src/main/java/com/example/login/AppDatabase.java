package com.example.login;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, SudokuPuzzle.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract com.example.login.UserDao userDao();
    public abstract com.example.login.SudokuPuzzleDao sudokuPuzzleDao();
}