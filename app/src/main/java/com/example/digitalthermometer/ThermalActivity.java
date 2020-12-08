package com.example.digitalthermometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThermalActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView statusView;
    private TextView tempView;

    private ThermalCamera camera;

    private boolean running;
    private Thread imageProcessingThread;

    private boolean faceDetectorBusy;
    private SnapshotFaceDetector faceDetector;
    private ConcurrentLinkedQueue<Snapshot> snapshots;

    private double avgTemp;

    private DbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermal);

        // UI Objects
        imageView = findViewById(R.id.visual_image_view);
        statusView = findViewById(R.id.status);
        tempView = findViewById(R.id.temp);

        // Instances of Helper Classes
        snapshots = new ConcurrentLinkedQueue<>();
        faceDetector = new SnapshotFaceDetector();
        mydb = new DbHelper(this);

        // Listener (Passed to Camera Thread)
        CameraListener listener = (thermalData) -> {
            if(snapshots.isEmpty()) {
                snapshots.add(new Snapshot(thermalData));
            }
        };

        // Start Camera
        camera = new ThermalCamera(getApplicationContext(), listener);

        // Start Image Processing
        running = true;
        faceDetectorBusy = false;
        imageProcessingThread = new Thread(() -> {
            while(running) {
                if (!snapshots.isEmpty()) {
                    Snapshot snapshot = snapshots.remove(); // throw away if face detector busy
                    if (!faceDetectorBusy) {
                        faceDetectorBusy = true;
                        if(snapshot.findFace(faceDetector)) {
                            avgTemp = snapshot.avgTemp();
                            String text = "Temp: " + avgTemp;
                            runOnUiThread(() -> tempView.setText(text));
                        }
                        faceDetectorBusy = false;
                        runOnUiThread(() -> {
                            imageView.setImageBitmap(snapshot.getVisualImage());
                        });
                    }
                }
            }
        });
        imageProcessingThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            running = false;
            imageProcessingThread.join();
        } catch (InterruptedException e) {
            // if threads are interrupted
        }

        faceDetector.stop();
        camera.stop();
    }

    public void start(View view) {
        camera.start();
    }

    public void stop(View view) {
        camera.stop();
    }

    public void save(View view) {
        /*Reading reading = new Reading();
        reading.setTemp(Double.parseDouble(String.format(java.util.Locale.US, "%.2f", avgTemp)));
        reading.setTime(Calendar.getInstance().getTime());
        reading.setSymptoms("None");

        long readingId = mydb.insertReading(reading);
        Intent tempIntent;

        if(avgTemp >= 100.0) {
            tempIntent = new Intent(ThermalActivity.this, PositiveReadingActivity.class);
        } else {
            tempIntent = new Intent(ThermalActivity.this, NegativeReadingActivity.class);
        }

        tempIntent.putExtra(reading.INTENT_IDENTIFIER_READING_ID, Long.toString(readingId));
        startActivity(tempIntent);*/
    }

    public void cancel(View view) {
        stop(view);
        Intent mainActivity = new Intent(ThermalActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }

    /**
     * Shows a {@link Toast} on the UI thread.
     * @param text The message to show
     */
    private void showToast(final String text) {
        runOnUiThread(() -> Toast.makeText(ThermalActivity.this, text, Toast.LENGTH_LONG).show());
    }

}
