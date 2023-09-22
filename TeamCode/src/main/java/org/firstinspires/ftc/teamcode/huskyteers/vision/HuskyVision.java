package org.firstinspires.ftc.teamcode.huskyteers.vision;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

public class HuskyVision {

    public BackdropAprilTagDetection backdropAprilTagDetection;
    public PixelDetection pixelDetection;
    public VisionPortal visionPortal;

    public HuskyVision(HardwareMap hwMap) {
        backdropAprilTagDetection = new BackdropAprilTagDetection();
        pixelDetection = new PixelDetection();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .enableLiveView(true)
                .addProcessors(backdropAprilTagDetection.aprilTag, pixelDetection.tfodProcessor)
                .build();

        // Manually set the camera gain and exposure.
        // ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        // exposureControl.setExposure((long)6, TimeUnit.MILLISECONDS);
        // GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        // gainControl.setGain(250);
    }
}