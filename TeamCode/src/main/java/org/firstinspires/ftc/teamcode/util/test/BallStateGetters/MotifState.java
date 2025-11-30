package org.firstinspires.ftc.teamcode.util.test.BallStateGetters;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class MotifState extends OpMode {

    ColorSensor c1;
    ColorSensor c2;
    ColorSensor c3;

    @Override
    public void init() {
        c1 = hardwareMap.get(ColorSensor.class,"c1");
        c2 = hardwareMap.get(ColorSensor.class,"c2");
        c3 = hardwareMap.get(ColorSensor.class,"c3");
    }

    @Override
    public void loop() {

    }
}