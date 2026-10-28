package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@TeleOp
public class motorTurretTest extends OpMode {
    DcMotor turret;
    double x = 24;
    double y = 120;
    double targetX = 48;
    double targetY = 24;
    double angle = Math.atan2(24-120,48-24);
    double ticksToDegrees = 273;
    double heading = (3 * Math.PI)/2;




    @Override
    public void init() {
        turret = hardwareMap.get(DcMotor.class,"turret");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        turret.setTargetPosition((int)(((angle-heading)/Math.PI) * 273));
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("ticks: ", turret.getCurrentPosition());
        turret.setPower(0.5);
    }
}
