package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class v1Main extends OpMode {

    DcMotor intake;
    DcMotor sorter;
//    Servo shooterGate;


    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class, "intakeMotor");
        sorter = hardwareMap.get(DcMotor.class, "sorterMotor");
//        shooterGate = hardwareMap.get(Servo.class, "shooterGate");

    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > 0){
            intake.setPower(1);
            sorter.setPower(1);
        }
        if(gamepad1.left_trigger > 0){
            intake.setPower(-1);
            sorter.setPower(-1);
        }
        if(gamepad1.left_trigger == 0 && gamepad1.right_trigger ==0){
            intake.setPower(0);
            sorter.setPower(0);
        }
//        if(gamepad1.aWasPressed()){
//            shooterGate.setPosition(1);
//        }
//        if(gamepad1.bWasPressed()){
//            shooterGate.setPosition(0);
//        }
    }
}
