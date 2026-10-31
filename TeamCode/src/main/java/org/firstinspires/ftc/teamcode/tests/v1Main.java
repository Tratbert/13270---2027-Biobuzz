package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class v1Main extends OpMode {

    DcMotor intake;
    DcMotor sorter;
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    private DcMotor nectarShooter;

    private DcMotor pollenShooter;
//    Servo shooterGate;


    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class, "intakeMotor");
        sorter = hardwareMap.get(DcMotor.class, "sorterMotor");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        nectarShooter = hardwareMap.get(DcMotor.class,"nectarShooter");
        pollenShooter = hardwareMap.get(DcMotor.class,"pollenShooter");
//        shooterGate = hardwareMap.get(Servo.class, "shooterGate");

    }

    @Override
    public void loop() {

        double max;
        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral =  gamepad1.left_stick_x;
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
        leftFront.setPower(leftFrontPower*1.6);
        rightFront.setPower(rightFrontPower*1.6);
        leftBack.setPower(leftBackPower*1.6); // had to fix both backs to drive
        rightBack.setPower(rightBackPower*1.6);

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
        if(gamepad2.right_trigger > 0){
            pollenShooter.setPower(1);
            nectarShooter.setPower(1);

        }
        if(gamepad2.left_trigger > 0){
            nectarShooter.setPower(0.75);
            pollenShooter.setPower(0.75);

        }
        if(gamepad2.right_trigger == 0 && gamepad2.left_trigger == 0){
            nectarShooter.setPower(0);
            pollenShooter.setPower(0);

        }
//        if(gamepad1.aWasPressed()){
//            shooterGate.setPosition(1);
//        }
//        if(gamepad1.bWasPressed()){
//            shooterGate.setPosition(0);
//        }
    }
}
