package org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.commands.groups.Advancing;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial.SampleClaw;

@Disabled
@org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial.SampleClaw.Attach
@Mercurial.Attach
@TeleOp(name = "sampleClawMerc")
public class sampleClawTestMerc extends OpMode {
    @Override
    public void init() {



        Mercurial.gamepad2().rightBumper().onTrue(new Advancing(org.firstinspires.ftc.teamcode.SammysOtherTeamsCode.Mercurial.SampleClaw.openClaw(), SampleClaw.closeClaw()));
    }

    @Override
    public void loop() {

    }
}
