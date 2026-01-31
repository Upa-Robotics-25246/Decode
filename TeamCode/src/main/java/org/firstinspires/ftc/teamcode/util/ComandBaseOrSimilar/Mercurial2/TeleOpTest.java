package org.firstinspires.ftc.teamcode.util.ComandBaseOrSimilar.Mercurial2;

import static org.firstinspires.ftc.teamcode.util.GlobalVariables.Flyff;
import static org.firstinspires.ftc.teamcode.util.GlobalVariables.FlypidCoefficients;
import static org.firstinspires.ftc.teamcode.util.GlobalVariables.blDirection;
import static org.firstinspires.ftc.teamcode.util.GlobalVariables.brDirection;
import static org.firstinspires.ftc.teamcode.util.GlobalVariables.flDirection;
import static org.firstinspires.ftc.teamcode.util.GlobalVariables.frDirection;
import static java.lang.Math.abs;

import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.exec;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.loop;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.sequence;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.waitSeconds;
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
        double velocity = 1000;
        boolean flywheelPID = false;
        double hoodPos=0;

        enum IntakeTransState{
            INTAKETRANS,
            TRANS,
            EXTAKE,
            EXTRANS,
            OFF
        }
        static State.IntakeTransState intakeTransState = State.IntakeTransState.OFF;
    }

public static Mercurial.RegisterableProgram TeleOpTest = Mercurial.teleop(ctx ->{
    DcMotorEx fr,fl,br,bl,flywheel,intake,transfer;
    State states = new State();

    fr = ctx.hardwareMap().get(DcMotorEx.class,"fr");
    fl = ctx.hardwareMap().get(DcMotorEx.class,"fl");
    bl = ctx.hardwareMap().get(DcMotorEx.class,"bl");
    br = ctx.hardwareMap().get(DcMotorEx.class,"br");
    flywheel = ctx.hardwareMap().get(DcMotorEx.class,"flywheel");
    flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
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
    fl.setDirection(flDirection);
    bl.setDirection(blDirection);
    fr.setDirection(frDirection);
    br.setDirection(brDirection);

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
                            flywheel.setPower(states.flypidf.calculate(new KineticState(
                                0, flywheel.getVelocity())));
                            states.flypidf.setGoal(new KineticState(0, states.velocity));


                        }else{
                            states.flypidf.setGoal(new KineticState(0, 0));
                            flywheel.setPower(0);
                        }
                        //velocity = gotten from regression

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
                        State.intakeTransState = State.IntakeTransState.OFF;
                        break;
                    case INTAKETRANS:
                        State.intakeTransState = State.IntakeTransState.OFF;
                        break;
                    case TRANS:
                        State.intakeTransState = State.IntakeTransState.OFF;
                        break;
                    case EXTRANS:
                        State.intakeTransState = State.intakeTransState.OFF;
                        break;
                }
            })
    );
    //transfer
    ctx.bindSpawn(
            ctx.risingEdge(()-> ctx.gamepad1().a),exec(()->{
                switch(State.intakeTransState){
                    case OFF:
                        State.intakeTransState = State.IntakeTransState.TRANS;

                        break;
                    case EXTAKE:
                        State.intakeTransState = State.IntakeTransState.OFF;
                        break;
                    case INTAKETRANS:
                        State.intakeTransState = State.IntakeTransState.OFF;
                        break;
                    case TRANS:
                        State.intakeTransState = State.IntakeTransState.OFF;
                        break;
                    case EXTRANS:
                        State.intakeTransState = State.intakeTransState.OFF;
                        break;
                }
            })
    );
    //extake
    ctx.bindSpawn(
            ctx.risingEdge(()-> ctx.gamepad1().x),exec(()->{
                switch(State.intakeTransState){
                    case OFF:
                        State.intakeTransState = State.IntakeTransState.EXTRANS;

                        break;
                    case EXTAKE:
                        State.intakeTransState = State.IntakeTransState.OFF;
                        break;
                    case INTAKETRANS:
                        State.intakeTransState = State.IntakeTransState.OFF;
                        break;
                    case TRANS:
                        State.intakeTransState = State.IntakeTransState.OFF;
                        break;
                    case EXTRANS:
                        State.intakeTransState = State.intakeTransState.OFF;
                        break;
                }
            })
    );
    //extrans
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
                                intake.setPower(-1);
                                transfer.setPower(-1);
                                break;
                            case INTAKETRANS:
                                intake.setPower(1);
                                transfer.setPower(1);
                                break;
                            case TRANS:
                                intake.setPower(0);
                                transfer.setPower(1);
                                break;
                            case EXTRANS:
                                transfer.setPower(-1);
                                intake.setPower(0);
                        }
                    }))
            )
    );
    ctx.dropToScheduler();

});
}
