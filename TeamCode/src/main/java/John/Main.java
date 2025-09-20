package John;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class Main extends OpMode {
    DcMotor robert;

    public void init() {
        robert = hardwareMap.get(DcMotor.class, "motorNames");
        robert.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void loop() {
        if ( gamepad1.a == true ) {
            robert.setPower(gamepad1.left_stick_y);
        }
    }
}
