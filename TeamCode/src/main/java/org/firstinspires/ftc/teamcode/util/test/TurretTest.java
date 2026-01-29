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
   int TurretPos = 0;
    @Override
    public void init() {
        turret = hardwareMap.get(DcMotorEx.class,"turret");
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hood = hardwareMap.get(Servo.class,"hood");
        hood.setPosition(0);
        turret.setTargetPosition(0);

    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up){
            hoodPos+=0.01;
        }else if(gamepad1.dpad_down){
            hoodPos-=0.01;
        }
        if (hoodPos>1){
            hoodPos = 1;
        }else if(hoodPos<0){
            hoodPos = 0;
        }
        if(gamepad1.dpadRightWasPressed()){
            hood.setPosition(hoodPos);
        }
        if (gamepad1.y){
            TurretPos+=1;
        }else if(gamepad1.a){
            TurretPos-=1;
        }
        if(TurretPos<0){
            TurretPos = 0;
        }
        if(gamepad1.xWasPressed()){
            hood.setPosition(TurretPos);
        }

        telemetry.addData("hoodPos: ",hoodPos);
        telemetry.addData("TurretPos: ",TurretPos);
        telemetry.update();
    }
}
