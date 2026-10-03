package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class testRobot extends OpMode {
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;
//    private DcMotor intakeMotor;
//    private DcMotor shooterPollen;
//    private DcMotor shooterNectar;
//    private Servo intakeServo;
    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
//        hardwareMap.get(DcMotor.class, "shooterPollen");
//        hardwareMap.get(DcMotor.class, "shooterNectar");
//        hardwareMap.get(Servo.class, "intakeServo");
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




//        if (gamepad1.right_trigger > 0) {
//            // turn right
//            leftFront.setPower(0.5);
//            leftBack.setPower(0.5);
//            rightFront.setPower(-0.5);
//            rightBack.setPower(-0.5);
//        } else if (gamepad1.left_trigger > 0) {
//            // turn left
//            leftFront.setPower(-0.5);
//            leftBack.setPower(-0.5);
//            rightFront.setPower(0.5);
//            rightBack.setPower(0.5);
//        }else if (gamepad1.aWasPressed()) {
//            // backwards
//            leftFront.setPower(-0.5);
//            leftBack.setPower(-0.5);
//            rightFront.setPower(-0.5);
//            rightBack.setPower(-0.5);
//        } else if (gamepad1.yWasPressed()) {
//            // forwards
//            leftFront.setPower(0.5);
//            leftBack.setPower(0.5);
//            rightFront.setPower(0.5);
//            rightBack.setPower(0.5);
//        }
//        if (gamepad1.xWasPressed()) {
//            // shooting pollen
//            shooterPollen.setPower(0.5);
//        }
//        if (gamepad1.xWasPressed()) {
//            // shooting nectar
//            shooterNectar.setPower(0.5);
//        }

    }
}
