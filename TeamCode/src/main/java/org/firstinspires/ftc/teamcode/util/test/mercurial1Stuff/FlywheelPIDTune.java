package org.firstinspires.ftc.teamcode.util.test.mercurial1Stuff;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.wpilibcontroller.SimpleMotorFeedforward;

import org.firstinspires.ftc.teamcode.util.pedro.Constants;
import org.firstinspires.ftc.teamcode.util.pedro.Poses;

public class FlywheelPIDTune extends OpMode {
    DcMotorEx flywheel;

    static Pose startPose = Poses.startPoseFarBlue;
    Pose trackPoint = Poses.BlueGoalPos;

    Follower follower;

    PIDController Feedback;
    SimpleMotorFeedforward feedforward;
    static double kP = 0;
    static double kI = 0;
    static double kD = 0;
    static double kS = 0;
    static double kV = 0;
    public static double velocity =0;


    //NEED ODO FOR DISTANCE, ONLY WAY THIS WILL WORK
    @Override
    public void init() {
        flywheel = hardwareMap.get(DcMotorEx.class, "transfer");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        feedforward = new SimpleMotorFeedforward(kS, kV);
        Feedback = new PIDController(kP, kI, kD);
    }

    @Override
    public void loop() {

        Feedback.setPID(kP, kI, kD);
        //velocity is gotten from the regression, change once you get the regression working
        double power = Feedback.calculate(flywheel.getVelocity(), velocity);
        double ff = feedforward.calculate(velocity);

        //hoodPos is also gotten from the regression


        follower.update();
        double absX = trackPoint.getX() - follower.getPose().getX();
        double absY = trackPoint.getY() - follower.getPose().getY();

        double dist = Math.sqrt(
                ((Math.pow(absX, 2)) + (Math.pow(absY, 2)))
        );


        flywheel.setPower(power + ff);
        telemetry.addData("Power", flywheel.getPower());
        telemetry.addData("Velocity", flywheel.getVelocity());
        telemetry.addData("distance", dist);
    }

}