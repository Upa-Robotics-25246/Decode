package org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.frozenmilk.dairy.pasteurized.SDKGamepad;
import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.bindings.BoundGamepad;
import dev.frozenmilk.mercurial.commands.groups.Advancing;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial.specimenClaw;

@Disabled
@org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial.specimenClaw.Attach
@Mercurial.Attach
@TeleOp(name = "SpecClawMerc")
public class specimenClawTestMerc extends OpMode {
    @Override
    public void init() {
        BoundGamepad Gamepad2 = new BoundGamepad(new SDKGamepad(gamepad2));


        Gamepad2.leftBumper().onTrue(new Advancing(org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial.specimenClaw.openClaw(), specimenClaw.closeClaw()));
    }

    @Override
    public void loop() {

    }
}
