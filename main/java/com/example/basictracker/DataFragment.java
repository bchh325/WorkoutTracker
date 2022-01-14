package com.example.basictracker;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Database;
import androidx.room.Room;

import com.example.basictracker.databinding.FragmentDataBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataFragment extends Fragment implements View.OnClickListener {

    private FragmentDataBinding binding;

    private List<Integer> setsList = new ArrayList<>();
    private List<Integer> repsList = new ArrayList<>();
    private List<Integer> weightList = new ArrayList<>();
    private List<String> datesList = new ArrayList<>();

    private WorkoutsDatabase appDb;
    private TableLayout dataTable;

    private String workout_name;
    private TextView data_header;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        appDb = ThirdFragment.getAppDb();
        workout_name = ThirdFragment.getWorkoutName();
        getSetsList();
        getRepsList();
        getWeightList();
        getDatesList();

        binding = FragmentDataBinding.inflate(inflater, container, false);

        System.out.println("Sets: " + setsList);
        System.out.println("Reps: " + repsList);
        System.out.println("Weight: " + weightList);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DataFragment.this)
                        .navigate(R.id.action_dataFragment_to_thirdFragment);
            }

        });
        data_header = (TextView) view.findViewById(R.id.dataHeader);
        if (workout_name.equals("SELECT")) {
            data_header.setText("N/A");
        } else {
            data_header.setText(workout_name);
        }

        generateParentRow();
        generateTable();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void generateParentRow() {
        View view = getView();
        dataTable = (TableLayout) view.findViewById(R.id.dataTable);

        TableRow colParent = new TableRow(getContext());

        TextView c1 = new TextView(getContext());
        TextView c2 = new TextView(getContext());
        TextView c3 = new TextView(getContext());
        TextView c4 = new TextView(getContext());

        c1.setText("Sets    ");
        c2.setText("Reps    ");
        c3.setText("Weight     ");
        c4.setText("Date    ");

        c1.setTextColor(Color.WHITE);
        c2.setTextColor(Color.WHITE);
        c3.setTextColor(Color.WHITE);
        c4.setTextColor(Color.WHITE);

        colParent.addView(c1);
        colParent.addView(c2);
        colParent.addView(c3);
        colParent.addView(c4);

        dataTable.addView(colParent);
    }

    public void generateTable() {
        for (int i = 0; i < weightList.size(); i++) {
            TableRow dataRow = new TableRow(getContext());
            dataRow.setClickable(true);
            //dataRow.setOnClickListener(dataRowListener);

            TextView setsCol = new TextView(getContext());
            TextView repsCol = new TextView(getContext());
            TextView weightCol = new TextView(getContext());
            TextView dateCol = new TextView(getContext());

            setsCol.setText(setsList.get(i).toString());
            repsCol.setText(repsList.get(i).toString());
            weightCol.setText(weightList.get(i).toString());
            dateCol.setText(datesList.get(i));

            setsCol.setTextColor(Color.WHITE);
            repsCol.setTextColor(Color.WHITE);
            weightCol.setTextColor(Color.WHITE);
            dateCol.setTextColor(Color.WHITE);

            dataRow.addView(setsCol);
            dataRow.addView(repsCol);
            dataRow.addView(weightCol);
            dataRow.addView(dateCol);

            dataTable.addView(dataRow);
        }
    }

    public void getSetsList() {
        appDb = WorkoutsDatabase.getInstance(this.getContext());
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                setsList = appDb.workoutsDao().getNumSets(workout_name);
            }
        });
        thread.start();
    }

    public void getRepsList() {
        appDb = WorkoutsDatabase.getInstance(this.getContext());
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                repsList = appDb.workoutsDao().getNumReps(workout_name);
            }
        });
        thread.start();
    }

    public void getWeightList() {
        appDb = WorkoutsDatabase.getInstance(this.getContext());
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("START " + workout_name);
                weightList = appDb.workoutsDao().getNumWeight(workout_name);
                System.out.println(weightList);
            }
        });
        thread.start();
    }

    public void getDatesList() {
        appDb = WorkoutsDatabase.getInstance(this.getContext());
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                datesList = appDb.workoutsDao().getDates(workout_name);
            }
        });
        thread.start();
    }

    @Override
    public void onClick(View v) {

    }
}