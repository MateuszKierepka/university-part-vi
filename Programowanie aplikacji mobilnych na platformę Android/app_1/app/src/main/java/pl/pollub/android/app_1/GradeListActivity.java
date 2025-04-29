package pl.pollub.android.app_1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import pl.pollub.android.app_1.databinding.ActivityGradeListBinding;

// wyswietla liste przedmiotow i umozliwia przypisanie ocen do kazdego z nich
public class GradeListActivity extends AppCompatActivity {

    private ActivityGradeListBinding binding;
    private List<SubjectGrade> subjectGradeList;
    public static final String AVERAGE_GRADE_KEY = "AVERAGE_GRADE_KEY";

    // inicjalizuje interfejs uzytkownika, tworzy liste przedmiotow na podstawie liczby
    // przedmiotow z MainActivity po zakonczeniu aktywnosci wysyla dane spowrotem do MainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.binding = ActivityGradeListBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundleFromMain = getIntent().getExtras();
        int numberOfGrades = bundleFromMain.getInt(MainActivity.NUMBER_OF_GRADES_KEY);
        this.subjectGradeList = Arrays
                .stream(getResources()
                .getStringArray(R.array.nazwy_przedmiotow_tab))
                .limit(numberOfGrades)
                .map(SubjectGrade::new).collect(Collectors.toList());
        AdapterGradeList adapter = new AdapterGradeList(getLayoutInflater(),subjectGradeList);
        this.binding.rvGradeList.setAdapter(adapter);
        this.binding.rvGradeList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.btnSendGrades.setOnClickListener(view -> {
            double averageGrade = calculateAverage();
            Bundle bundleToMain = new Bundle();
            bundleToMain.putInt(MainActivity.NUMBER_OF_GRADES_KEY,numberOfGrades);
            bundleToMain.putDouble(AVERAGE_GRADE_KEY, averageGrade);
            Intent intent = new Intent();
            intent.putExtras(bundleToMain);
            this.setResult(RESULT_OK,intent);
            this.finish();
        });
    }

    //
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int[] grades = savedInstanceState.getIntArray("GRADES_KEY");
        if (grades != null) {
            for (int i = 0; i < grades.length && i < subjectGradeList.size(); i++) {
                subjectGradeList.get(i).setGrade(grades[i]);
            }
        }
    }

    //
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int[] grades = new int[subjectGradeList.size()];
        for (int i = 0; i < subjectGradeList.size(); i++) {
            grades[i] = subjectGradeList.get(i).getGrade();
        }
        outState.putIntArray("GRADES_KEY", grades);
        super.onSaveInstanceState(outState);
    }

    // obliczenie sredniej ocen
    protected double calculateAverage() {
        double sum = 0;
        for (SubjectGrade grade : subjectGradeList) {
            sum += grade.getGrade();
        }
        return sum / subjectGradeList.size();
    }

    // obsluga klikniecia strzalki powrotu do poprzedniej aktywnosci
    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}