package org.firstinspires.ftc.teamcode.util.test;

import static java.lang.Math.abs;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Configurable
public class BasicDrivetrain extends OpMode {

    public DcMotorEx fr,fl,br,bl;;
    double drive,turn,strafe,FLspeed,FRspeed,BLspeed,BRspeed;

    public static DcMotorSimple.Direction flDirection,frDirection,blDirection,brDirection;
    @Override
    public void init() {
        fr = hardwareMap.get(DcMotorEx.class,"fr");
        fl = hardwareMap.get(DcMotorEx.class,"fl");
        bl = hardwareMap.get(DcMotorEx.class,"bl");
        br = hardwareMap.get(DcMotorEx.class,"br");


        fl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        flDirection = DcMotorEx.Direction.REVERSE;
        frDirection = DcMotorEx.Direction.REVERSE;
        blDirection = DcMotorEx.Direction.REVERSE;
        brDirection = DcMotorEx.Direction.REVERSE;

        fl.setDirection(flDirection);
        bl.setDirection(DcMotorEx.Direction.REVERSE);
        fr.setDirection(DcMotorEx.Direction.FORWARD);
        br.setDirection(DcMotorEx.Direction.REVERSE);

    }

    @Override
    public void loop() {
            drive = (gamepad1.left_stick_y * -1);
            turn = (gamepad1.right_stick_x);
            strafe = (gamepad1.left_stick_x);

            FLspeed = drive + turn + strafe;
            FRspeed = drive - turn - strafe;
            BLspeed = drive + turn - strafe;
            BRspeed = drive - turn + strafe;


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
