package org.firstinspires.ftc.teamcode.util.test;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.util.pedro.Constants;
import org.firstinspires.ftc.teamcode.util.pedro.Poses;
@Configurable
@TeleOp(name = "ABSOLUTE WEEWOO")
public class turretAutoAim extends OpMode {

    DcMotorEx turret;
    Follower follower;
    Pose trackPoint = Poses.BlueGoalPos;//(x:0,y:144)
    Pose startPose = Poses.startPoseFarBlue;
    double ppr = 0;// from motor, idk the motor im using rn

    PIDController controller;
    public static double p = 0,i = 0, d =0;
    double gearRatio = 1/(1.675);
    double getDegrees(){
        return turret.getCurrentPosition() * 360.0/(ppr*gearRatio);
    }

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        turret = hardwareMap.get(DcMotorEx.class,"turret");

        controller = new PIDController(p,i,d);

    }

    @Override
    public void loop() {
        double setpoint = Math.atan2(trackPoint.getY()-follower.getPose().getY(),
                trackPoint.getX()-follower.getPose().getX());

        controller.setPID(p,i,d);

        double power = controller.calculate(getDegrees(),Math.toDegrees(setpoint));

        turret.setPower(power);



    }
}