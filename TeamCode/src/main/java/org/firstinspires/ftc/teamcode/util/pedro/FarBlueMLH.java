package org.firstinspires.ftc.teamcode.util.pedro;

import static org.firstinspires.ftc.teamcode.util.GlobalVariables.Flyff;
import static org.firstinspires.ftc.teamcode.util.GlobalVariables.FlypidCoefficients;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.exec;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.parallel;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.sequence;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.waitSeconds;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.waitUntil;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.function.BooleanSupplier;

import dev.frozenmilk.dairy.mercurial.ftc.Mercurial;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;

public class FarBlueMLH {
    public static PathChain pickupmiddle;
    public static PathChain Diaganolfing;
    public static PathChain opengate;
    public static PathChain backtospawn;

    public static PathChain pickuplow;

    public static PathChain backToSpawn3;
    public static PathChain BackToSpawn2;
    public static PathChain pickuphp;
    static Follower  follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;

    public static class State {

        public void buildPaths() {
            //shoot
            pickupmiddle = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(56.000, 8.000),
                                    new Pose(55.752, 70.288),
                                    new Pose(42.504, 58.768),
                                    new Pose(12.224, 59.040)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(180))

                    .build();

            Diaganolfing = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(12.224, 59.040),

                                    new Pose(22.064, 70.560)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(180))

                    .build();

            opengate = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(22.064, 70.560),

                                    new Pose(16.336, 70.088)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(180))

                    .build();

            backtospawn = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(16.336, 70.088),

                                    new Pose(56.000, 12.000)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(180))
                    .setReversed()
                    .build();

            //shoot


            pickuplow = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(56.000, 10.000),
                                    new Pose(43.900, 39.256),
                                    new Pose(38.596, 36.528),
                                    new Pose(13.056, 36.096)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(180))

                    .build();


            BackToSpawn2 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(13.272, 36.112),

                                    new Pose(56.000, 10.000)
                            )
                    ).setTangentHeadingInterpolation()
                    .setReversed()
                    .build();
            //shoot
            pickuphp = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(56.000, 10.000),

                                    new Pose(9.888, 7.760)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(180))

                    .build();

            backToSpawn3 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(9.888, 7.760),

                                    new Pose(56.000, 10.000)
                            )
                    ).setTangentHeadingInterpolation()
                    .setReversed()
                    .build();

            //shoot

        }

        public void autonomousPathUpdate() {
//            switch (pathState) {
//                case 0:
//                    parallel(//shootcode,
//                            waitSeconds(1)
//                    );
//                    setPathState(1);
//                    break;
//                case 1:
//
//                    if (!follower.isBusy()) {
//                        follower.followPath(pickupmiddle, true);
//                        setPathState(2);
//                    }
//                    break;
//                case 2:
//                    if (!follower.isBusy()){
//                        follower.followPath(Diaganolfing,true);
//                        setPathState(3);
//                    }
//                   break;
//                case 3:
//                    if (!follower.isBusy()){
//                        follower.followPath(opengate,true);
//                        setPathState(4);
//                    }
//                   break;
//                case 4:
//                    if (!follower.isBusy()){
//                        follower.followPath(backtospawn,true);
//                        setPathState(5);
//                    }
//                   break;
//                case 5:
//                    if (!follower.isBusy()){
//                        parallel(
//                                exec(()->{/*shoot code*/}),
//                                waitSeconds(1));
//
//                        setPathState(6);
//                    }
//                   break;
//                case 6:
//                    if (!follower.isBusy()){
//                        follower.followPath(pickuplow,true);
//                        setPathState(7);
//                    }
//                   break;
//
//                case 7:
//                    if (!follower.isBusy()){
//                        follower.followPath(BackToSpawn2,true);
//                        setPathState(8);
//                    }
//                   break;
//                case 8:
//                    if (!follower.isBusy()){
//                        sequence(parallel(exec(()->{/*shoot code*/}),
//                                waitSeconds(1))
//                                ,exec(()->follower.followPath(pickuphp,true)));
//                        setPathState(9);
//                    }
//                   break;
//                case 9:
//                    if (!follower.isBusy()){
//                        follower.followPath(backToSpawn3,true);
//                        setPathState(10);
//                    }
//                   break;
//                case 10:
//
//
//                if (!follower.isBusy()) {
//                    parallel(exec(()->{/*shootcode*/}),
//                            exec(()->waitSeconds(1)));
//                    setPathState(-1);
//                }
//                    break;


        }

    }

    /**
     * These change the states of the paths and actions. It will also reset the timers of the individual switches
     **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();


    }


    public static Mercurial.RegisterableProgram auton = Mercurial.autonomous(ctx -> {
        State states = new State();
    ctx.schedule(
                sequence(waitUntil(ctx::inLoop),
                exec(()->{/*shoot code*/}),//preloads
                sequence(
                        exec(()->follower.followPath(pickupmiddle)),
                        waitUntil(()->!follower.isBusy())
                ),//pick up middle
                sequence(
                        exec(()->follower.followPath(Diaganolfing)),
                        waitUntil(()->!follower.isBusy())
                ),//diagonal fing
                sequence(exec(()->follower.followPath(opengate)),
                        waitUntil(()->!follower.isBusy())
                ),//opengate
                sequence(exec(()->follower.followPath(backtospawn)),
                        waitUntil(()->!follower.isBusy())
                ),//go to shoot
                exec(()->{/*shoot code*/}),//middle pickups
                sequence(exec(()->follower.followPath(pickuplow)),
                        waitUntil(()->!follower.isBusy())
                ),
                sequence(exec(()->follower.followPath(BackToSpawn2)),
                        waitUntil(()->!follower.isBusy())
                ),
                exec(()->{/*shoot code*/}),//low pickups
                sequence(exec(()->follower.followPath(pickuphp)),
                        waitUntil(()->!follower.isBusy())
                ),
                sequence(exec(()->follower.followPath(backToSpawn3)),
                        waitUntil(()->!follower.isBusy())
                ),
                exec(()->{/*shoot code*/})//hp
                )
            );
        });
}
