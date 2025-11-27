package org.firstinspires.ftc.teamcode.util.opencv;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.wpilibcontroller.SimpleMotorFeedforward;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name = "ABSOLUTE WEEWOO")
public class turretAutoAim extends OpMode {
    DcMotorEx turret;

    PIDController controller;
    SimpleMotorFeedforward feedforward;
    double bearing;
    static double p = 0,i = 0, d =0, s = 0, v = 0;


    AprilTagProcessor tag;
    VisionPortal visionPortal;
    @Override
    public void init() {
        turret = hardwareMap.get(DcMotorEx.class,"turret");
        tag = new AprilTagProcessor.Builder().build();

        VisionPortal.Builder PortalTag = new VisionPortal.Builder();

        PortalTag.setCamera(hardwareMap.get(WebcamName.class,"Webcam 1"));

        PortalTag.addProcessor(tag);
        visionPortal = PortalTag.build();
        controller = new PIDController(p,i,d);
        feedforward = new SimpleMotorFeedforward(s,v);
    }

    @Override
    public void loop() {
        controller.setPID(p,i,d);

        List<AprilTagDetection> currentdetections = tag.getDetections();
        for (AprilTagDetection detections:currentdetections){
            if (detections.metadata != null){
                if (detections.id == 24){
                    bearing = detections.ftcPose.bearing;
                } else if (detections.id == 20) {
                    bearing = detections.ftcPose.bearing;
                }
            }
        }
        // idk what to do rn cus axon problem
    }
}