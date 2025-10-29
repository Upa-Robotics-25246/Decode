package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class DecodeControl extends MecanumDrive {

    //////////////////////////////////////////////////////////////
    // Constants
    //////////////////////////////////////////////////////////////
    final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.
    final double FULL_SPEED = 1.0;

    final double FEED_TIME_SECONDS = 0.80; //The feeder servos run this long when a shot is requested.

    final double  LAUNCHER_CLOSE_TARGET_VELOCITY = 1500;
    final double LAUNCHER_FAR_TARGET_VELOCITY = 1700;
    final double ALLOWED_VELOCITY_DIVERSION = 100;

    final double LEFT_POSITION = 0.348;
    //final double LEFT_POSITION = 0.555;
    final double RIGHT_POSITION = 0.310;
    //final double RIGHT_POSITION = 0.08;


    //////////////////////////////////////////////////////////////
    // Enums
    //////////////////////////////////////////////////////////////
    protected enum LaunchState {
        OFF,
        IDLE,
        SPIN_UP,
        LAUNCH,
        LAUNCHING,
    }

    protected enum IntakeState {
        ON,
        OFF;
    }

    protected enum DiverterDirection {
        LEFT,
        RIGHT;
    }

    protected enum LauncherDistance {
        CLOSE,
        FAR;
    }

    //////////////////////////////////////////////////////////////
    // Member Variables
    //////////////////////////////////////////////////////////////

    // Control Components
    protected DcMotorEx intake = null;
    protected DcMotorEx leftLauncher = null;
    protected DcMotorEx rightLauncher = null;
    protected CRServo leftFeeder = null;
    protected CRServo rightFeeder = null;
    protected Servo diverter = null;


    // Control Objects
    protected ElapsedTime feederTimer = null;
    protected PIDFCoefficients feederPIDFCoefficients = null;


    // State Variables
    protected LaunchState leftLauncherState = LaunchState.OFF;
    protected LaunchState rightLauncherState = LaunchState.OFF;
    protected DiverterDirection diverterDirection = DiverterDirection.RIGHT;
    protected IntakeState intakeState = IntakeState.OFF;
    protected LauncherDistance launcherDistance = LauncherDistance.CLOSE;
    protected double launcherVelocity = LAUNCHER_CLOSE_TARGET_VELOCITY;


    @Override
    public void init() {
        super.init();

        feederTimer = new ElapsedTime();
        feederPIDFCoefficients = new PIDFCoefficients(300, 0, 0, 10);

        leftLauncher = hardwareMap.get(DcMotorEx.class, "ll");
        rightLauncher = hardwareMap.get(DcMotorEx.class, "rl");
        intake = hardwareMap.get(DcMotorEx.class, "i");
        leftFeeder = hardwareMap.get(CRServo.class, "lf");
        rightFeeder = hardwareMap.get(CRServo.class, "rf");
        diverter = hardwareMap.get(Servo.class, "d");

        leftLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLauncher.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLauncher.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        leftLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLauncher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, feederPIDFCoefficients);
        rightLauncher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, feederPIDFCoefficients);

        leftFeeder.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFeeder.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFeeder.setPower(STOP_SPEED);
        rightFeeder.setPower(STOP_SPEED);

        diverter.setPosition(RIGHT_POSITION);
    }

    protected void launcherSpinUp() {
        leftLauncherState = LaunchState.IDLE;
        rightLauncherState = LaunchState.IDLE;
        leftLauncher.setVelocity(launcherVelocity);
        rightLauncher.setVelocity(launcherVelocity);
    }

    protected void launcherStop() {
        leftLauncherState = LaunchState.OFF;
        rightLauncherState = LaunchState.OFF;
        leftLauncher.setVelocity(STOP_SPEED);
        rightLauncher.setVelocity(STOP_SPEED);
    }

    protected void diverterLeft() {
        diverterDirection = DiverterDirection.LEFT;
        diverter.setPosition(LEFT_POSITION);
    }

    protected void diverterRight() {
        diverterDirection = DiverterDirection.RIGHT;
        diverter.setPosition(RIGHT_POSITION);
    }

    protected void diverterDirectionToggle() {
        switch (diverterDirection){
            case LEFT:
                diverterRight();
                break;
            case RIGHT:
                diverterLeft();
                break;
        }
    }

    protected void intakeOn() {
        intakeState = IntakeState.ON;
        intake.setPower(1);
    }

    protected void intakeOff(){
        intakeState = IntakeState.OFF;
        intake.setPower(0);
    }

    protected void intakeStateToggle() {
        switch (intakeState){
            case ON:
                intakeOff();
                break;
            case OFF:
                intakeOn();
                break;
        }
    }

    protected void launcherVelocityToggle() {
        switch (launcherDistance) {
            case CLOSE:
                launcherDistance = LauncherDistance.FAR;
                setLauncherVelocity(LAUNCHER_FAR_TARGET_VELOCITY);
                break;
            case FAR:
                launcherDistance = LauncherDistance.CLOSE;
                setLauncherVelocity(LAUNCHER_CLOSE_TARGET_VELOCITY);
                break;
        }
    }

    protected void setLauncherVelocity(double velocity) {
        launcherVelocity = velocity;
        if (leftLauncherState != LaunchState.OFF || rightLauncherState != LaunchState.OFF )
        {
            leftLauncher.setVelocity(launcherVelocity);
            rightLauncher.setVelocity(launcherVelocity);
        }
    }

    private void feedRightLauncher(){

    }

    private void feedLeftLauncher(){

    }

    void launchLeft(boolean shotRequested) {
        switch (leftLauncherState) {
            case OFF:
            case IDLE:
                if (shotRequested) {
                    leftLauncherState = LaunchState.SPIN_UP;
                    rightLauncherState = LaunchState.IDLE;
                }
                break;
            case SPIN_UP:
                launcherSpinUp();
                if (Math.abs(leftLauncher.getVelocity()) > (launcherVelocity - ALLOWED_VELOCITY_DIVERSION)) {
                    leftLauncherState = LaunchState.LAUNCH;
                }
                break;
            case LAUNCH:
                leftFeeder.setPower(FULL_SPEED);
                feederTimer.reset();
                leftLauncherState = LaunchState.LAUNCHING;
                break;
            case LAUNCHING:
                if (feederTimer.seconds() > FEED_TIME_SECONDS) {
                    leftLauncherState = LaunchState.IDLE;
                    leftFeeder.setPower(STOP_SPEED);
                }
                break;
        }
    }

    void launchRight(boolean shotRequested) {
        switch (rightLauncherState) {
            case OFF:
            case IDLE:
                if (shotRequested) {
                    rightLauncherState = LaunchState.SPIN_UP;
                    leftLauncherState = LaunchState.IDLE;
                }
                break;
            case SPIN_UP:
                launcherSpinUp();
                if (Math.abs(rightLauncher.getVelocity()) > (launcherVelocity - ALLOWED_VELOCITY_DIVERSION)) {
                    rightLauncherState = LaunchState.LAUNCH;
                }
                break;
            case LAUNCH:
                rightFeeder.setPower(FULL_SPEED);
                feederTimer.reset();
                rightLauncherState = LaunchState.LAUNCHING;
                break;
            case LAUNCHING:
                if (feederTimer.seconds() > FEED_TIME_SECONDS) {
                    rightLauncherState = LaunchState.IDLE;
                    rightFeeder.setPower(STOP_SPEED);
                }
                break;
        }
    }
}
