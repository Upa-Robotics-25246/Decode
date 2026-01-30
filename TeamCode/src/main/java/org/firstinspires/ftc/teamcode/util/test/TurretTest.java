package org.firstinspires.ftc.teamcode.util.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "Turret first test")
public class TurretTest extends OpMode {
   DcMotorEx turret;
   Servo hood;
   double hoodPos = 0;
   double TurretPower = 0;
    @Override
    public void init() {
        turret = hardwareMap.get(DcMotorEx.class,"turret");
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hood = hardwareMap.get(Servo.class,"hood");
        hood.setPosition(0);
        turret.setTargetPosition(0);

    }

    @Override
    public void loop() {
        if (gamepad1.dpadUpWasPressed()){
            hoodPos+=0.05;
        }else if(gamepad1.dpadDownWasPressed()){
            hoodPos-=0.05;
        }
        if (hoodPos>1){
            hoodPos = 1;
        }else if(hoodPos<0){
            hoodPos = 0;
        }
        if(gamepad1.dpadRightWasPressed()){
            hood.setPosition(hoodPos);

        }
        if (gamepad1.yWasPressed()){
            TurretPower+=0.05;
        }else if(gamepad1.aWasPressed()){
            TurretPower-=0.05;
        }
        if (TurretPower>1) {
            TurretPower = 1;
        } else if(TurretPower<-1){
            TurretPower = -1;
        }
        if(gamepad1.xWasPressed()){

            turret.setPower(TurretPower);

        }

        telemetry.addData("hoodPos: ",hoodPos);
        telemetry.addData("TurretPower: ",TurretPower);
        telemetry.addData("Is x pressed?", gamepad1.x);
        telemetry.addData("Is right pressed?",gamepad1.dpad_right);
        telemetry.update();

    }
}
