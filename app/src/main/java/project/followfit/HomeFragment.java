package project.followfit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FloatingActionButton fab;
    private EditText editText;
    private CalendarView calendarView;
    public String selectedDate;
    private mySQLiteDBHandler db;
    private TextView dateSelected;
    private Button btnDelete;

    ListView calendar_list;

    ArrayList<String> listItem;
    ArrayAdapter adapter;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = new mySQLiteDBHandler(getActivity());

        listItem = new ArrayList<>();

        editText = view.findViewById(R.id.edit_exercise);
        calendarView = view.findViewById(R.id.calendarView);
        calendar_list = view.findViewById(R.id.calendar_list);
        dateSelected = view.findViewById(R.id.date_selected);
        btnDelete = view.findViewById(R.id.btn_delete);

        DeleteData();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                //viewData();
                dateSelected.setText(selectedDate);
            }
        });

        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               openDialog();
           }
        });


        return view;
    }

    private void viewData(){
        Cursor cursor = db.viewData(selectedDate);

        if (cursor.getCount() == 0){
            Toast.makeText(getActivity(), "No workouts logged. What's your excuse today?", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(0));
            }

            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listItem);
            calendar_list.setAdapter(adapter);
        }
    }



    public void openDialog(){
        Bundle bundle = new Bundle();
        bundle.putString("date",selectedDate);
        ExerciseDialog exerciseDialog = new ExerciseDialog();
        exerciseDialog.setArguments(bundle);
        exerciseDialog.show(getFragmentManager(),"exercise dialog");
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = db.deleteData(selectedDate);
                        if (deletedRows > 0)
                            Toast.makeText(getActivity(), "Day Log Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "No Workouts Logged", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}



