package com.example.digitalthermometer;

/*
 * Wrapper class for the FLIR One thermal camera.
 */

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.flir.thermalsdk.ErrorCode;
import com.flir.thermalsdk.androidsdk.ThermalSdkAndroid;
import com.flir.thermalsdk.androidsdk.image.BitmapAndroid;
import com.flir.thermalsdk.androidsdk.live.connectivity.UsbPermissionHandler;
import com.flir.thermalsdk.image.fusion.FusionMode;
import com.flir.thermalsdk.live.Camera;
import com.flir.thermalsdk.live.CommunicationInterface;
import com.flir.thermalsdk.live.ConnectParameters;
import com.flir.thermalsdk.live.Identity;
import com.flir.thermalsdk.live.connectivity.ConnectionStatusListener;
import com.flir.thermalsdk.live.discovery.DiscoveryEventListener;
import com.flir.thermalsdk.live.discovery.DiscoveryFactory;
import com.flir.thermalsdk.live.streaming.ThermalImageStreamListener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ThermalCamera {
    private final CameraListener videoListener;
    private final DiscoveryEventListener findHardwareListener;
    private Identity hardwareIdentity;
    private Camera camera;

    private final Context context;

    private boolean hardwareConnected = false;
    private boolean videoRunning = false;

    public ThermalCamera(Context appContext, CameraListener appListener) {
        // Save Context
        context = appContext;

        // Initialize Thermal SDK
        try {
            ThermalSdkAndroid.init(appContext);
        } catch (Exception e) {
            // if invoked multiple times for same context, an error occurs
        }

        // Set Video Stream Listener
        videoListener = appListener;

        // Set Find Hardware Listener
        findHardwareListener = new DiscoveryEventListener() {
            @Override
            public void onCameraFound(Identity identity) {
                hardwareIdentity = identity;
                DiscoveryFactory.getInstance().stop(CommunicationInterface.USB);
                hardwareConnected = true;
            }

            @Override
            public void onDiscoveryError(CommunicationInterface communicationInterface, ErrorCode errorCode) {
                // do nothing
            }
        };

        // Camera
        camera = new Camera();
    }

    public void findHardware() {
        if(hardwareConnected) {
            return;
        }

        DiscoveryFactory.getInstance().scan(findHardwareListener, CommunicationInterface.USB);
    }

    public void start() {
        if(videoRunning) {
            return;
        }

        if(!hardwareConnected) {
            findHardware();
        }

        if (UsbPermissionHandler.isFlirOne(hardwareIdentity)) {
            (new UsbPermissionHandler()).requestFlirOnePermisson(hardwareIdentity, context, new UsbPermissionHandler.UsbPermissionListener() {
                @Override
                public void permissionGranted(@NonNull Identity identity) {
                    try {
                        camera.connect(hardwareIdentity, errorCode -> {
                            videoRunning = false;
                        }, new ConnectParameters());

                        camera.subscribeStream(() -> camera.withImage(thermalData -> {
                            videoListener.receive(thermalData);
                        }));

                        videoRunning = true;
                    } catch(IOException e) {
                        videoRunning = false;
                    }
                }

                @Override
                public void permissionDenied(@NonNull Identity identity) {
                    // do nothing
                }

                @Override
                public void error(ErrorType errorType, Identity identity) {
                    // do nothing
                }
            });
        }
    }

    public void stop() {
        if (!videoRunning || camera == null) {
            return;
        }

        if (camera.isGrabbing()) {
            camera.unsubscribeAllStreams();
        }

        camera.disconnect();

        videoRunning = false;
    }
}