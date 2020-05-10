package project.followfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class WorkoutsFragment extends Fragment {

    ListView l1;
    mySQLiteDBHandler db;
    ArrayList<Exercise> arrayList;
    MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

        l1 = (ListView)view.findViewById(R.id.workout_log);
        db = new mySQLiteDBHandler(getActivity());
        arrayList = new ArrayList<>();

        loadDataInListView();

        return view;
    }

    private void loadDataInListView() {
        arrayList = db.getAllData();
        myAdapter = new MyAdapter(getActivity(), arrayList);
        l1.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
}
