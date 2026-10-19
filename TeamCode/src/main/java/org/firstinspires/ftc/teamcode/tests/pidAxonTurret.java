package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

@TeleOp
public class pidAxonTurret extends OpMode {

    Servo axon;
    AnalogInput axonEncoder;
    double kp = 0;
    double ki = 0;
    double kd = 0;
    double integralSum = 0;
    double lastError = 0;
    double[] steps = {0.001,0.01,0.1,1};
    int Step = 0;


    ElapsedTime timer = new ElapsedTime();


    @Override
    public void init() {
        axon = hardwareMap.get(Servo.class, "axonServo");
        axonEncoder = hardwareMap.get(AnalogInput.class, "axonEncoder");
    }

    @Override
    public void loop() {

        if(gamepad1.rightBumperWasPressed()){
            Step++;
        }
        if(gamepad1.leftBumperWasPressed()){
            Step--;
        }
        if(gamepad1.yWasPressed()){
            kp += steps[Step%4];
        }
        if(gamepad1.aWasPressed()){
            kp -= steps[Step%4];
        }
        if(gamepad1.dpadUpWasPressed()){
            ki += steps[Step%4];
        }
        if(gamepad1.dpadDownWasPressed()){
            ki -= steps[Step%4];
        }
        if(gamepad1.dpadRightWasPressed()){
            kd += steps[Step%4];
        }
        if(gamepad1.dpadLeftWasPressed()){
            kd -= steps[Step%4];
        }
        telemetry.addData("ki: ", ki);
        telemetry.addData("kp: ", kp);
        telemetry.addData("kd ", kd);
        double voltage = axonEncoder.getVoltage();

        double x = 72;
        double y = 72;

        double targetX = 96;
        double targetY = 24;

        double xDiff = targetX-x;
        double yDiff = targetY-y;

        double targetAngleRad = Math.atan2(yDiff,xDiff)+Math.PI;

        double targetAngleDeg = targetAngleRad*180/Math.PI;

        double angle = (voltage/3.300538)*360.0;


        double error = targetAngleDeg-angle;
        double derivitive = (error-lastError)/ timer.seconds();
        integralSum = integralSum+(error* timer.seconds());
        double out = (kp*error) + (ki*integralSum) + (kd*derivitive);


        axon.setPosition(out);

        lastError = error;

        timer.reset();

        telemetry.addData("Analog Voltage", "%.3f V", voltage);
        telemetry.addData("Estimated Angle", "%.1f deg", angle);
        telemetry.addData("Error: ", error);
        telemetry.addData("Target Angle: ", targetAngleDeg);
        telemetry.update();

    }
}
