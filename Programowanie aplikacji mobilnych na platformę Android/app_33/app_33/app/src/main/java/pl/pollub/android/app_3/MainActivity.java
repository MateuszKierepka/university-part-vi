package pl.pollub.android.app_3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import pl.pollub.android.app_3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String URL_KEY = "URL_KEY";
    private ActivityMainBinding binding;
    private boolean serviceBound = false; // czy mamy polaczenie z usluga
    private LiveData<ProgressEvent> progressEventLiveData; //dane z uslugi
    private static final int PERMISSIONS_REQUEST_CODE = 1001;

    //reagowanie na polaczenie/rozlaczenie z usluga
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            serviceBound = true;
            DownloadService.MyServiceBinder downloadBinder = (DownloadService.MyServiceBinder) iBinder;
            progressEventLiveData = downloadBinder.getProgressEvent();
            progressEventLiveData.observe(MainActivity.this, MainActivity.this::updateProgress);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            progressEventLiveData.removeObservers(MainActivity.this);
            serviceBound = false;
        }
    };

    // inicjalizacja aktywnosci
    // ustawia layout, inicjalizuje przyciski i ich listenery
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(this.binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestRequiredPermission();

        ShortTask shortTask = new ShortTask();
        this.binding.btnGetFileInfo.setOnClickListener(view -> shortTask.executeTask(new ShortTask.ResultCallback() {
            @Override
            public void onSuccess(FileInfo result) {
                MainActivity.this.binding.etFileType.setText(result.getFiletype());
                MainActivity.this.binding.etFileSize.setText(String.valueOf(result.getFileSize()));
            }
            @Override
            public void onError(Throwable throwable) {
                // ...
            }
        }, this.binding.etUrl.getText().toString()));

        this.binding.btnGetFile.setOnClickListener(view -> {
            Intent intent = new Intent(this,DownloadService.class);
            intent.putExtra(URL_KEY,this.binding.etUrl.getText().toString());
            this.startService(intent);
        });

        Intent myServiceIntent = new Intent(this, DownloadService.class);
        bindService(myServiceIntent, serviceConnection,  Context.BIND_AUTO_CREATE);
    }

    // sprawdza i prosi o wymagane uprawnienia
    private void requestRequiredPermission() {
        String requiredPermission = getRequiredPermission();
        if (requiredPermission.isEmpty()) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, requiredPermission) == PackageManager.PERMISSION_GRANTED) {
            // już mamy uprawnienie możemy rozpocząć pobieranie
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, requiredPermission)) {
            // musimy wyjaśnić użytkownikowi po co nam uprawnienie
            ActivityCompat.requestPermissions(this, new String[]{requiredPermission}, PERMISSIONS_REQUEST_CODE);
        } else {
            // nie mamy uprawnień - prosimy o uprawnienie
            ActivityCompat.requestPermissions(this, new String[]{requiredPermission}, PERMISSIONS_REQUEST_CODE);
        }
    }

    // zwraca wymagane uprawnienie w zaleznosci od wersji androida
    private static String getRequiredPermission() {
        String requiredPermission = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requiredPermission = android.Manifest.permission.POST_NOTIFICATIONS;
        }
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            requiredPermission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        }
        return requiredPermission;
    }

    // obsluguje wynik proby uzyskania uprawnien
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String requiredPermission = getRequiredPermission();
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
            if (permissions.length > 0 && permissions[0].equals(requiredPermission) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //użytkownik przyznał uprawnienie - można rozpocząć pobieranie
            } else
                //użytkownik się nie zgodził
                break;
            default:
                //nieznane uprawnienie
                break;
        }
    }

    // czyszczenie zasobow przy zamknieciu aktywnosci
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection); //odłączenie usługi
    }

    // aktualizuje interfejs uzytkownika na podstawie postepu pobierania
    private void updateProgress(ProgressEvent progressEvent) {
        if(progressEvent != null) {
            this.binding.etDownloadedBytes.setText(String.valueOf(progressEvent.getDownloadedBytes()));
            this.binding.progress.setProgress(progressEvent.getDownloadProgress());
        }
    }
}