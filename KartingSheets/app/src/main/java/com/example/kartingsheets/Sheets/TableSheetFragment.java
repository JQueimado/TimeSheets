package com.example.kartingsheets.Sheets;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.example.kartingsheets.R;

public class TableSheetFragment extends Fragment {

    private TimeSheet timeSheet;

    public TableSheetFragment( TimeSheet timeSheet ){
        this.timeSheet = timeSheet;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_table_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private class TimeRow extends TableRow {

        private int index;
        private String time;
        private String dif_purple;
        private String dif_last;

        public TimeRow(Context context, int index, long time, long dif_last, long dif_purple) {
            super(context);
            this.index = index;
            this.time = TimeSheet.parseLap(time);
            this.dif_last = TimeSheet.parse_dif(dif_last);
            this.dif_purple = TimeSheet.parseLap(dif_purple, TimeSheet.PARSING_MODE_SIMPLE);

            //TODO: create and setup View

        }
    }
}