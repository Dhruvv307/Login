package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SudokuPuzzleAdapter extends RecyclerView.Adapter<SudokuPuzzleAdapter.ViewHolder> {
    private List<SudokuPuzzle> puzzles;
    private Context context;

    public SudokuPuzzleAdapter(Context context, List<SudokuPuzzle> puzzles) {
        this.context = context;
        this.puzzles = puzzles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_sudoku_puzzles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SudokuPuzzle puzzle = puzzles.get(position);
        holder.tvPuzzleId.setText("ID: " + puzzle.getId());
        holder.tvPuzzleStatus.setText(puzzle.isSolved() ? "Solved" : "Unsolved");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SudokuActivity.class);
            intent.putExtra("board", SudokuPuzzle.fromJson(puzzle.getBoard()));
            intent.putExtra("fixedCells", SudokuPuzzle.fromJsonFixedCells(puzzle.getFixedCells()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return puzzles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPuzzleId, tvPuzzleStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPuzzleId = itemView.findViewById(R.id.tvPuzzleId);
        }
    }
}