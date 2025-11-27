package org.firstinspires.ftc.teamcode.util.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@TeleOp
public class Intake extends OpMode {
    DcMotorEx intake;

    @Override
    public void init() {
        intake = hardwareMap.get(DcMotorEx.class, "intake");
    }

    @Override
    public void loop() {
        if (gamepad1.rightBumperWasPressed()){
            intake.setPower(1);
        } else if (gamepad1.leftBumperWasPressed()) {
            intake.setPower(0);
        } else if (gamepad1.aWasPressed()) {
            intake.setPower(-1);
        }
    }
}
