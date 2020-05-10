package project.followfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<Exercise> arrayList;

    public MyAdapter(Context context, ArrayList<Exercise> arrayList){

        this.context=context;
        this.arrayList = arrayList;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.mycustomlistview,null);
        TextView t1_date = (TextView)convertView.findViewById(R.id.date_txt);
        TextView t1_exercise = (TextView)convertView.findViewById(R.id.exercise_txt);
        TextView t1_weight = (TextView)convertView.findViewById(R.id.weight_txt);
        TextView t1_reps = (TextView)convertView.findViewById(R.id.reps_txt);
        TextView t1_muscle = (TextView)convertView.findViewById(R.id.muscle_txt);

        Exercise exercise = arrayList.get(position);

        t1_date.setText(exercise.getDate());
        t1_exercise.setText(exercise.getExercise());
        t1_weight.setText(exercise.getWeight());
        t1_reps.setText(exercise.getReps());
        t1_muscle.setText(exercise.getMuscle());


        return convertView;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }
}
