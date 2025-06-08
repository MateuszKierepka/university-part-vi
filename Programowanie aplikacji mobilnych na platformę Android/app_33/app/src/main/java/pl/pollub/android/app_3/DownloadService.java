package pl.pollub.android.app_3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

// usluga odpowiedzialna za pobieranie plikow w tle
public class DownloadService extends Service {
    public final static String TAG = DownloadService.class.getSimpleName();
    public final static String PACKAGE_NAME = "com.example.lab3_tlo.service";
    public final static String PROGRESS_INFO = PACKAGE_NAME + ".progress_info";
    private final static int BLOCK_SIZE = 32768;
    private static final String CHANNEL_ID = PACKAGE_NAME + ".service_channel";
    private static final int NOTIFICATION_ID_PROGRESS = 1;
    private static final int NOTIFICATION_ID_COMPLETE = 2;
    private NotificationManager notificationManager;
    private HandlerThread handlerThread;
    private Handler handler;
    private int bytesDownloaded;
    private int fileSize;
    private MutableLiveData<ProgressEvent> progressLiveData =  new MutableLiveData<>(null);
    private final IBinder binder = new MyServiceBinder();

    // binder definiuje metody, ktore mozna wywolywac z zewnatrz np. z aktywnosci
    public class MyServiceBinder extends Binder {
        LiveData<ProgressEvent> getProgressEvent() {
            return progressLiveData;
        }
    }

    // metoda odpowiedzialna za zwrocenie bindera
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    // inicjalizacja uslugi
    // tworzy dodatkowy watek do wykonywania zadan asynchronicznie
    @Override
    public void onCreate() {
        super.onCreate();
        handlerThread = new HandlerThread("DownloadService"); //dodatkowy wątek do wykonywania zadań asynchronicznie (usługa działa w głównym wątku aplikacji)
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()); //utworzenie handlera umożliwiającego wykonanie zadań w osobnym wątku
    }

    // wywolywana gdy usluga zostanie uruchomiona za pomoca startService
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); //przygotowanie kanału powiadomień (usługa musi wyświetlać powiadomienie)
        this.prepareNotificationChannel();
        handler.post(() -> executeTask(intent.getStringExtra(MainActivity.URL_KEY))); //wykonanie zadania za pomocą handlera
        return START_NOT_STICKY;
    }

    // wykonuje zadanie pobierania pliku
    protected void executeTask(String fileUrl) {
        this.notificationManager.cancel(NOTIFICATION_ID_PROGRESS); //usuwanie starych powiadomień
        this.bytesDownloaded = 0;
        this.fileSize = 0;
        
        //przejście usługi na pierwszy plan
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID_PROGRESS, getNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC);
        } else {
            startForeground(NOTIFICATION_ID_PROGRESS, getNotification());
        }

        String fileName = null;
        String mimeType = null;
        OutputStream fileOutputStream = null;
        HttpsURLConnection connection = null;
        DataInputStream reader = null;

        try {
            Log.d(TAG, "Start download");
            URL url = new URL(fileUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            fileSize = connection.getContentLength();
            mimeType = connection.getContentType();
            File tmpFile = new File(url.getFile());
            fileName = tmpFile.getName();
            //utworzenie nowego pliku
            ContentValues values = new ContentValues();
            //używa scoped storage, mamy dostęp do katalogu Downloads, ale trzeba dodać plik do bazy plików
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Uri fileUri = getFileUri(fileName);
                if (fileUri != null) {
                    getContentResolver().delete(fileUri, null, null); //jeżeli plik istnieje to go usuwamy
                }
                //tworzymy nowy wpis w bazie plików
                ContentResolver resolver = getContentResolver();
                values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                values.put(MediaStore.Downloads.MIME_TYPE, mimeType);
                values.put(MediaStore.Downloads.IS_PENDING, 1);
                values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
                fileUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                fileOutputStream = resolver.openOutputStream(fileUri);
            } else { //w przypadku starszych wersji używamy standardowych ścieżek
                File outFile = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator + fileName);
                if (outFile.exists())
                    outFile.delete();
                fileOutputStream = new FileOutputStream(outFile);
            }
            Log.d(TAG, "Start download, part II");
            reader = new DataInputStream(connection.getInputStream());
            byte[] buffer = new byte[BLOCK_SIZE];
            int downloaded = reader.read(buffer, 0, BLOCK_SIZE);
            while (downloaded != -1) {
                fileOutputStream.write(buffer, 0, downloaded);
                this.bytesDownloaded += downloaded;
                Log.d(TAG, "downloaded:" + this.bytesDownloaded);
                this.sendMessagesAndUpdateNotification();
                downloaded = reader.read(buffer, 0, BLOCK_SIZE);
            }
            Log.d(TAG, "downloadFile - going to background");
            stopForeground(STOP_FOREGROUND_REMOVE);
        } catch (Exception e) {
            e.printStackTrace();
            bytesDownloaded = -1;
            sendMessagesAndUpdateNotification();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Uri fileUri = getFileUri(fileName);
                if (fileUri != null) {
                    getContentResolver().delete(fileUri, null, null);
                }
            } else {
                File outFile = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator + fileName);
                if (outFile.exists()) {
                    outFile.delete();
                }
            }
            stopForeground(STOP_FOREGROUND_REMOVE);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // przygotowuje kanal powiadomien
    private void prepareNotificationChannel() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, getString(R.string.notification_channel_name), NotificationManager.IMPORTANCE_LOW);
            mChannel.setDescription(getString(R.string.notification_channel_description));
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    // tworzy powiadomienie o postepie pobierania
    private Notification getNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background)
               .setWhen(System.currentTimeMillis())
               .setPriority(Notification.PRIORITY_LOW)
               .setContentIntent(pendingIntent);

        if (bytesDownloaded == 0) {
            builder.setContentTitle(getString(R.string.notification_title_downloading))
                   .setContentText(getString(R.string.notification_text_downloading))
                   .setProgress(100, 0, true)
                   .setOngoing(true);
        } else if (bytesDownloaded < fileSize) {
            builder.setContentTitle(getString(R.string.notification_title_downloading))
                   .setContentText(getString(R.string.notification_text_downloading) + ": " + percentage() + "%")
                   .setProgress(100, percentage(), false)
                   .setOngoing(true);
        } else if (bytesDownloaded == fileSize) {
            builder.setContentTitle(getString(R.string.notification_text_finished))
                   .setProgress(0, 0, false)
                   .setOngoing(false)
                   .setAutoCancel(true);
        } else {
            builder.setContentTitle(getString(R.string.notification_text_finished_error))
                   .setProgress(0, 0, false)
                   .setOngoing(false)
                   .setAutoCancel(true);
        }

        return builder.build();
    }

    // pobiera uri pliku na podstawie nazwy
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private Uri getFileUri(String fileName) {
        ContentResolver resolver = getContentResolver();
        String[] projection = {MediaStore.Downloads._ID};
        String selection = MediaStore.Downloads.DISPLAY_NAME + " = ?";
        String[] selectionArgs = {fileName};
        try (Cursor cursor = resolver.query(MediaStore.Downloads.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Downloads._ID);
                long id = cursor.getLong(idColumn);
                return Uri.withAppendedPath(MediaStore.Downloads.EXTERNAL_CONTENT_URI, String.valueOf(id));
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    // aktualizuje powiadomienia i wysyla informacje o postepie
    void sendMessagesAndUpdateNotification() {
        if (bytesDownloaded < fileSize) {
            notificationManager.notify(NOTIFICATION_ID_PROGRESS, getNotification());
            progressLiveData.postValue(new ProgressEvent(bytesDownloaded, percentage(), ProgressEvent.DOWNLOAD_IN_PROGRESS));
        } else if (bytesDownloaded == fileSize) {
            notificationManager.notify(NOTIFICATION_ID_COMPLETE, getNotification());
            progressLiveData.postValue(new ProgressEvent(bytesDownloaded, 100, ProgressEvent.DOWNLOAD_OK));
            stopForeground(STOP_FOREGROUND_REMOVE);
        } else {
            notificationManager.notify(NOTIFICATION_ID_COMPLETE, getNotification());
            progressLiveData.postValue(new ProgressEvent(bytesDownloaded, percentage(), ProgressEvent.DOWNLOAD_ERROR));
            stopForeground(STOP_FOREGROUND_REMOVE);
        }
    }

    // oblicza procent pobrania pliku
    private int percentage() {
        return (int)( ((double)this.bytesDownloaded/(double) this.fileSize ) * 100 );
    }
}
