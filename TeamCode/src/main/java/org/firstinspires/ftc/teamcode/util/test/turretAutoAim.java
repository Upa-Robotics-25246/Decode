package org.firstinspires.ftc.teamcode.util.test;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.util.pedro.Poses;

@TeleOp(name = "ABSOLUTE WEEWOO")
public class turretAutoAim extends OpMode {

    DcMotorEx turret;
    Pose trackPoint = Poses.trackPoint;//(x:0,y:144:heading:Math.toRadians(135)
    double ppr = 0;// from motor, idk the motor im using rn

    PIDController controller;
    static double p = 0,i = 0, d =0;
    double gearRatio = 1/(3.625);
    double getDegrees(){
        return turret.getCurrentPosition() * 360.0/(ppr*gearRatio);
    }

    @Override
    public void init() {
        turret = hardwareMap.get(DcMotorEx.class,"turret");

        controller = new PIDController(p,i,d);

    }

    @Override
    public void loop() {


        controller.setPID(p,i,d);

        double power = controller.calculate(getDegrees(),Math.toDegrees(trackPoint.getHeading()));

        turret.setPower(power);



    }
}