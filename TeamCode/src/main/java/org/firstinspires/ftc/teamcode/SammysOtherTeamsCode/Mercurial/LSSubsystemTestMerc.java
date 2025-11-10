package org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.frozenmilk.dairy.pasteurized.SDKGamepad;
import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.bindings.BoundGamepad;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial.LS;

@Disabled
@Mercurial.Attach

@org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial.LS.Attach
@TeleOp(name = "LsMerc")
public class LSSubsystemTestMerc extends OpMode {
    



    @Override
    public void init() {
        BoundGamepad boundGamepad = new BoundGamepad(new SDKGamepad(gamepad2));

        boundGamepad.dpadUp().onTrue(org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial.LS.setTargetPos100());
        boundGamepad.dpadDown().onTrue(LS.setTargetPos0());

    }

    @Override
    public void loop() {

    }
}
