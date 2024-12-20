package com.example.login;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, SudokuPuzzle.class, HighScore.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract SudokuPuzzleDao sudokuPuzzleDao();

    public abstract HighScoreDao highScoreDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create sudoku_puzzle table
            database.execSQL("CREATE TABLE IF NOT EXISTS `sudoku_puzzle` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`board` TEXT, " +
                    "`fixed_cells` TEXT, " +
                    "`solved` INTEGER NOT NULL)");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create high_scores table
            database.execSQL("CREATE TABLE IF NOT EXISTS `high_scores` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`userId` TEXT NOT NULL, " +
                    "`score` INTEGER NOT NULL, " +
                    "`difficulty` TEXT, " +
                    "`completionTime` INTEGER NOT NULL, " +
                    "`dateAchieved` TEXT)");

            // Create index for high_scores
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_high_scores_userId` " +
                    "ON `high_scores` (`userId`)");
        }
    };
}