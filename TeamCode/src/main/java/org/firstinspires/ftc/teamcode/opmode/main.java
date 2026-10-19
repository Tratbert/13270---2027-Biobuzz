//package org.firstinspires.ftc.teamcode.opmode;
//
//import com.pedropathing.follower.Follower;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.pedro.Constants;
//@TeleOp(name = "main")
//public class main extends OpMode {
//
//    DcMotor intake;
//    DcMotor sorter;
//    Servo shooterGate;
//    private Follower follower;
//
//
//    @Override
//    public void init() {
//        intake = hardwareMap.get(DcMotor.class, "intakeMotor");
//        sorter = hardwareMap.get(DcMotor.class, "sorterMotor");
//        shooterGate = hardwareMap.get(Servo.class, "shooterGate");
//        follower = Constants.create(hardwareMap);
//
//    }
//
//    @Override
//    public void loop() {
//        if(gamepad1.right_trigger > 0){
//            intake.setPower(1);
//            sorter.setPower(1);
//        }else{
//            intake.setPower(0);
//            sorter.setPower(0);
//        }
//        if(gamepad1.left_trigger > 0){
//            intake.setPower(-1);
//            sorter.setPower(-1);
//        }else{
//            intake.setPower(0);
//            sorter.setPower(0);
//        }
////        if(gamepad1.aWasPressed()){
////            shooterGate.setPosition(1);
////        }
////        if(gamepad1.bWasPressed()){
////            shooterGate.setPosition(0);
////        }
//        double forward = -gamepad1.left_stick_y;
//        double lateral = gamepad1.left_stick_x;
//        double turn = gamepad1.right_stick_x;
//        follower.manual(forward, lateral, turn);
//        follower.update();
//    }
//}
