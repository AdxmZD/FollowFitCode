package project.followfit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StatisticsFragment extends Fragment {

    Button insertWeight,clearWeight;
    EditText inputWeight;
    GraphView graphView;

    mySQLiteDBHandler db;
    SQLiteDatabase sqLiteDatabase;

    LineGraphSeries<DataPoint> dataseries;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        insertWeight = view.findViewById(R.id.btn_insertWeight);
        clearWeight = view.findViewById(R.id.btn_clearWeight);
        inputWeight = view.findViewById(R.id.inputWeight);
        graphView = view.findViewById(R.id.graphView);

        db = new mySQLiteDBHandler(getActivity());
        sqLiteDatabase = db.getWritableDatabase();

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        dataseries = new LineGraphSeries<>(grabData());
        graphView.addSeries(dataseries);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(4);

        insertToGraph();

        clearDatabase();

        return view;
    }

    public void clearDatabase(){
        clearWeight.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("Are you sure you want to clear graph?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.clearDB();
                                dataseries.resetData(grabData());
                                Toast.makeText(getActivity(), "Graph Cleared", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("CANCEL",null);

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void insertToGraph(){
        insertWeight.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                try {
                    double xValue = new Date().getTime();
                    int yValue = Integer.parseInt(String.valueOf(inputWeight.getText()));
                    db.insertGraph(Double.toString(xValue), yValue);
                    Toast.makeText(getActivity(), "Weight Inserted", Toast.LENGTH_LONG).show();
                    dataseries.resetData(grabData());
                } catch(Exception e){
                    Toast.makeText(getActivity(), "Invalid Weight", Toast.LENGTH_LONG).show();
                    System.out.println("Weight not added error");
                }

            }

        });
    }

    private DataPoint[] grabData(){

    String [] column = {"xValue","yValue"};
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.query("weight_table", column, null, null, null, null, null);

        DataPoint[] dataPoints = new DataPoint[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();
            dataPoints[i] = new DataPoint(cursor.getDouble(0),cursor.getInt(1));
        }
        System.out.println(dataPoints+"uzair");
        return dataPoints;
}

}


