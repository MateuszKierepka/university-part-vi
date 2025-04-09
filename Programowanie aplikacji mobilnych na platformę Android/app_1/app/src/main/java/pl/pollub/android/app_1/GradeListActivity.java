package pl.pollub.android.app_1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import pl.pollub.android.app_1.databinding.ActivityGradeListBinding;

public class GradeListActivity extends AppCompatActivity {

    private ActivityGradeListBinding binding;
    private List<SubjectGrade> subjectGradeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.binding = ActivityGradeListBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundleFromMain = getIntent().getExtras();
        int numberOfGrades = bundleFromMain.getInt(MainActivity.NUMBER_OF_GRADES_KEY);
        this.subjectGradeList = Arrays.stream(getResources()
                .getStringArray(R.array.nazwy_przedmiotow_tab))
                .limit(numberOfGrades)
                .map(SubjectGrade::new).collect(Collectors.toList());
        AdapterGradeList adapter = new AdapterGradeList(getLayoutInflater(),subjectGradeList);
        this.binding.rvGradeList.setAdapter(adapter);
        this.binding.rvGradeList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.btnSendGrades.setOnClickListener(view -> {
            Bundle bundleToMain = new Bundle();
            bundleToMain.putInt(MainActivity.NUMBER_OF_GRADES_KEY,numberOfGrades);
            Intent intent = new Intent();
            intent.putExtras(bundleToMain);
            this.setResult(RESULT_OK,intent);
            this.finish();
        });
    }

}