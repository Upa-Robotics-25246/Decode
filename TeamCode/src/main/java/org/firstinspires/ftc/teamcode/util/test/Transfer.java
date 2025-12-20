package org.firstinspires.ftc.teamcode.util.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class Transfer extends OpMode {
    DcMotorEx transfer;

    @Override
    public void init() {
        transfer = hardwareMap.get(DcMotorEx.class, "transfer");
    }

    @Override
    public void loop() {
        if (gamepad1.rightBumperWasPressed()){
            transfer.setPower(1);
        } else if (gamepad1.leftBumperWasPressed()) {
            transfer.setPower(0);
        } else if (gamepad1.aWasPressed()) {
            transfer.setPower(-1);
        }
    }
}
