package pl.pollub.android.app_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pl.pollub.android.app_2.databinding.ActivityPhoneBinding;
import pl.pollub.android.app_2.databinding.PhoneItemBinding;

public class PhoneActivity extends AppCompatActivity {

    public static final String MANUFACTURER_KEY = "MANUFACTURER_KEY";
    public static final String MODEL_KEY = "MODEL_KEY";
    public static final String ANDROID_VERSION_KEY = "ANDROID_VERSION_KEY";
    public static final String WEB_SITE_KEY = "WEB_SITE_KEY";
    public static final String PHONE_ID_KEY = "PHONE_ID_KEY";
    private ActivityPhoneBinding binding;
    private int phoneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.binding = ActivityPhoneBinding.inflate(this.getLayoutInflater());
        setContentView(this.binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.phoneMain, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.binding.btnCancel.setOnClickListener(view -> {
            this.setResult(RESULT_CANCELED);
            this.finish();
        });
        this.binding.btnSave.setOnClickListener(view -> saveOrAddNewPhone());
        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                this.phoneId = bundle.getInt(PHONE_ID_KEY,0);
                if(this.phoneId > 0){
                    String manufacturer = bundle.getString(MANUFACTURER_KEY);
                    String model = bundle.getString(MODEL_KEY);
                    String androidVersion = bundle.getString(ANDROID_VERSION_KEY);
                    String webSite = bundle.getString(WEB_SITE_KEY);
                    this.binding.etManufacturer.setText(manufacturer);
                    this.binding.etModel.setText(model);
                    this.binding.etAndroidVersion.setText(androidVersion);
                    this.binding.etWebSite.setText(webSite);
                }
            }
        }
    }

    private void saveOrAddNewPhone() {
        Bundle phoneDataBundle = new Bundle();
        phoneDataBundle.putInt(PHONE_ID_KEY,this.phoneId);
        phoneDataBundle.putString(MANUFACTURER_KEY, this.binding.etManufacturer.getText().toString());
        phoneDataBundle.putString(MODEL_KEY, this.binding.etModel.getText().toString());
        phoneDataBundle.putString(ANDROID_VERSION_KEY, this.binding.etAndroidVersion.getText().toString());
        phoneDataBundle.putString(WEB_SITE_KEY, this.binding.etWebSite.getText().toString());
        this.setResult(RESULT_OK,new Intent().putExtras(phoneDataBundle));
        this.finish();
    }
}