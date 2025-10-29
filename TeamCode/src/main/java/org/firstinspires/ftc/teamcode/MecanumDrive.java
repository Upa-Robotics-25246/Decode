package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public abstract class MecanumDrive extends OpMode {

    protected DcMotorEx leftFrontDrive = null;
    protected DcMotorEx leftBackDrive = null;
    protected DcMotorEx rightFrontDrive = null;
    protected DcMotorEx rightBackDrive = null;

    double denominator = 0;
    double leftFrontPower = 0;
    double leftBackPower = 0;
    double rightFrontPower = 0;
    double rightBackPower = 0;

    @Override
    public void init() {
        leftFrontDrive = hardwareMap.get(DcMotorEx.class, "lfd");
        leftBackDrive = hardwareMap.get(DcMotorEx.class, "lbd");
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, "rfd");
        rightBackDrive = hardwareMap.get(DcMotorEx.class, "rbd");

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)

        //Initial Directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void drive(double y, double x, double rx) {
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]

        denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        leftFrontPower = (y + x + rx) / denominator;
        leftBackPower = (y - x + rx) / denominator;
        rightFrontPower = (y - x - rx) / denominator;
        rightBackPower = (y + x - rx) / denominator;

        leftFrontDrive.setPower(leftFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightFrontDrive.setPower(rightFrontPower);
        rightBackDrive.setPower(rightBackPower);
    }

}
