package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

@TeleOp
public class axonServoTest extends OpMode {

    Servo axon;
    AnalogInput axonEncoder;

    ElapsedTime timer = new ElapsedTime();


    @Override
    public void init() {
        axon = hardwareMap.get(Servo.class, "axonServo");
        axonEncoder = hardwareMap.get(AnalogInput.class, "axonEncoder");
    }

    @Override
    public void loop() {
        double voltage = axonEncoder.getVoltage();

        double x = 72;
        double y = 72;

        double targetX = 96;
        double targetY = 24;

        double xDiff = targetX-x;
        double yDiff = targetY-y;

        double targetAngleRad = Math.atan2(yDiff,xDiff);

        double targetAngleDeg = targetAngleRad/Math.PI * -180;


        double angle = (voltage/3.300538)*360.0;


        double error = targetAngleDeg-angle;

        axon.setPosition(error/135+0.5);

        telemetry.addData("Analog Voltage", "%.3f V", voltage);
        telemetry.addData("Estimated Angle", "%.1f deg", angle);
        telemetry.addData("Error: ", error);
        telemetry.addData("Target Angle: ", targetAngleRad);
        telemetry.addData("Target Voltage: ", targetAngleDeg);
        telemetry.update();

    }
}
