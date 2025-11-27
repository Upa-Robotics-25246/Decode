package org.firstinspires.ftc.teamcode.util.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class AirSortOffsetTester extends OpMode {
    Servo hood;
    DcMotorEx flywheel;
    private double pos = 0, power = 0;// will come from quadratic regression
    static double hoodOffset = 0, powerOffset = 0;
    double dist;// need odo
    @Override
    public void init() {
        hood = hardwareMap.get(Servo.class,"hood");
        flywheel = hardwareMap.get(DcMotorEx.class,"flywheel");
    }

    @Override
    public void loop() {
        hood.setPosition(pos+hoodOffset);
        flywheel.setPower(power+powerOffset);
    }
}
