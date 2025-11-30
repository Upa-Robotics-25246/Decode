package org.firstinspires.ftc.teamcode.util.test;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.wpilibcontroller.SimpleMotorFeedforward;

@TeleOp(name = "ABSOLUTE WEEWOO")
public class turretAutoAim extends OpMode {

    DcMotorEx turret;
    Pose trackPoint = new Pose(0,144,135);
    double ppr = 0;

    PIDController controller;
    static double p = 0,i = 0, d =0;
    double gearRatio = 1/(3.625);
    double getYaw(){
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

        double power = controller.calculate(getYaw(),trackPoint.getHeading());

        turret.setPower(power);



    }
}