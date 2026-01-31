package org.firstinspires.ftc.teamcode.TeleOp;

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
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.waitUntil;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import dev.frozenmilk.dairy.mercurial.ftc.Mercurial;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;

public class TeleOpGeneric {

    private static class State{
        ControlSystem flypidf;
        PIDCoefficients pidCoefficients = FlypidCoefficients;
        BasicFeedforwardParameters ff = Flyff;
        double velocity = 1800;
        boolean flywheelPID = false;
        double hoodPos=0;
        boolean flywheelReversed = false;


        enum IntakeState{
            FORWARD,
            REVERSE,
            OFF
        }
        static IntakeState intakeState = IntakeState.OFF;
        enum TransferState{
            FORWARD,
            REVERSE,
            OFF
        }
        static TransferState transferState = TransferState.OFF;
    }

    public static Mercurial.RegisterableProgram TeleOpGeneric = Mercurial.teleop(ctx ->{

        DcMotorEx fr,fl,br,bl,flywheel,intake,transfer,turret;
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
        turret = ctx.hardwareMap().get(DcMotorEx.class,"turret");

        // other stuff for init of motors/ servos go here
        fl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


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
                            if(!states.flywheelReversed) {
                                if (states.flywheelPID) {
                                    flywheel.setPower(states.flypidf.calculate(new KineticState(
                                            0, flywheel.getVelocity())));
                                    states.flypidf.setGoal(new KineticState(0, states.velocity));

                                } else {
                                    states.flypidf.setGoal(new KineticState(0, 0));
                                    flywheel.setPower(0);
                                }
                                //velocity = gotten from regression
                            }else{
                                if (states.flywheelPID) {
                                    flywheel.setPower(states.flypidf.calculate(new KineticState(
                                            0, flywheel.getVelocity())));
                                    states.flypidf.setGoal(new KineticState(0, -states.velocity));

                                } else {
                                    states.flypidf.setGoal(new KineticState(0, 0));
                                    flywheel.setPower(0);
                                }
                            }
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
        //intake
        ctx.bindSpawn(
                ctx.risingEdge(()-> ctx.gamepad1().left_bumper),exec(()-> {
                    switch (State.intakeState) {
                        case OFF:
                            State.intakeState = State.IntakeState.FORWARD;

                        case FORWARD:
                            State.intakeState = State.IntakeState.OFF;
                        case REVERSE:
                            State.intakeState = State.IntakeState.FORWARD;
                    }

                })
        );
        //transfer
        ctx.bindSpawn(
                ctx.risingEdge(()-> ctx.gamepad1().a),exec(()->{
                    switch (State.transferState) {
                        case OFF:
                            State.transferState = State.TransferState.FORWARD;

                        case FORWARD:
                            State.transferState = State.TransferState.OFF;
                        case REVERSE:
                            State.transferState = State.TransferState.FORWARD;
                    }


                })
        );
        //extake
        ctx.bindSpawn(
                ctx.risingEdge(()-> ctx.gamepad1().x),exec(()->{
                    switch (State.intakeState) {
                        case OFF:
                            State.intakeState = State.IntakeState.REVERSE;

                        case FORWARD:
                            State.intakeState = State.IntakeState.REVERSE;
                        case REVERSE:
                            State.intakeState = State.IntakeState.OFF;
                    }
                })
        );
        //extrans
        ctx.bindSpawn(
                ctx.risingEdge(()-> ctx.gamepad1().b),exec(()->{
                    switch (State.transferState) {
                        case OFF:
                            State.transferState = State.TransferState.REVERSE;

                        case FORWARD:
                            State.transferState = State.TransferState.REVERSE;
                        case REVERSE:
                            State.transferState = State.TransferState.OFF;
                    }

                })
        );
        ctx.bindSpawn(
                ctx.risingEdge(()-> ctx.gamepad1().dpad_down),exec(()->{
                    states.flywheelReversed = !states.flywheelReversed;
                })
        );


        ctx.schedule(
                sequence(

                        waitUntil(ctx::inLoop),
                        loop(exec(() -> {
                            switch(State.transferState){
                                case OFF:
                                    transfer.setPower(0);
                                    break;
                                case FORWARD:
                                    transfer.setPower(1);
                                case REVERSE:
                                    transfer.setPower(-1);
                            }
                            switch(State.intakeState){
                                case OFF:
                                    intake.setPower(0);
                                    break;
                                case FORWARD:
                                    intake.setPower(1);
                                case REVERSE:
                                    intake.setPower(-1);
                            }

                        }))
                )
        );


        ctx.bindSpawn(
                ctx.risingEdge(()-> ctx.gamepad1().right_trigger>0.1),
                exec(()->{
                    turret.setPower(ctx.gamepad1().right_trigger);
                })
        );
        ctx.bindSpawn(
                ctx.risingEdge(()-> ctx.gamepad1().left_trigger>0.1),
                exec(()->{
                    turret.setPower(-ctx.gamepad1().left_trigger);
                })
        );
        ctx.bindSpawn(
                ctx.risingEdge(()-> ctx.gamepad1().dpad_up),
                exec(()->{
                    hood.setPosition(hood.getPosition()+0.05);
                })
        );




        ctx.dropToScheduler();

    });
}
