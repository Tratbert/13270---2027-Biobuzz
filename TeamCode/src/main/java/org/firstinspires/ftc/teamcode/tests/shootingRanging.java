package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.Arrays;

@TeleOp
public class shootingRanging extends OpMode {

    DcMotorEx shooter;

    DcMotor sorter;
    DcMotor intake;

    double[] P_Kv_Ks = {0,0,0};

    double[] values = {0.0001,0.001, 0.01, 0.1, 1, 10};

    int[] targetVelTable = {1,10,100,1000};
    int velTableSteps =0;

    int indicator = 0;

    int step = 0;

    int targetVelocity = 2000;

    int[] pose = {0,0};
    int[] targetPose = {0,0};

    double distance = Math.sqrt(Math.pow((targetPose[0]-pose[0]),2) + Math.pow((targetPose[1] - pose[1]), 2));
    String strIndicator;

    @Override
    public void init() {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        intake = hardwareMap.get(DcMotor.class, "intake");
        sorter = hardwareMap.get(DcMotor.class, "sorter");
    }

    @Override
    public void loop() {
        double velocity = shooter.getVelocity();
        double error = targetVelocity-velocity;
        double feedBack = error * P_Kv_Ks[0];
        double feedForward = P_Kv_Ks[1] * targetVelocity + P_Kv_Ks[2];
        if(gamepad1.rightBumperWasPressed()){
            step++;
        }
        if(gamepad1.leftBumperWasPressed()){
            step--;
        }
        if(indicator % 3 == 0){
            strIndicator = "P";
        } else if (indicator % 3 == 1) {
            strIndicator = "Kv";
        } else {
            strIndicator = "Ks";
        }

        if(gamepad1.dpadRightWasPressed()){
            indicator++;
        }
        if(gamepad1.dpadLeftWasPressed()){
            indicator--;
        }
        if(gamepad1.dpadUpWasPressed()){
            P_Kv_Ks[Math.abs(indicator%3)]+=values[Math.abs(step%6)];
        }
        if(gamepad1.dpadDownWasPressed()){
            P_Kv_Ks[Math.abs(indicator%3)]-=values[Math.abs(step%6)];
        }

        if(gamepad2.rightBumperWasPressed()){
            velTableSteps++;
        }
        if(gamepad2.leftBumperWasPressed()){
            velTableSteps--;
        }
        if(gamepad2.dpadUpWasPressed()){
            targetVelocity += targetVelTable[Math.abs(velTableSteps%4)];
        }
        if(gamepad2.dpadDownWasPressed()){
            targetVelocity -= targetVelTable[Math.abs(velTableSteps%4)];
        }

        if(gamepad2.left_trigger > 0){
            sorter.setPower(1);
            intake.setPower(1);
        }
        if(gamepad2.right_trigger > 0){
            sorter.setPower(-1);
            intake .setPower(-1);
        }
        if(gamepad2.right_trigger > 0 && gamepad2.left_trigger > 0){
            sorter.setPower(0);
            intake.setPower(0);
        }


        shooter.setPower(feedBack + feedForward);

        telemetry.addData("Curr Value: ", values[Math.abs(step%6)]);
        telemetry.addData("P: ", P_Kv_Ks[0]);
        telemetry.addData("Kv: ", P_Kv_Ks[1]);
        telemetry.addData("Ks: ", P_Kv_Ks[2]);
        telemetry.addData("Currently Changing: ", strIndicator);
        telemetry.addData("Target Velocity: ", targetVelocity);
        telemetry.addData("Current Velocity: ", velocity);
        telemetry.addData("Current Changing Velocity by: ", targetVelTable[Math.abs(velTableSteps%4)]);
        telemetry.addData("Pose: ", Arrays.toString(pose));
        telemetry.addData("Target Pose: ", Arrays.toString(targetPose));
        telemetry.addData("Distance: ", distance);

    }
}
