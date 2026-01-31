package org.firstinspires.ftc.teamcode.util.pedro;

import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.sequence;
import static dev.frozenmilk.dairy.mercurial.continuations.Continuations.waitSeconds;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import dev.frozenmilk.dairy.mercurial.ftc.Mercurial;

public class FarBlueMLH {
    public PathChain pickupmiddle;
    public PathChain Diaganolfing;
    public PathChain opengate;
    public PathChain backtospawn;
    public PathChain turn;
    public PathChain pickuplow;
    public PathChain turnalil;
    public PathChain backToSpawn3;
    public PathChain BackToSpawn2;
    public PathChain pickuphp;
    Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    public class State {
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

            //shoot while turning

            turn = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(56.000, 12.000),

                                    new Pose(56.000, 10.000)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(180))

                    .build();

            pickuplow = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(56.000, 10.000),
                                    new Pose(43.900, 39.256),
                                    new Pose(38.596, 36.528),
                                    new Pose(13.056, 36.096)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(180))

                    .build();

            turnalil = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(13.056, 36.096),

                                    new Pose(13.272, 36.112)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(146))

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
            switch (pathState) {
                case 0:
                    sequence(//shootcode,
                            waitSeconds(1)
                    );
                    setPathState(1);
                    break;
                case 1:

                    if (!follower.isBusy()) {
                        follower.followPath(pickupmiddle, true);
                        setPathState(2);
                    }
                case 15:


                if (!follower.isBusy()) {
                    setPathState(-1);
                }
                    break;

            }

        }/** These change the states of the paths and actions. It will also reset the timers of the individual switches **/
        public void setPathState (int pState){
            pathState = pState;
            pathTimer.resetTimer();


        }
    }

    public static Mercurial.RegisterableProgram auton = Mercurial.autonomous( ctx ->{

    });
}
