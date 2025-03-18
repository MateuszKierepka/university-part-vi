package pl.pollub.android.app_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.pollub.android.app_1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String IS_FIRST_NAME_VALID_KEY = "IS_FIRST_NAME_VALID_KEY";
    public static final String IS_LAST_NAME_VALID_KEY = "IS_LAST_NAME_VALID_KEY";
    public static final String IS_NUMBER_OF_GRADES_VALID_KEY = "IS_NUMBER_OF_GRADES_VALID_KEY";
    private boolean isEtFirstNameValid;
    private boolean isEtLastNameValid;
    private boolean isEtNumberOfGradesValid;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        loadLayout();
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
    private void checkValidation(){
        this.binding.btnSubmit.setVisibility((this.isEtFirstNameValid && this.isEtLastNameValid && this.isEtNumberOfGradesValid)? View.VISIBLE : View.INVISIBLE);
    }
}
