package org.firstinspires.ftc.teamcode.util.opencv;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
@TeleOp
public class AprilTagVelocityCalc extends OpMode {
    DcMotorEx flywheel;
    double range;

    AprilTagProcessor tag;
    VisionPortal visionPortal;
    @Override
    public void init() {
        flywheel = hardwareMap.get(DcMotorEx.class,"flywheel");
        tag = new AprilTagProcessor.Builder().build();

        VisionPortal.Builder PortalTag = new VisionPortal.Builder();

        PortalTag.setCamera(hardwareMap.get(WebcamName.class,"Webcam 1"));

        PortalTag.addProcessor(tag);
        visionPortal = PortalTag.build();
    }

    @Override
    public void loop() {

        double power = gamepad1.left_trigger;
        flywheel.setVelocity((power*6000)*(28/60));
        flywheel.setPower(gamepad2.right_trigger);
        double vel = flywheel.getVelocity();
        telemetry.addData("Velocity:",vel);

        List<AprilTagDetection> currentdetections = tag.getDetections();
        for (AprilTagDetection detections:currentdetections){
            if (detections.metadata != null){
                if (detections.id == 24){
                    range = detections.ftcPose.y;
                } else if (detections.id == 20) {
                    range = detections.ftcPose.y;
                }
            }
        }
        telemetry.addData("AprilTag distance",range);
        telemetry.update();

    }
}
