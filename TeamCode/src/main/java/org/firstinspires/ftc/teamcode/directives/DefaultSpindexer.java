package org.firstinspires.ftc.teamcode.directives;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.stellarstructure.Trigger;
import org.firstinspires.ftc.teamcode.stellarstructure.conditions.GamepadButton;
import org.firstinspires.ftc.teamcode.stellarstructure.conditions.StatefulCondition;
import org.firstinspires.ftc.teamcode.stellarstructure.runnables.DefaultDirective;
import org.firstinspires.ftc.teamcode.subsystems.Spindexer;

public class DefaultSpindexer extends DefaultDirective {
	public DefaultSpindexer(Spindexer spindexer, Gamepad gamepad1) {
		super(spindexer);

		new Trigger(
			new GamepadButton(gamepad1, GamepadButton.Button.X), //when X held
			() -> {spindexer.setSelectedSegment(0);}
		).schedule();

		new Trigger(
				new GamepadButton(gamepad1, GamepadButton.Button.Y), //when Y held
				() -> {spindexer.setSelectedSegment(1);}
		).schedule();

		new Trigger(
				new GamepadButton(gamepad1, GamepadButton.Button.B), //when B held
				() -> {spindexer.setSelectedSegment(2);}
		).schedule();

		new Trigger(
				new StatefulCondition(
						new GamepadButton(gamepad1, GamepadButton.Button.A),
						StatefulCondition.Edge.RISING //on initial press
				),
				spindexer::toggleIsIntakePosition
		).schedule();
	}
}
