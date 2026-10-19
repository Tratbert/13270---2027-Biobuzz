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
    double kp = 0.0059;
    double ki = 0.0054;
    double kd = 0.0007;
    double integralSum = 0;
    double lastError = 0;
    double lastAngle = 0;
    double[] steps = {0.00001,0.0001,0.001,0.01,0.1,1};
    int Step = 0;

    double maxIntegralSum = 46.3;
    double a = 1;
    double previousEstimate = 0;
    double currentEstimate = 0;



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
            kp += steps[Step%6];
        }
        if(gamepad1.aWasPressed()){
            kp -= steps[Step%6];
        }
        if(gamepad1.dpadUpWasPressed()){
            ki += steps[Step%6];
        }
        if(gamepad1.dpadDownWasPressed()){
            ki -= steps[Step%6];
        }
        if(gamepad1.dpadRightWasPressed()){
            kd += steps[Step%6];
        }
        if(gamepad1.dpadLeftWasPressed()){
            kd -= steps[Step%6];
        }
        if(gamepad1.xWasPressed()){
            a += steps[Step%6];
        }
        if(gamepad1.bWasPressed()){
            a-= steps[Step%6];
        }
        telemetry.addData("ki: ", ki);
        telemetry.addData("kp: ", kp);
        telemetry.addData("kd ", kd);
        telemetry.addData("a: ",a);
        telemetry.addData("Step: ", Step);
        telemetry.addData("Steps: ", steps[Step%6]);

        double voltage = axonEncoder.getVoltage();

        double x = 72;
        double y = 72;

        double targetX = 96;
        double targetY = 96;

        double xDiff = targetX-x;
        double yDiff = targetY-y;

        double targetAngleRad = Math.atan2(yDiff,xDiff) - Math.PI;

        double targetAngleDeg = targetAngleRad/Math.PI * -180;

        double angle = (voltage/3.300538)*360.0;


        double error = targetAngleDeg-angle;
        double errorChange = (error - lastError);
        currentEstimate = (a*previousEstimate) + (1-a) * errorChange;
        previousEstimate = currentEstimate;
        double derivitive = (currentEstimate)/ timer.seconds();
        integralSum = integralSum+(error* timer.seconds());
        if(integralSum > maxIntegralSum){
            integralSum = maxIntegralSum;
        }
        if(integralSum < -maxIntegralSum){
            integralSum = -maxIntegralSum;
        }
//        if(angle != lastAngle){
//            integralSum = 0;
//        }
        double out = (kp*error) + (ki*integralSum) + (kd*derivitive);
        telemetry.addData("Out" , out);

        if(out < -0.5){
            out = -0.5;
        }
        if(out > 0.5){
            out = 0.5;
        }

        axon.setPosition(out+0.5);

        lastError = error;
        lastAngle = angle;

        timer.reset();

        telemetry.addData("Analog Voltage", "%.3f V", voltage);
        telemetry.addData("Estimated Angle", "%.1f deg", angle);
        telemetry.addData("Error: ", error);
        telemetry.addData("Target Angle: ", targetAngleDeg);
        telemetry.addData("Timer: ", timer);
        telemetry.addData("Integral Sum: ", integralSum);
        telemetry.addData("Derivative: ", derivitive);
        telemetry.update();

    }
}
