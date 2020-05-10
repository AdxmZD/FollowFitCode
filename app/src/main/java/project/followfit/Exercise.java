package project.followfit;

public class Exercise {

    //int id;
    String date,exercise,weight,reps,muscle;

    public Exercise(String date, String exercise, String weight, String reps, String muscle) {

        this.date = date;
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.muscle = muscle;
    }

    //public Exercise(String date, String ex, String weight, String reps, String muscle){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }
}
