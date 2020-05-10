package project.followfit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class ExerciseDialog extends AppCompatDialogFragment {

    public EditText edit_exercise,edit_weight,edit_reps,edit_muscle;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle bundle = this.getArguments();
        final String selectedDate = bundle.getString("date");

        final mySQLiteDBHandler db = new mySQLiteDBHandler(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_exercise, null);

        edit_exercise = view.findViewById(R.id.edit_exercise);
        edit_weight = view.findViewById(R.id.edit_weight);
        edit_reps = view.findViewById(R.id.edit_reps);
        edit_muscle = view.findViewById(R.id.edit_muscle);


        builder.setView(view)
                .setTitle("Log Exercise")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isInserted = db.InsertData(selectedDate,edit_exercise.getText().toString(),edit_weight.getText().toString(),edit_reps.getText().toString(),edit_muscle.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(),"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                });
        return builder.create();
    }
}
