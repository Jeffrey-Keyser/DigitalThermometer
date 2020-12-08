package com.example.digitalthermometer;

/*
 * A listener interface that receives the camera's video stream.
 */

import com.flir.thermalsdk.image.ThermalImage;

public interface CameraListener {
    void receive(ThermalImage thermalImage);
}
