package org.firstinspires.ftc.teamcode.util.ComandBaseOrSimilar.ftclibOrSolverslib;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.seattlesolvers.solverslib.util.InterpLUT;


public class flywheelPIDtuning extends OpMode {
    InterpLUT vel = new InterpLUT();
    PIDController Feedback;
    SimpleMotorFeedforward feedforward;
    static double kP = 0;
    static double kI =0;
    static double kD = 0;
    static double kS = 0;
    static double kV = 0;

    DcMotorEx flyWheel;
    double dist;


    @Override
    public void init() {


        feedforward = new SimpleMotorFeedforward(kS,kV);
        Feedback = new PIDController(kP,kI,kD);
        flyWheel = hardwareMap.get(DcMotorEx.class,"flywheel");
        velocityLUTinit();


    }

    @Override
    public void loop() {

        Feedback.setPID(kP,kI,kD);
        double velocity = vel.get(dist);// dist is gonna be based on odo
        double power = Feedback.calculate(flyWheel.getVelocity(),velocity);
        double ff = feedforward.calculate(velocity);



        flyWheel.setPower(power+ff);

    }

    public void velocityLUTinit(){
        double distance = 0;// placeholders
        double velocity = 0;
        vel.add(distance,velocity);
        vel.createLUT();

    }



}
