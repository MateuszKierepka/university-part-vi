package pl.pollub.android.app_1;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import pl.pollub.android.app_1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String IS_FIRST_NAME_VALID_KEY = "IS_FIRST_NAME_VALID_KEY";
    public static final String IS_LAST_NAME_VALID_KEY = "IS_LAST_NAME_VALID_KEY";
    public static final String IS_NUMBER_OF_GRADES_VALID_KEY = "IS_NUMBER_OF_GRADES_VALID_KEY";
    public static final String NUMBER_OF_GRADES_KEY = "NUMBER_OF_GRADES_KEY";


    private boolean isEtFirstNameValid;
    private boolean isEtLastNameValid;
    private boolean isEtNumberOfGradesValid;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.llMain, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadLayout();
        this.launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::gradesCallback);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.isEtFirstNameValid = savedInstanceState.getBoolean(IS_FIRST_NAME_VALID_KEY);
        this.isEtLastNameValid = savedInstanceState.getBoolean(IS_LAST_NAME_VALID_KEY);
        this.isEtNumberOfGradesValid = savedInstanceState.getBoolean(IS_NUMBER_OF_GRADES_VALID_KEY);
        this.checkValidation();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(IS_FIRST_NAME_VALID_KEY,this.isEtFirstNameValid);
        outState.putBoolean(IS_LAST_NAME_VALID_KEY,this.isEtLastNameValid);
        outState.putBoolean(IS_NUMBER_OF_GRADES_VALID_KEY,this.isEtNumberOfGradesValid);
        super.onSaveInstanceState(outState);
    }

    private void loadLayout() {
        this.binding.btnSubmit.setOnClickListener(this::prepareGrades);

        this.binding.etFirstName.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) return;
            this.isEtFirstNameValid = !TextUtils.isEmpty(this.binding.etFirstName.getText());
            if(!this.isEtFirstNameValid){
                this.binding.etFirstName.setError(getString(R.string.et_first_name_error_text));
            }
            checkValidation();
        });
        this.binding.etLastName.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) return;
            this.isEtLastNameValid = !TextUtils.isEmpty(this.binding.etLastName.getText());
            if(!this.isEtLastNameValid){
                this.binding.etLastName.setError(getString(R.string.et_last_name_error_text));
            }
            checkValidation();
        });
        this.binding.etNumberOfGrades.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) return;
            this.isEtNumberOfGradesValid = !TextUtils.isEmpty(this.binding.etNumberOfGrades.getText());
            checkValidation();
            if(!this.isEtNumberOfGradesValid){
                this.binding.etNumberOfGrades.setError(getString(R.string.et_number_of_grades_error_text));
                return;
            }
            try{
                int result = Integer.parseInt(this.binding.etNumberOfGrades.getText().toString());
                if(result < 5 || result > 15){
                    this.binding.etNumberOfGrades.setError(getString(R.string.et_number_of_grades_format_error_text));
                    this.isEtNumberOfGradesValid = false;
                    checkValidation();
                }
            }catch (Exception e){
                this.binding.etNumberOfGrades.setError(getString(R.string.et_number_of_grades_format_error_text));
                this.isEtNumberOfGradesValid = false;
                checkValidation();
            }
        } );
    }

    private void prepareGrades(View view) {
        if(this.isDataValid()) {
            Bundle bundle = new Bundle();
            //TODO fix null pointer
            int numberOfGrades = Integer.parseInt(this.binding.etNumberOfGrades.getText().toString());
            bundle.putInt(NUMBER_OF_GRADES_KEY,numberOfGrades);
            //TODO walidacja do rozbudowania
            Intent gradesIntent = new Intent(this, GradeListActivity.class);
            gradesIntent.putExtras(bundle);
            this.launcher.launch(gradesIntent);
        }
    }

    private void checkValidation(){
        this.binding.btnSubmit.setVisibility(isDataValid() ? View.VISIBLE : View.INVISIBLE);
    }

    private boolean isDataValid() {
        return this.isEtFirstNameValid && this.isEtLastNameValid && this.isEtNumberOfGradesValid;
    }

    private void gradesCallback(ActivityResult o) {
        if(o.getResultCode() == RESULT_OK){
            Bundle bundle = o.getData().getExtras();
            int i = bundle.getInt(NUMBER_OF_GRADES_KEY);
            int k = 0;
        }
    }
}