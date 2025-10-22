package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.directives.DefaultIntake;
import org.firstinspires.ftc.teamcode.interstellar.hardwaremapwrappers.StellarDcMotor;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.interstellar.Subsystem;


public final class Intake extends Subsystem {
	private StellarDcMotor intake;
	private double intakeSpeed = 0;

	@Override
	public void init(HardwareMap hardwareMap) {
		intake = new StellarDcMotor(hardwareMap, "intake");
	}

	@Override
	public void setGamepads(Gamepad gamepad1, Gamepad gamepad2) {
		setDefaultDirective(new DefaultIntake(this, gamepad1));
	}

	@Override
	public void update() {
		setMotorSpeed();
	}

	public void setIntakeSpeed(double intakeSpeed) {
		this.intakeSpeed = intakeSpeed;
	}

	public void setMotorSpeed() {
		intake.setPower(intakeSpeed);
	}

	@Override
	public String getTelemetryData() {
		return String.format("Intake Speed: %f", intakeSpeed);
	}
}