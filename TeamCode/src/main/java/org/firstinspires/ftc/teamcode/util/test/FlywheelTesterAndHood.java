package org.firstinspires.ftc.teamcode.util.test;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.pedro.Constants;
import org.firstinspires.ftc.teamcode.util.GlobalVariables;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;

@TeleOp(name = "FlywheelTester+hoodTester")
public class FlywheelTesterAndHood extends OpMode {
    DcMotorEx flywheel;
    Servo hood;
    static double hoodPos = 0;
    static Pose startPose = GlobalVariables.startPoseFarBlue;
    Pose trackPoint = GlobalVariables.BlueGoalPos;

    Follower follower;
    public static double velocity = 0;



    ControlSystem pidf;
    public static PIDCoefficients pidCoefficients=GlobalVariables.FlypidCoefficients;
    public static BasicFeedforwardParameters ff= GlobalVariables.Flyff;



    //NEED ODO FOR DISTANCE, ONLY WAY THIS WILL WORK
    @Override
    public void init() {
        flywheel = hardwareMap.get(DcMotorEx.class, "transfer");
        hood = hardwareMap.get(Servo.class,"hood");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        pidf = ControlSystem.builder()
                .velPid(pidCoefficients)
                .basicFF(ff)
                .build();

    }

    @Override
    public void loop() {


        pidf.setGoal(new KineticState(0,velocity));




        follower.update();
        double absX = trackPoint.getX()-follower.getPose().getX();
        double absY = trackPoint.getY()-follower.getPose().getY();

        double dist = Math.sqrt(
                ((Math.pow(absX,2))+(Math.pow(absY,2)))
        );

        flywheel.setPower(pidf.calculate(new KineticState(0, flywheel.getVelocity())));
        telemetry.addData("Power", flywheel.getPower());
        telemetry.addData("Velocity", flywheel.getVelocity());
        hood.setPosition(hoodPos);
        telemetry.addData("Hood Pos", hood.getPosition());
        telemetry.addData("distance",dist);
    }
}
