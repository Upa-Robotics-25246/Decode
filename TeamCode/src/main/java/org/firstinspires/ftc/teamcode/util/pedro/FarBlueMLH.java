package org.firstinspires.ftc.teamcode.util.pedro;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

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

    public FarBlueMLH(Follower follower) {
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

                                new Pose(55.768, 8.096)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))
                .setReversed()
                .build();

        turn = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(55.768, 8.096),

                                new Pose(55.768, 8.096)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        pickuplow = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(55.768, 8.096),
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

        backToSpawn3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(13.272, 36.112),

                                new Pose(55.536, 7.352)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        BackToSpawn2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(55.536, 7.352),

                                new Pose(55.976, 7.400)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        pickuphp = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(55.976, 7.400),

                                new Pose(9.888, 7.760)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();
    }
}

