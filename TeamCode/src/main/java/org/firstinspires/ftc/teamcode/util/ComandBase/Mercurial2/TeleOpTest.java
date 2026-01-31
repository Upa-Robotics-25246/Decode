package org.firstinspires.ftc.teamcode.util.ComandBase.Mercurial2;

import static org.firstinspires.ftc.teamcode.util.GlobalVariables.Flyff;
import static org.firstinspires.ftc.teamcode.util.GlobalVariables.FlypidCoefficients;
import static java.lang.Math.abs;

import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.exec;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.loop;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.sequence;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.waitUntil;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import dev.frozenmilk.dairy.mercurial.ftc.Mercurial;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;

public class TeleOpTest {
    private static class State{
        ControlSystem flypidf;
        PIDCoefficients pidCoefficients = FlypidCoefficients;
        BasicFeedforwardParameters ff = Flyff;
        double velocity;
        boolean flywheelPID = false;
        double hoodPos=0;

        enum IntakeTransState{
            INTAKETRANS,
            TRANS,
            EXTAKE,
            OFF
        }
        static State.IntakeTransState intakeTransState = State.IntakeTransState.OFF;
    }

public static Mercurial.RegisterableProgram TeleOpTest = Mercurial.teleop(ctx ->{
    DcMotorEx fr,fl,br,bl,flywheel,intake,transfer;
    State states = new State();

    fr = ctx.hardwareMap().get(DcMotorEx.class,"right_front");
    fl = ctx.hardwareMap().get(DcMotorEx.class,"left_front");
    bl = ctx.hardwareMap().get(DcMotorEx.class,"left_back");
    br = ctx.hardwareMap().get(DcMotorEx.class,"right_back");
    flywheel = ctx.hardwareMap().get(DcMotorEx.class,"flywheel");
    Servo hood = ctx.hardwareMap().get(Servo.class,"hood");
    intake = ctx.hardwareMap().get(DcMotorEx.class,"intake");
    intake.setDirection(DcMotorSimple.Direction.REVERSE);
    transfer = ctx.hardwareMap().get(DcMotorEx.class,"transfer");
    transfer.setDirection(DcMotorSimple.Direction.REVERSE);

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

    states.flypidf = ControlSystem.builder()
            .velPid(states.pidCoefficients)
            .basicFF(states.ff)
            .build();








    ctx.schedule(
            sequence(

                    waitUntil(ctx::inLoop),
                    loop(exec(() -> {
                       double drive = (ctx.gamepad1().left_stick_y * -1);
                        double turn = (ctx.gamepad1().right_stick_x);
                       double strafe = (ctx.gamepad1().left_stick_x);

                        double FLspeed = drive + turn + strafe;
                        double FRspeed = drive - turn - strafe;
                        double BLspeed = drive + turn - strafe;
                        double BRspeed = drive - turn + strafe;


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

                    }))
            )
    );



    //flywheel PID
    ctx.schedule(
            sequence(

                    waitUntil(ctx::inLoop),
                    loop(exec(() -> {
                        if(states.flywheelPID) {
                            states.flypidf.setGoal(new KineticState(0, states.velocity));
                        }else{
                            states.flypidf.setGoal(new KineticState(0, 0));
                        }
                        //velocity = gotten from regression
                        flywheel.setPower(states.flypidf.calculate(new KineticState(
                                0, flywheel.getVelocity())));
                    }))
            )
    );

    ctx.bindSpawn(
            ctx.risingEdge(()-> ctx.gamepad1().right_bumper),exec(()->states.flywheelPID = !states.flywheelPID)
    );
    //HoodPos stuff
    ctx.schedule(
            sequence(

                    waitUntil(ctx::inLoop),
                    loop(exec(() -> {
                       //hoodPos = gotten from regression
                        hood.setPosition(states.hoodPos);
                    }))
            )
    );
    //intake+transfer
    ctx.bindSpawn(
            ctx.risingEdge(()-> ctx.gamepad1().left_bumper),exec(()->{
                switch(State.intakeTransState){
                    case OFF:
                        State.intakeTransState = State.IntakeTransState.INTAKETRANS;

                        break;
                    case EXTAKE:
                        intake.setPower(-0.75);
                        transfer.setPower(-1);
                        break;
                    case INTAKETRANS:
                        intake.setPower(0.75);
                        transfer.setPower(1);
                    case TRANS:
                        intake.setPower(0);
                        transfer.setPower(0.75);
                }
            })
    );
    ctx.schedule(
            sequence(

                    waitUntil(ctx::inLoop),
                    loop(exec(() -> {
                        switch(State.intakeTransState){
                            case OFF:
                                intake.setPower(0);
                                transfer.setPower(0);

                                break;
                            case EXTAKE:
                                intake.setPower(-0.75);
                                transfer.setPower(-1);
                                break;
                            case INTAKETRANS:
                                intake.setPower(0.75);
                                transfer.setPower(1);
                            case TRANS:
                                intake.setPower(0);
                                transfer.setPower(0.75);
                        }
                    }))
            )
    );

});
}
