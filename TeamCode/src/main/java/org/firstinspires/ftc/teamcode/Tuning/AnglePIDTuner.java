package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Modules.PIDController;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;


@Config
@TeleOp(name = "Angle Tuner", group = "Tuning")
public class AnglePIDTuner extends LinearOpMode {

    Robot robot;
    PIDController anglePID;

    boolean hasrun = false;
    public static boolean align = false;
    public static double kp = 0, ki = 0, kd = 0;
    public static double error = 0;
    private Telemetry t = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    String goalTag = "Red Goal";

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);

        hasrun = false;
        align = false;
        boolean prevAlign = false;

        double kp = 0, ki = 0, kd = 0;
        anglePID = new PIDController(kp, ki, kd);

        double prevHeading = robot.getDrivetrain().getRobotHeading(AngleUnit.DEGREES);

        waitForStart();
        if (isStopRequested()) return;

        while(opModeIsActive()){
            if (gamepad1.a || align) {
                align = true;
            }
            if (gamepad1.b) {
                align = false;
            }

            if(prevAlign != align) {
                anglePID.setPID(kp, ki, kd);
            }

            fieldCentricDrive();
            prevAlign = align;
        }
    }

    public double turnPowerAngle(double angle) {
        //AprilTagDetection goalTag = robot.getCamera().getGoalApriltag();

        //if (goalTag == null) {return 0; }

        return anglePID.calculate(0, angle);
    }

    private void fieldCentricDrive() {
        double slowdown = gamepad1.right_trigger > 0 ? 0.25 : 1;
        double y = -gamepad1.left_stick_y * slowdown;
        double x = gamepad1.left_stick_x * 1.1 * slowdown;
        double alignVal = turnPowerAngle(error+prevHeading-robot.getDrivetrain().getRobotHeading(AngleUnit.DEGREES));
        if(alignVal == 0) { align = false; }
        t.addData("Turn Power Lock on Tag: ", alignVal);
        double rx = (alignVal != 0 && align) ? alignVal : gamepad1.right_stick_x * slowdown;

        double heading = robot.getDrivetrain().getRobotHeading(AngleUnit.RADIANS);


        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frWheelPower = (rotY - rotX - rx) / denominator;
        double flWheelPower = (rotY + rotX + rx) / denominator;
        double brWheelPower = (rotY + rotX - rx) / denominator;
        double blWheelPower = (rotY - rotX + rx) / denominator;

        robot.getDrivetrain().setWheelPowers(flWheelPower, frWheelPower, brWheelPower, blWheelPower);

        if (gamepad1.y) {
            robot.getDrivetrain().resetImu();
        }
    }
}
