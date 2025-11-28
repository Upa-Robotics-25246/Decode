package org.firstinspires.ftc.teamcode.util.test;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.pedro.Constants;

@TeleOp(name = "FlywheelTester+hoodTester")
public class FlywheelTesterAndHood extends OpMode {
    DcMotorEx flywheel;
    Servo hood;
    static double power;
    static double hoodPos;
    static Pose startPose =new Pose(0,0);
    Pose trackPoint = new Pose(0,144,135);

    Follower follower;

    //NEED ODO FOR DISTANCE, ONLY WAY THIS WILL WORK
    @Override
    public void init() {
        flywheel = hardwareMap.get(DcMotorEx.class, "intake");
        hood = hardwareMap.get(Servo.class,"hood");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
    }

    @Override
    public void loop() {
//        double power = gamepad2.left_trigger;
//        double velocity = (6000 *power)* (28/60); // max rpm of the motor is 6000
//        flywheel.setVelocity(velocity);
//        // velocity is the max rpm * power to find the percentage, and then
//        // multiply by ticks per second(28 ticks per revolution/60 seconds) to find overall ticks per sec
//        telemetry.addData("Velocity", flywheel.getVelocity());
//        telemetry.addData("Power", power);
        double absX = trackPoint.getX()-follower.getPose().getX();
        double absY = trackPoint.getY()-follower.getPose().getY();

        double dist = Math.sqrt(
                ((Math.pow(absX,2))+(Math.pow(absY,2)))
        );

        flywheel.setPower(power);
        telemetry.addData("Power", flywheel.getPower());
        telemetry.addData("Velocity", flywheel.getVelocity());
        hood.setPosition(hoodPos);
        telemetry.addData("Hood Pos", hood.getPosition());
        telemetry.addData("distance",dist);
    }
}
