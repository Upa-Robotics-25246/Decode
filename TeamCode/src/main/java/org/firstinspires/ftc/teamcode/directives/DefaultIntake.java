package org.firstinspires.ftc.teamcode.directives;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.interstellar.Trigger;
import org.firstinspires.ftc.teamcode.interstellar.directives.DefaultDirective;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class DefaultIntake extends DefaultDirective {
	public DefaultIntake(Intake intake, Gamepad gamepad) {
		super(intake);

		//todo: make if/else

		new Trigger(
				() -> gamepad.left_trigger > 0.05, //when left trigger pressed
				() -> {intake.setIntakeSpeed(-gamepad.left_trigger);} //set intake to left trigger
		).schedule();

		new Trigger(
				() -> gamepad.right_trigger > 0.05, //when right trigger pressed
				() -> {intake.setIntakeSpeed(gamepad.right_trigger);} //set intake to right trigger
		).schedule();

		new Trigger(
				() -> (gamepad.right_trigger <= 0.05) == (gamepad.left_trigger <= 0.05), //neither or both triggers are pressed
				() -> {intake.setIntakeSpeed(0);} //set intake speed to 0
		).schedule();
	}
}