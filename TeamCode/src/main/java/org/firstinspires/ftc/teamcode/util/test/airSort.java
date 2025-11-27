package org.firstinspires.ftc.teamcode.util.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.util.InterpLUT;

public class airSort extends OpMode {
    double hoodPos = 0; // based on quadratic regression or whatever its called
    double flywheelVel = 0;//based on quadratic regression or wtv

    Servo hood;
    DcMotorEx flywheel;

    InterpLUT highArc = new InterpLUT();
    InterpLUT lowArc = new InterpLUT();

    double dist;// based on odometery

    enum motifPatterns {
        PPG,
        PGP,
        GPP
    }

    motifPatterns motifPattern = motifPatterns.PPG;//placeholder

    enum possibleStates {
        PPG,
        PGP,
        GPP,
        OTHER
//        PPP,
//        GGP,
//        PGG,
//        GGG,
//        PP,
//        PG,
//        GP,
//        GG,
//        G,
//        P
    }

    possibleStates state = possibleStates.PPG;


    @Override
    public void init() {
        HighArcInit();
        lowArcinit();
        hood = hardwareMap.get(Servo.class, "hood");
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
    }

    @Override
    public void loop() {
        if (motifPattern == motifPatterns.PPG) {
            switch (state) {
                case PPG:
                    lowArcShoot();
                    middleArcShoot();
                    highArcShoot();
                    break;
                case PGP:
                    lowArcShoot();
                    highArcShoot();
                    middleArcShoot();
                    break;
                case GPP:
                    lowArcShoot();
                    highArcShoot();
                    middleArcShoot();
                    break;
                case OTHER:
                    lowArcShoot();
                    middleArcShoot();
                    highArcShoot();
                    break;
            }
        } else if (motifPattern == motifPatterns.PGP) {
            switch (state) {
                case PPG:
                    lowArcShoot();
                    highArcShoot();
                    middleArcShoot();
                    break;
                case PGP:
                    lowArcShoot();
                    middleArcShoot();
                    highArcShoot();
                    break;
                case GPP:
                    middleArcShoot();
                    lowArcShoot();
                    highArcShoot();
                    break;
                case OTHER:
                    lowArcShoot();
                    middleArcShoot();
                    highArcShoot();
                    break;
            }
        } else if (motifPattern == motifPatterns.GPP) {
            switch (state) {
                case PPG:
                    highArcShoot();
                    middleArcShoot();
                    lowArcShoot();
                    break;
                case PGP:
                    lowArcShoot();
                    highArcShoot();
                    middleArcShoot();
                    break;
                case GPP:
                    lowArcShoot();
                    middleArcShoot();
                    highArcShoot();
                    break;
                case OTHER:
                    lowArcShoot();
                    middleArcShoot();
                    highArcShoot();
                    break;
            }

        }
    }


    public void HighArcInit() {
        double distance = 0;// placeholders
        double hoodPosOffset = 0;
        highArc.add(distance, hoodPosOffset);
        highArc.createLUT();

    }

    public void lowArcinit() {
        double distance = 0;// placeholders
        double hoodPosOffset = 0;
        lowArc.add(distance, hoodPosOffset);
        lowArc.createLUT();

    }


    public void lowArcShoot() {

        hood.setPosition(hoodPos + lowArc.get(dist));// when the angle is raised,it gives lower arc(i hope im thinking this right)
        flywheel.setPower(flywheelVel);// prob gonna be some math to convert flywheel vel into
        //power, so that its accurate

        //transfer code

    }

    public void middleArcShoot() {
        hood.setPosition(hoodPos);
        flywheel.setPower(flywheelVel);


        //transfer code
    }

    public void highArcShoot() {
        hood.setPosition(hoodPos - highArc.get(dist));// lower angle, higher arc
        flywheel.setPower(flywheelVel);// i may add more power to this for more accurate thing, idk

        //transfer code
    }
}

