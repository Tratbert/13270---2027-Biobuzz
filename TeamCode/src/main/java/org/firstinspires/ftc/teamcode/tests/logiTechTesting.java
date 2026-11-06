package org.firstinspires.ftc.teamcode.tests;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.List;

@TeleOp
public class logiTechTesting extends OpMode {

    AprilTagProcessor myAprilTagProcessor;

    VisionPortal myVisionPortal;

    private DigitalChannel greenLED;
    private DigitalChannel redLED;

    int aprilTagId;

    boolean hiveFaceUp = false;

    @Override
    public void init() {

        greenLED = hardwareMap.get(DigitalChannel.class, "greenLED");
        redLED = hardwareMap.get(DigitalChannel.class, "redLED");
        greenLED.setMode(DigitalChannel.Mode.OUTPUT);
        redLED.setMode(DigitalChannel.Mode.OUTPUT);
        redLED.setState(true);
        greenLED.setState(false);



        myAprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .build();

        myVisionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "logi"))
                .addProcessor(myAprilTagProcessor)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();

    }

    @Override
    public void loop() {

        List<AprilTagDetection> currentDetections = myAprilTagProcessor.getDetections();
        telemetry.addData("# of April Tag Detections", currentDetections.size());
        for (AprilTagDetection detection : currentDetections){
            if(detection instanceof AprilTagSingleDetection){
                AprilTagSingleDetection singleDetection = (AprilTagSingleDetection) detection;
                aprilTagId = singleDetection.id;
            } else {
                aprilTagId = -1;
            }
        }
        telemetry.addData("April Tag ID: ", aprilTagId);
        if(30 <= aprilTagId && aprilTagId <= 33){
            hiveFaceUp = true;
        } else {
            hiveFaceUp = false;
        }

        greenLED.setState(hiveFaceUp);
        redLED.setState(!hiveFaceUp);

        telemetry.addData("Hive Up: ", hiveFaceUp);

    }
}
