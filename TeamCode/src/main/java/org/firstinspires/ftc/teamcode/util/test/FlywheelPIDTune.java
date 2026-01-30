package org.firstinspires.ftc.teamcode.util.test;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;

@Configurable
@TeleOp
public class FlywheelPIDTune extends OpMode {
    DcMotorEx flywheel;
    ControlSystem controller;
   public static double kP = 0;
   public static double kI = 0;
    public static double kD = 0;
    public static double kS = 0;
   public static double kV = 0;
    public static double velocity =0;

    public static PIDCoefficients pidC = new PIDCoefficients(0.011, 0.0, 0.0);

    public static BasicFeedforwardParameters ffCoefs = new BasicFeedforwardParameters(0.000006493506494, 0.0, 0.03);



    @Override
    public void init() {
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        controller = ControlSystem.builder()
                .velPid(kP,kI,kD)
                .basicFF(kV,0,kS)
                .build();
    }


    @Override
    public void loop() {




        controller.setGoal(new KineticState(0.0,velocity));
        flywheel.setPower(controller.calculate(new KineticState(0, flywheel.getVelocity())));
        telemetry.addData("Power", flywheel.getPower());
        telemetry.addData("Velocity", flywheel.getVelocity());
    }

}