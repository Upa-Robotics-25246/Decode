package org.firstinspires.ftc.teamcode.util.test;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class BasicDrivetrain extends OpMode {

    public DcMotorEx fr,fl,br,bl;
    public double slowSpeed = 1;
    double drive,turn,strafe,FLspeed,FRspeed,BLspeed,BRspeed;

    @Override
    public void init() {
        fr = hardwareMap.get(DcMotorEx.class,"right_front");
        fl = hardwareMap.get(DcMotorEx.class,"left_front");
        bl = hardwareMap.get(DcMotorEx.class,"left_back");
        br = hardwareMap.get(DcMotorEx.class,"right_back");

        // other stuff for init of motors/ servos go here
        fl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        //3 reversed cus we accidentally flipped one of the wires prob need to fix soon
        fl.setDirection(DcMotorEx.Direction.REVERSE);
        bl.setDirection(DcMotorEx.Direction.REVERSE);
        fr.setDirection(DcMotorEx.Direction.FORWARD);
        br.setDirection(DcMotorEx.Direction.REVERSE);

    }

    @Override
    public void loop() {
            drive = (gamepad1.left_stick_y * -1)* slowSpeed;
            turn = (gamepad1.right_stick_x)*slowSpeed;
            strafe = (gamepad1.left_stick_x)*slowSpeed ;

            FLspeed = drive + turn + strafe;
            FRspeed = drive - turn - strafe;
            BLspeed = drive + turn - strafe;
            BRspeed = drive - turn + strafe;

            // Scaling Drive Powers Proportionally
            double maxF = Math.max((abs(FLspeed)),(abs(FRspeed)));
            double maxB = Math.max((abs(BLspeed)),(abs(BRspeed)));
            double maxFB_speed = Math.max(abs(maxF), abs(maxB));

            if(maxFB_speed > 1){
                FLspeed = FLspeed / maxFB_speed;
                FRspeed = FRspeed / maxFB_speed;
                BLspeed = BLspeed / maxFB_speed;
                BRspeed = BRspeed / maxFB_speed;
            }

            fl.setPower(FLspeed);
            fr.setPower(FRspeed);
            bl.setPower(BLspeed);
            br.setPower(BRspeed);

    }
}
