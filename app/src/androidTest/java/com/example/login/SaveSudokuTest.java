package com.example.login;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SaveSudokuTest {

    private AppDatabase database;
    private SudokuPuzzleDao puzzleDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        puzzleDao = database.sudokuPuzzleDao();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void testSaveSudoku() {
        SudokuPuzzle puzzle = new SudokuPuzzle();
        puzzle.setBoard(SudokuPuzzle.toJson(new int[][]{{1, 2}, {3, 4}}));
        puzzle.setFixedCells(SudokuPuzzle.toJson(new boolean[][]{{true, false}, {false, true}}));
        puzzle.setSolved(false);

        puzzleDao.insertPuzzle(puzzle);

        List<SudokuPuzzle> puzzles = puzzleDao.getAllPuzzles();
        assertEquals(1, puzzles.size());
    }
}