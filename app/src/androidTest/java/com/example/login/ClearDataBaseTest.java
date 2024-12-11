package com.example.login;


import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


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

    @Test
    public void testClearData() {
        User user1 = new User("user1", "password1", false);
        User user2 = new User("user2", "password2", true);
        userDao.insert(user1);
        userDao.insert(user2);

        SudokuPuzzle puzzle1 = new SudokuPuzzle();
        puzzle1.setBoard(SudokuPuzzle.toJson(new int[][]{{1, 2}, {3, 4}}));
        puzzle1.setFixedCells(SudokuPuzzle.toJson(new boolean[][]{{true, false}, {false, true}}));
        puzzle1.setSolved(false);
        puzzleDao.insertPuzzle(puzzle1);

        SudokuPuzzle puzzle2 = new SudokuPuzzle();
        puzzle2.setBoard(SudokuPuzzle.toJson(new int[][]{{5, 6}, {7, 8}}));
        puzzle2.setFixedCells(SudokuPuzzle.toJson(new boolean[][]{{true, true}, {true, true}}));
        puzzle2.setSolved(true);
        puzzleDao.insertPuzzle(puzzle2);

        List<User> users = userDao.getAllUsers();
        List<SudokuPuzzle> puzzles = puzzleDao.getAllPuzzles();
        assertEquals(2, users.size());
        assertEquals(2, puzzles.size());

        userDao.clearAllUsers();
        puzzleDao.clearAllPuzzles();

        users = userDao.getAllUsers();
        puzzles = puzzleDao.getAllPuzzles();
        assertEquals(0, users.size());
        assertEquals(0, puzzles.size());
    }
}