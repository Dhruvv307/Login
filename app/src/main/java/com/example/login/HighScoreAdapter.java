package com.example.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHolder> {
    private List<HighScore> highScores;

    public HighScoreAdapter(List<HighScore> highScores) {
        this.highScores = highScores;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_high_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HighScore score = highScores.get(position);
        holder.tvRank.setText(String.valueOf(position + 1));
        holder.tvScore.setText(String.valueOf(score.getScore()));
        holder.tvUser.setText(score.getUserId());
        holder.tvTime.setText(formatTime(score.getCompletionTime()));
        holder.tvDate.setText(score.getDateAchieved());
    }

    @Override
    public int getItemCount() {
        return highScores.size();
    }

    public void updateScores(List<HighScore> newScores) {
        this.highScores = newScores;
        notifyDataSetChanged();
    }

    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank, tvScore, tvUser, tvTime, tvDate;

        ViewHolder(View view) {
            super(view);
            tvRank = view.findViewById(R.id.tvRank);
            tvScore = view.findViewById(R.id.tvScore);
            tvUser = view.findViewById(R.id.tvUser);
            tvTime = view.findViewById(R.id.tvTime);
            tvDate = view.findViewById(R.id.tvDate);
        }
    }
}