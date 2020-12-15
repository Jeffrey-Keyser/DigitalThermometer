package com.example.digitalthermometer;

/*
 * Listens for camera connection updates.
 */

public interface CameraConnectionListener {
    void hardwareConnected();
    void streaming();
    void notStreaming();
}
