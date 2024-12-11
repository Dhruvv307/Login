package com.example.login;


import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;


public class ClearDataBaseTest {
    private AppDatabase database;
    private UserDao userDao;
    private SudokuPuzzleDao puzzleDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        userDao = database.userDao();
        puzzleDao = database.sudokuPuzzleDao();
    }

    @After
    public void tearDown() {
        database.close();
    }


}