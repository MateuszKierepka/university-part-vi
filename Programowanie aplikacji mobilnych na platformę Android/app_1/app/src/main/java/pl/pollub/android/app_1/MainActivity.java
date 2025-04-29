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
import android.widget.Toast;

import pl.pollub.android.app_1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String IS_FIRST_NAME_VALID_KEY = "IS_FIRST_NAME_VALID_KEY";
    public static final String IS_LAST_NAME_VALID_KEY = "IS_LAST_NAME_VALID_KEY";
    public static final String IS_NUMBER_OF_GRADES_VALID_KEY = "IS_NUMBER_OF_GRADES_VALID_KEY";
    public static final String NUMBER_OF_GRADES_KEY = "NUMBER_OF_GRADES_KEY";
    public static final String HAS_AVERAGE_KEY = "HAS_AVERAGE_KEY";
    public static final String AVERAGE_VALUE_KEY = "AVERAGE_VALUE_KEY";

    private boolean isEtFirstNameValid;
    private boolean isEtLastNameValid;
    private boolean isEtNumberOfGradesValid;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    private double averageValue = 0.0;
    private boolean hasAverage = false;

    // inicjalizacja interfejsu uzytkownika
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

    // przywraca stan interfejsu (walidacja i srednia) po zmianie orientacji ekranu
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.isEtFirstNameValid = savedInstanceState.getBoolean(IS_FIRST_NAME_VALID_KEY);
        this.isEtLastNameValid = savedInstanceState.getBoolean(IS_LAST_NAME_VALID_KEY);
        this.isEtNumberOfGradesValid = savedInstanceState.getBoolean(IS_NUMBER_OF_GRADES_VALID_KEY);
        this.checkValidation();
        this.hasAverage = savedInstanceState.getBoolean(HAS_AVERAGE_KEY, false);
        if (this.hasAverage) {
            this.averageValue = savedInstanceState.getDouble(AVERAGE_VALUE_KEY, 0.0);
            showAverageResultButton();
        }
    }

    // zapisuje stan walidacji i dane o sredniej aby przywrocic je po obrocie ekranu
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(IS_FIRST_NAME_VALID_KEY,this.isEtFirstNameValid);
        outState.putBoolean(IS_LAST_NAME_VALID_KEY,this.isEtLastNameValid);
        outState.putBoolean(IS_NUMBER_OF_GRADES_VALID_KEY,this.isEtNumberOfGradesValid);
        outState.putBoolean(HAS_AVERAGE_KEY, this.hasAverage);
        if (this.hasAverage) {
            outState.putDouble(AVERAGE_VALUE_KEY, this.averageValue);
        }
        super.onSaveInstanceState(outState);
    }

    // nasluchiwanie na klikniecia i walidacja
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

    // przygotowuje dane do wyslania do nowej aktywnosci po kliknieciu przycisku
    private void prepareGrades(View view) {
        validateAllFields();
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

    // ustawia przycisk na widoczny jesli dane poprawne
    private void checkValidation(){
        this.binding.btnSubmit.setVisibility(isDataValid() ? View.VISIBLE : View.INVISIBLE);
    }

    // sprawdza czy wszystkie wprowadzone dane sa poprawne
    private boolean isDataValid() {
        return this.isEtFirstNameValid && this.isEtLastNameValid && this.isEtNumberOfGradesValid;
    }

    private void validateAllFields() {
        this.isEtFirstNameValid = !TextUtils.isEmpty(this.binding.etFirstName.getText());
        if(!this.isEtFirstNameValid){
            this.binding.etFirstName.setError(getString(R.string.et_first_name_error_text));
        }
        this.isEtLastNameValid = !TextUtils.isEmpty(this.binding.etLastName.getText());
        if(!this.isEtLastNameValid){
            this.binding.etLastName.setError(getString(R.string.et_last_name_error_text));
        }
        this.isEtNumberOfGradesValid = !TextUtils.isEmpty(this.binding.etNumberOfGrades.getText());
        if(!this.isEtNumberOfGradesValid){
            this.binding.etNumberOfGrades.setError(getString(R.string.et_number_of_grades_error_text));
        } else {
            try {
                int result = Integer.parseInt(this.binding.etNumberOfGrades.getText().toString());
                if(result < 5 || result > 15){
                    this.binding.etNumberOfGrades.setError(getString(R.string.et_number_of_grades_format_error_text));
                    this.isEtNumberOfGradesValid = false;
                }
            } catch (Exception e) {
                this.binding.etNumberOfGrades.setError(getString(R.string.et_number_of_grades_format_error_text));
                this.isEtNumberOfGradesValid = false;
            }
        }
    }

    // obsluga sredniej zwracanej przez aktywnosc GradeListActivity
    private void gradesCallback(ActivityResult o) {
        if(o.getResultCode() == RESULT_OK){
            Bundle bundle = o.getData().getExtras();
//            int i = bundle.getInt(NUMBER_OF_GRADES_KEY);
            if (bundle != null) {
                this.averageValue = bundle.getDouble(GradeListActivity.AVERAGE_GRADE_KEY);
                this.hasAverage = true;
                showAverageResultButton();
            }
        }
    }

    // wyswietlenie odpowiedniego przycisku dla otrzymanej sredniej
    private void showAverageResultButton() {
        this.binding.tvAverageGrade.setText(getString(R.string.average_grade_label, this.averageValue));
        this.binding.tvAverageGrade.setVisibility(View.VISIBLE);
        this.binding.btnResult.setVisibility(View.VISIBLE);
        if (this.averageValue >= 3.0) {
            this.binding.btnResult.setText(R.string.btn_positive_result);
        } else {
            this.binding.btnResult.setText(R.string.btn_negative_result);
        }
    }

    // wyswietlenie komunikatu zaleznego od sredniej i zakonczenie dzialania programu
    public void onResultButtonClicked(View view) {
        String message;
        if (this.averageValue >= 3.0) {
            message = getString(R.string.positive_result_message);
        } else {
            message = getString(R.string.negative_result_message);
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }
}