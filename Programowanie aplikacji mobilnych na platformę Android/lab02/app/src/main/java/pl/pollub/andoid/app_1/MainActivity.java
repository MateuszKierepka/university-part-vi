package pl.pollub.andoid.app_1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText etFirstName;
    private boolean isEtFirstNameValid;
    private EditText etLastName;
    private boolean isEtLastNameValid;
    private EditText etNumberOfGrades;
    private boolean isEtNumberOfGradesValid;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ll_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadLayout();
    }
    private void loadLayout() {
        this.etFirstName = findViewById(R.id.et_first_name);
        this.etLastName = findViewById(R.id.et_last_name);
        this.etNumberOfGrades = findViewById(R.id.et_number_of_grades);
        this.btnSubmit = findViewById(R.id.btn_submit);
        this.etFirstName.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) return;
            this.isEtFirstNameValid = !TextUtils.isEmpty(this.etFirstName.getText());
            if (!this.isEtFirstNameValid) {
                this.etFirstName.setError(getString(R.string.et_first_name_error_text));
            }
            checkValidation();
        });
        this.etLastName.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) return;
            this.isEtLastNameValid = !TextUtils.isEmpty(this.etLastName.getText());
            if (!this.isEtLastNameValid) {
                this.etLastName.setError(getString(R.string.et_last_name_error_text));
            }
            checkValidation();
        });
        this.etNumberOfGrades.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) return;
            this.isEtNumberOfGradesValid = !TextUtils.isEmpty(this.etNumberOfGrades.getText());
            checkValidation();
            if (this.isEtNumberOfGradesValid){
                this.etNumberOfGrades.setError(getString(R.string.et_number_of_grades_error_text));
                this.isEtNumberOfGradesValid = false;
            }
            try{
                int result = Integer.parseInt(this.etNumberOfGrades.getText().toString());
                if (result < 5 || result > 15){
                    this.etNumberOfGrades.setError(getString(R.string.et_number_of_grades_format_error_text));
                    return;
                }
            } catch (Exception e){
                this.etNumberOfGrades.setError(getString(R.string.et_number_of_grades_error_text));
                this.isEtNumberOfGradesValid = false;
            }
        });
    }
    private void checkValidation() {
        this.btnSubmit.setVisibility((this.isEtFirstNameValid && this.isEtLastNameValid && this.isEtNumberOfGradesValid)? View.VISIBLE : View.INVISIBLE);
    }
}