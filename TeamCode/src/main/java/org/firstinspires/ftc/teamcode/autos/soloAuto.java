//package org.firstinspires.ftc.teamcode.autos;
//
//import com.pedropathing.api.PoseFactory;
//import com.pedropathing.follower.Follower;
//import org.firstinspires.ftc.teamcode.pedro.Constants;
//import static com.pedropathing.api.Paths.*;
//import com.pedropathing.paths.Path;
//import com.pedropathing.math.Pose;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//@Autonomous
//public class soloAuto extends OpMode {
//
//    private Follower follower;
//    private final PoseFactory p = PoseFactory.degrees();
//
//    private final Pose startPose = p.of(24,9,90); //gotta change to the real one
//    private final Pose park = p.of(9,9,90); // again, gotta change this
//
//    private Path park(){
//        return line(startPose, park).linear(startPose, park);
//    }
//
//    @Override
//    public void init() {
//        follower = Constants.create(hardwareMap);
//        follower.setPose(startPose);
//    }
//
//    @Override
//    public void start(){
//
//    }
//
//    @Override
//    public void loop() {
//
//    }
//}
