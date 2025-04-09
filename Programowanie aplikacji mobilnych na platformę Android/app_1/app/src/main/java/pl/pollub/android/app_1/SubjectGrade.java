package pl.pollub.android.app_1;

public class SubjectGrade {
    private String name;
    private int grade;

    public SubjectGrade(String name) {
        this.name = name;
        this.grade = 2;
    }

    public int getGrade() {
        return grade;
    }

    public String getName() {
        return name;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
