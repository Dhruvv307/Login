package com.example.login;

import android.content.Context;
import android.graphics.Color;
import androidx.appcompat.widget.AppCompatTextView;

public class SudokuCell extends AppCompatTextView {
    private boolean isLocked = false;
    private int value = 0;

    public SudokuCell(Context context) {
        super(context);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }

    public void setValue(int value) {
        this.value = value;
        setText(value == 0 ? "" : String.valueOf(value));
    }

    public int getValue() {
        return value;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
        setTextColor(locked ? Color.BLACK : Color.BLUE);
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setSelected(boolean selected) {
        setBackgroundResource(selected ? R.drawable.sudoku_cell_selected_background : R.drawable.sudoku_cell_selected_background);
    }
}