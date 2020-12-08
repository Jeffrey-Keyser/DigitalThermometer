package com.example.digitalthermometer;

/*
 * Wrapper for using the ML Toolkit to find a face in an image.
 */

import android.graphics.Bitmap;

import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;

import java.util.List;

public class SnapshotFaceDetector {
    private final com.google.mlkit.vision.face.FaceDetector detector;

    public SnapshotFaceDetector() {
        detector = FaceDetection.getClient();
    }

    public List<Face> findFaces(Bitmap image) {
        Task<List<Face>> task = detector.process(InputImage.fromBitmap(image, 0));

        while(!task.isComplete()) {
            // wait for task to complete
        }

        try {
            return task.getResult();
        } catch(RuntimeExecutionException e) {
            return null;
        }
    }

    public void stop() {
        detector.close();
    }
}
