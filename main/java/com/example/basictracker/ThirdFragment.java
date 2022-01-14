package com.example.basictracker;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;
import androidx.appcompat.app.AppCompatActivity;



import com.example.basictracker.databinding.FragmentThirdBinding;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThirdFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static WorkoutsDatabase appDb;
    private FragmentThirdBinding binding;

    private EditText sets;
    private EditText reps;
    private EditText weight;

    private String date;
    private static String workout;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        startDatabase();
        binding = FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_thirdFragment_to_FirstFragment);
            }

        });

        binding.workoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_thirdFragment_to_dataFragment);
            }
        });

        binding.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (workout.matches("SELECT")) {
                    displayToast(view, "ERROR: Select a workout");
                } else if (sets.getText().toString().matches("") ||
                        reps.getText().toString().matches("") ||
                        weight.getText().toString().matches("")) {
                    displayToast(view, "ERROR: One or more fields are empty");
                } else {
                    insertThread();
                }
            }
        });

        sets = (EditText)view.findViewById(R.id.sets_input);
        reps = (EditText)view.findViewById(R.id.reps_input);
        weight = (EditText)view.findViewById(R.id.weight_input);

        Spinner spinner = view.findViewById(R.id.workouts);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), R.array.workoutslist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        workout = parent.getItemAtPosition(pos).toString();
        System.out.println("WORKOUT IS " + workout);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("Nothing selected");
    }

    public void displayToast(View view, String str) {
        Toast toast = Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void startDatabase() {
        appDb = WorkoutsDatabase.getInstance(this.getContext());
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                appDb.workoutsDao().getWorkoutsList();
            }
        });
        thread.start();
    }

    public void insertThread() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
        date = fmt.format(now);
        appDb = WorkoutsDatabase.getInstance(this.getContext());
        Workouts data = new Workouts(
                workout,
                Integer.parseInt(sets.getText().toString()),
                Integer.parseInt(reps.getText().toString()),
                Integer.parseInt(weight.getText().toString()),
                date);
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                appDb.workoutsDao().insertWorkout(data);
            }
        });
        thread.start();
    }

    public static WorkoutsDatabase getAppDb() {
        return appDb;
    }

    public static String getWorkoutName() {
        return workout;
    }
}