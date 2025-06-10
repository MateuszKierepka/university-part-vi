package pl.pollub.android.app_4;

import static android.graphics.Color.BLUE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

// klasa odpowiedzialna za powierzchnie rysowania, umozliwia rysowanie po ekranie za pomoca dotyku
public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = DrawingSurface.class.getSimpleName();
    private SurfaceHolder mHolder; // umozliwia kontrolowanie i monitorowanie powierzchni
    private Bitmap mBitmap = null;
    private Paint mFarba;
    private Canvas mBitmapCanvas = null;
    private Path mSciezka;
    private int currentColor = BLUE;

    // konstruktor inicjalizujacy podstawowe parametry rysowania
    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder(); // pojemnik powierzchni - pozwala kontrolowac i monitorowac powierzchnie
        mHolder.addCallback(this);
        // włączenie (podwójna negacja) rysowania za pomocą metody onDraw
        this.mFarba = new Paint();
        this.mFarba.setStrokeWidth(2);
        this.mFarba.setStyle(Paint.Style.STROKE);
        setWillNotDraw(false);
    }

    // metoda obslugujaca zdarzenia dotyku ekranu, umozliwia rysowanie
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
               this.mSciezka = new Path();
               this.mSciezka.moveTo(event.getX(), event.getY());
                this.mBitmapCanvas.drawCircle(event.getX(), event.getY(), 5, mFarba);
                break;
            case MotionEvent.ACTION_MOVE:
                if(this.mSciezka != null) {
                    this.mSciezka.lineTo(event.getX(), event.getY());
                    this.mBitmapCanvas.drawPath(mSciezka, mFarba);
                }
                break;
            case MotionEvent.ACTION_UP:
                this.mBitmapCanvas.drawCircle(event.getX(), event.getY(), 5, mFarba);
                break;
        }
        invalidate();
        return true;
    }

    // metoda rysujaca aktualny stan bitmapy na ekranie
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        mFarba.setColor(currentColor);
        canvas.drawBitmap(this.mBitmap,0, 0, null);
    }

    // implementacja wymaganej metody performClick dla zgodnosci z lint - onTouchEvent i performClick trzeba implementować razem
    public boolean performClick() {
        return super.performClick();
    }

    // robocza bitmapa na ktorej tworzymy rysunek gdy uzytkownik dotknie ekranu
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (this.mBitmap == null) { // Sprawdzenie, czy bitmapa już istnieje
            this.clearScreen();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (this.mBitmap.getWidth() != width || this.mBitmap.getHeight() != height) {
            Bitmap bitmap = Bitmap.createScaledBitmap(mBitmap, width, height, true);
            this.mBitmap = bitmap;
            mBitmapCanvas = new Canvas(mBitmap);
        }
    }

    // metoda wywolywana przy zniszczeniu powierzchni
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { }

    // utworzenie bitmapy o rozmiarze takim samym jak powierzchnia
    // utworzenie kanwy na ktorej bedzie rysowany obraz
    // tworzenie bitmapy i zwiazanej z nia kanwy
    public void clearScreen() {
        this.mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        this.mBitmapCanvas = new Canvas(mBitmap);
        this.mBitmapCanvas.drawARGB(255, 255, 255, 255);
        invalidate(); //unieważnienie zawartości komponentu
    }

    // metoda ustawiajaca kolor rysowania
    public void setColor(int color) {
        this.currentColor = color;
        invalidate();
    }

    // metoda zwracajaca aktualna bitmapę
    public Bitmap getBitmap() {
        return this.mBitmap;
    }
}