package com.example.login;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HighScoreDao {
    @Insert
    void insertScore(HighScore score);

    @Query("SELECT * FROM high_scores WHERE userId = :userId ORDER BY score DESC")
    List<HighScore> getUserScores(String userId);

    @Query("SELECT * FROM high_scores ORDER BY score DESC LIMIT :limit")
    List<HighScore> getTopScores(int limit);

    @Query("SELECT * FROM high_scores WHERE difficulty = :difficulty ORDER BY score DESC LIMIT :limit")
    List<HighScore> getTopScoresByDifficulty(String difficulty, int limit);

    @Query("SELECT * FROM high_scores ORDER BY completionTime ASC LIMIT :limit")
    List<HighScore> getFastestCompletions(int limit);
}