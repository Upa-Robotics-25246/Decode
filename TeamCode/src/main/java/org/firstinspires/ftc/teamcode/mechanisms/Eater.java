package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * The "Eater" is the robot intake.
 * aka Devourer of Artifacts, Robo-Kirb, The Rizzler
 */
public class Eater {
    boolean isEating = false;

    private DcMotor eaterMotor;

    public void init(HardwareMap hardwareMap) {
        eaterMotor = hardwareMap.dcMotor.get("intake");
        eaterMotor.setDirection(DcMotor.Direction.FORWARD);
        eaterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public boolean toggle() {
        if (isEating) {
            isEating = false;
            eaterMotor.setPower(0);
        } else {
            isEating = true;
            eaterMotor.setPower(1);
        }

        return isEating;
    }
}
