package com.example.digitalthermometer;

/*
 * Holds a single frame of a visual image and the corresponding thermal image from the camera stream.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.flir.thermalsdk.androidsdk.image.BitmapAndroid;
import com.flir.thermalsdk.image.Rectangle;
import com.flir.thermalsdk.image.TemperatureUnit;
import com.flir.thermalsdk.image.ThermalImage;
import com.flir.thermalsdk.image.fusion.FusionMode;
import com.google.mlkit.vision.face.Face;

import java.util.List;

public class Snapshot {
    private final Bitmap visualImage;
    private final Bitmap thermalImage;

    private double[] temps;

    private Rect visualFace;
    private Rect thermalFace;

    private final Paint paint;

    public Snapshot(ThermalImage thermalData) {
        thermalData.getFusion().setFusionMode(FusionMode.VISUAL_ONLY);
        visualImage = BitmapAndroid.createBitmap(thermalData.getImage()).getBitMap();
        thermalData.getFusion().setFusionMode(FusionMode.THERMAL_ONLY);
        thermalImage = BitmapAndroid.createBitmap(thermalData.getImage()).getBitMap();

        thermalData.setTemperatureUnit(TemperatureUnit.FAHRENHEIT);
        temps = thermalData.getValues(new Rectangle(0, 0, thermalImage.getWidth(), thermalImage.getHeight()));

        visualFace = new Rect(0, 0, visualImage.getWidth() - 1, visualImage.getHeight() - 1);
        thermalFace = new Rect(0, 0, thermalImage.getWidth() - 1, thermalImage.getHeight() - 1);

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    public Bitmap getVisualImage() {
        return visualImage;
    }

    // Finds faces in the image and draws box around one of them in visual image.
    // No guarantee which face will be chosen if there are multiple visible.
    // Returns true if and only if a face is found.
    public boolean findFace(SnapshotFaceDetector faceDetector) {
        List<Face> faces = faceDetector.findFaces(visualImage);
        if (faces != null && faces.size() != 0) {
            visualFace = faces.get(0).getBoundingBox();
            visualFace.left = Math.max(visualFace.left, 0);
            visualFace.top = Math.max(visualFace.top, 0);
            visualFace.right = Math.min(visualFace.right, visualImage.getWidth() - 1);
            visualFace.bottom = Math.min(visualFace.bottom, visualImage.getHeight() - 1);
            (new Canvas(visualImage)).drawRect(visualFace, paint);

            thermalFace = new Rect();
            float hScale = ((float) thermalImage.getWidth()) / ((float) visualImage.getWidth());
            float vScale = ((float) thermalImage.getHeight()) / ((float) visualImage.getHeight());
            thermalFace.left = (int) (hScale * (float) visualFace.left);
            thermalFace.top = (int) (vScale * (float) visualFace.top);
            thermalFace.right = (int) (hScale * (float) visualFace.right);
            thermalFace.bottom = (int) (vScale * (float) visualFace.bottom);
            (new Canvas(thermalImage)).drawRect(thermalFace, paint);

            return true;
        } else {
            return false;
        }
    }

    // Returns temperature of the face.
    // Current implementation: max temp in box
    public double faceTemp() {
        try {
            double measuredTemp = 0;
            int width = thermalFace.width();
            int height = thermalFace.height();

            for(int x = 0; x < width; x++) {
                for(int y = 0; y < height; y++) {
                    measuredTemp = Math.max(measuredTemp, temps[thermalFace.left + x + (thermalFace.top + y) * thermalImage.getWidth()]);
                }
            }

            return measuredTemp;
        } catch(Exception e) {
            return -1.0;
        }
    }
}
