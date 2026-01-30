package org.firstinspires.ftc.teamcode.util.test.mercurial1Stuff;

import com.bylazar.configurables.annotations.Configurable;
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
@Configurable
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
    static double velocity =0;



    @Override
    public void init() {
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        feedforward = new SimpleMotorFeedforward(kS, kV);
        Feedback = new PIDController(kP, kI, kD);
    }


    @Override
    public void loop() {

        Feedback.setPID(kP, kI, kD);

        double power = Feedback.calculate(flywheel.getVelocity(), velocity);
        double ff = feedforward.calculate(velocity);








        flywheel.setPower(power + ff);
        telemetry.addData("Power", flywheel.getPower());
        telemetry.addData("Velocity", flywheel.getVelocity());
    }

}