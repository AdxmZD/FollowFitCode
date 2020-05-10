package project.followfit;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;


public class mySQLiteDBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ExerciseCalendar";

    public static final String TABLE_EXERCISE = "exercise_table";
    public static final String EXERCISE_ID = "ID";
    public static final String EXERCISE_DATE = "Date";
    public static final String EXERCISE_NAME = "Exercise";
    public static final String EXERCISE_WEIGHT = "Weight";
    public static final String EXERCISE_REPS = "Reps";
    public static final String EXERCISE_GROUP = "Muscle";

    private static final String EXERCISE_CREATE = "create table " + TABLE_EXERCISE + "(" +
            EXERCISE_ID + " integer primary key autoincrement, " + EXERCISE_DATE + " integer not null, "
            + EXERCISE_NAME + " text not null, " + EXERCISE_WEIGHT + " text not null, " +
            EXERCISE_REPS + " integer not null, " + EXERCISE_GROUP + " text not null );";

    private static final String WEIGHT_CREATE = "create table weight_table(xValue INTEGER,yValue INTEGER);";

    public mySQLiteDBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(EXERCISE_CREATE);
        //db.execSQL(WEIGHT_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS weight_table");
        onCreate(db);
    }

    public boolean InsertData(String selectedDate,String Exercise,String Weight,String Reps, String Group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXERCISE_DATE,selectedDate);
        contentValues.put(EXERCISE_NAME,Exercise);
        contentValues.put(EXERCISE_WEIGHT,Weight);
        contentValues.put(EXERCISE_REPS,Reps);
        contentValues.put(EXERCISE_GROUP,Group);
        long result = db.insert(TABLE_EXERCISE,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor viewData(String selectedDate){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_EXERCISE+" where Date =" +selectedDate, null);
        return cursor;
    }

    public Cursor getAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_EXERCISE, null);
        return res;
    }

    public ArrayList<Exercise> getAllData()
    {
        ArrayList<Exercise> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_EXERCISE, null);

        while(cursor.moveToNext())
        {
            String date = cursor.getString(1);
            String ex = cursor.getString(2);
            String weight = cursor.getString(3);
            String reps = cursor.getString(4);
            String muscle = cursor.getString(5);
            Exercise exercise = new Exercise(date,ex,weight,reps,muscle);

            arrayList.add(exercise);
        }

        return arrayList;
    }

    public Integer deleteData(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EXERCISE, "Date = ?", new String[] {date});
    }

    public void insertGraph (String xVal, int yVal){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("xValue", xVal);
        cv.put("yValue", yVal);

        db.insert("weight_table",null,cv);
    }

    public void clearDB (){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS weight_table");
        db.execSQL(WEIGHT_CREATE);

    }
}