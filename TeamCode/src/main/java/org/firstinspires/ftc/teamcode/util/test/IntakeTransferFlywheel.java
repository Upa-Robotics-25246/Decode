package org.firstinspires.ftc.teamcode.util.test;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
@TeleOp
@Configurable
public class IntakeTransferFlywheel extends OpMode {
   static double intakeSpeed = 0;
   static double transferSpeed = 0;
   static int velocity = 0;


    ControlSystem pidf;
    public static PIDCoefficients pidCoefficients = new PIDCoefficients( 0.0000009, 0, 0);
    public static BasicFeedforwardParameters ff = new BasicFeedforwardParameters(0.00048,0,0.000463);

    DcMotorEx flywheel,intake,transfer;
    @Override
    public void init() {
        flywheel = hardwareMap.get(DcMotorEx.class,"flywheel");
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake = hardwareMap.get(DcMotorEx.class,"intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer = hardwareMap.get(DcMotorEx.class,"transfer");
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);

        pidf = ControlSystem.builder()
                .velPid(pidCoefficients)
                .basicFF(ff)
                .build();
    }

    @Override
    public void loop() {
        intake.setPower(intakeSpeed);
        transfer.setPower(transferSpeed);
        pidf.setGoal(new KineticState(0.0,velocity));
        flywheel.setPower(pidf.calculate(new KineticState(0, flywheel.getVelocity())));


        telemetry.addData("intakeSpeed",intakeSpeed);
        telemetry.addData("transferSpeed",transferSpeed);
        telemetry.addData("flywheel vel",flywheel.getVelocity());
        telemetry.addData("flywheel target",velocity);
        telemetry.update();
    }
}
