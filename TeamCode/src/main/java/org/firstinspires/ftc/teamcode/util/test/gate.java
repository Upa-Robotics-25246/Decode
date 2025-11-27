package org.firstinspires.ftc.teamcode.util.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class gate extends OpMode {
    Servo gate;
    static double pos = 0;//config
    @Override
    public void init() {
        gate = hardwareMap.get(Servo.class,"gate");
    }

    @Override
    public void loop() {
    gate.setPosition(pos);
    }
}
