package pl.pollub.android.app_3;

import android.os.Handler;
import android.os.Looper;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

// klasa odpowiedzialna za pobieranie informacji o pliku w tle
public class ShortTask {
    private static final String TAG = ShortTask.class.getSimpleName();
    private final ExecutorService executorService;
    private final Handler mainThreadHandler;
    private Future<FileInfo> fileInfoFuture;

    // utworzenie puli 2 wątków i Handlera do wysyłania zadań do wątku UI
    public ShortTask() {
        this.executorService = Executors.newFixedThreadPool(2);
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    // interfejs do przekazywania wynikow operacji
    public interface ResultCallback {
        void onSuccess(FileInfo result);
        void onError(Throwable throwable);
    }

    // wykonuje zadanie pobierania informacji o pliku
    // przyjmuje callback do przekazywania wynikow i url pliku
    public Future<FileInfo> executeTask(ResultCallback callback, String fileUrl) {
        if (fileInfoFuture != null && !fileInfoFuture.isDone()) {
            fileInfoFuture.cancel(true); // anulowanie bieżącego zadania
        }
        // tworzenie zadania, które odczyta wynik zadania wykonanego w tle i przekaże go do głównego wątku
        Runnable completionTask = new Runnable() {
            @Override
            public void run() {
                try {
                    // czekanie na wynik (jeżeli wyniku nie ma blokuje wątek wywołujący)
                    FileInfo fileInfo = fileInfoFuture.get();
                    callback.onSuccess(fileInfo); // przekazanie wyniku
                } catch (CancellationException e) {
                    // ewentualna reakcja na anulowanie
                } catch (Exception e) {
                    callback.onError(e); // przekazanie informacji o błędach
                }
            }
        };
        // tworzenie zadania wykonywanego w tle (zadanie zwraca wynik)
        Callable<FileInfo> asyncTask = new Callable<FileInfo>() {
            @Override // wykonywane zadania mogą powodować wyjątki
            public FileInfo call() throws Exception {
                HttpsURLConnection connection = null;
                FileInfo fileInfo = null;
                try {
                    URL url = new URL(fileUrl);
                    connection = (HttpsURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    fileInfo = new FileInfo(connection.getContentLength(), connection.getContentType());
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }
                mainThreadHandler.post(completionTask);
                return fileInfo; // zakończenie przyszłości (poprzez ustawienie wyniku)
            }
        };
        this.fileInfoFuture = executorService.submit(asyncTask); // wysłanie zadania do wykonania przez pulę wątków
        return this.fileInfoFuture; // zwrócenie przyszłości (może być użyta do anulowania zadania)
    }

    // czyszczenie zasobow przy zamknieciu
    // anuluje wykonywane zadanie i zamyka pule watkow
    public void shutdown() {
        if (this.fileInfoFuture != null && !this.fileInfoFuture.isDone()) {
            this.fileInfoFuture.cancel(true); // anulowanie wykonywanego zadania, true – przerwanie zadania
        }
        executorService.shutdown(); // zamknięcie puli wątków
    }
}