package org.firstinspires.ftc.teamcode.tests;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp
public class logiTechTesting extends OpMode {

    AprilTagProcessor myAprilTagProcessor;

    VisionPortal myVisionPortal;

    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;
    
    
    @Override
    public void init() {

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        
        
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

        double max;
        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial   =  gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral =  -gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower  = -1*(axial + lateral + yaw);
        double rightFrontPower = -1*(axial - lateral - yaw);
        double leftBackPower   = -1*(axial - lateral + yaw);
        double rightBackPower  = -1*(axial + lateral - yaw);

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.5) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }


        // Send calculated power to wheels
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower); // had to fix both backs to drive
        rightBack.setPower(rightBackPower);

        List<AprilTagDetection> currentDetections = myAprilTagProcessor.getDetections();
        telemetry.addData("# of April Tag Detections", currentDetections.size());
        for (AprilTagDetection detection : currentDetections){
            if(detection.metadata!= null){
                telemetry.addData("Tag id: ", "%d (%s)", detection.id, detection.metadata.name);

            } else {
                telemetry.addData("Unknown Tag Id: ", "%d", detection.id);
                int tagId = detection.id;
                if(tagId >= 38){
                    telemetry.addLine("Hub Tipped Left");
                } else {
                    telemetry.addLine("Hub Tipped Right");
                }

            }
        }

    }
}
