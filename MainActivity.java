package com.example.madproject_todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText taskNameInput;
    Spinner prioritySpinner;
    DatePicker dueDatePicker;
    TimePicker dueTimePicker;
    Button addTaskBtn;
    ListView taskListView;
    List<Task> tasks;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskNameInput = findViewById(R.id.taskNameInput);
        prioritySpinner = findViewById(R.id.prioritySpinner);
        dueDatePicker = findViewById(R.id.dueDatePicker);
        dueTimePicker = findViewById(R.id.dueTimePicker);
        addTaskBtn = findViewById(R.id.addTaskBtn);
        taskListView = findViewById(R.id.taskListView);

        tasks = new ArrayList<>();
        adapter = new TaskAdapter(this, tasks);
        taskListView.setAdapter(adapter);

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = taskNameInput.getText().toString();
                int priority = prioritySpinner.getSelectedItemPosition();
                String dueDate = getDateFromDatePicker(dueDatePicker);
                String time = getTimeFromTimePicker(dueTimePicker);

                if (!taskName.isEmpty()) {
                    Task task = new Task(taskName, priority, dueDate, time);
                    tasks.add(task);
                    adapter.notifyDataSetChanged();

                    // Navigate to TaskListActivity
                    Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                    intent.putParcelableArrayListExtra("tasks", (ArrayList<? extends Parcelable>) new ArrayList<>(tasks));
                    startActivity(intent);

                    // Clear input fields
                    taskNameInput.setText("");
                    prioritySpinner.setSelection(0);
                    resetDatePicker();
                } else {
                    Toast.makeText(MainActivity.this, "Task name cannot be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        return String.format(Locale.getDefault(), "%02d/%02d/%d", day, month, year);
    }

    private String getTimeFromTimePicker(TimePicker timePicker) {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }

    private void resetDatePicker() {
        Calendar calendar = Calendar.getInstance();
        dueDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
    public void navigateToTaskList(View view) {
        Intent intent = new Intent(this, TaskListActivity.class);
        startActivity(intent);
    }
}
