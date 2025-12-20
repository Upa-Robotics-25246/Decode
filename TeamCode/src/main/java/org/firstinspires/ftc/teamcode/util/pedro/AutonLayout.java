package org.firstinspires.ftc.teamcode.util.pedro;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;



public class AutonLayout extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    // poses go here
    private final Pose startPose = Poses.startPoseFar;
    private final Pose scorePose = Poses.scorePoseFar;

    public void buildPaths(){
        //put paths here
    }
    public void autonomousPathUpdate() {
        switch (pathState) {
            //look at example and make a switch case for this
        }
    }
    /** These change the states of the paths and actions. It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }


    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);

    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();

    }
}
