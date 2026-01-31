package org.firstinspires.ftc.teamcode.util;

import com.pedropathing.geometry.Pose;

import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;

public class GlobalVariables {

    public static Pose BlueGoalPos = new Pose(0,144);
    public static Pose RedGoalPos = new Pose(144,144);
    public static Pose startPoseFarBlue = new Pose(56, 8, Math.toRadians(180));
    public static Pose scorePoseFarBlue = startPoseFarBlue;
    public enum Alliance{
        RED,
        BLUE
    }

    //FlywheelPID coeffs
    public static PIDCoefficients FlypidCoefficients = new PIDCoefficients( 0.0000009, 0, 0.000001);
    public static BasicFeedforwardParameters Flyff = new BasicFeedforwardParameters(0.000455,0,0.000463);

}
