package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.directives.DefaultLeverTransfer;
import org.firstinspires.ftc.teamcode.stellarstructure.hardwaremapwrappers.StellarServo;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.stellarstructure.Subsystem;

public final class LeverTransfer extends Subsystem {
	private StellarServo leverTransfer;
	private final double leverDownPosition, leverUpPosition;
	private boolean isLeverTargetUp = false;

    public LeverTransfer(double leverDownPosition, double leverUpPosition) {
		this.leverDownPosition = leverDownPosition;
		this.leverUpPosition = leverUpPosition;
	}

	@Override
	public void init(HardwareMap hardwareMap) {
		leverTransfer = new StellarServo(hardwareMap, "leverTransfer");
	}

	@Override
	public void setGamepads(Gamepad gamepad1, Gamepad gamepad2) {
		setDefaultDirective(new DefaultLeverTransfer(this, gamepad1));
	}

	@Override
	public void update() {
		updateServoPosition();
	}

	public void setLeverPositionIsUp(boolean isUpPosition) {
		isLeverTargetUp = isUpPosition;
	}

	public void toggleLeverPosition() {
		isLeverTargetUp = !isLeverTargetUp;
	}

	public void updateServoPosition() {
		leverTransfer.setPosition(isLeverTargetUp ? leverUpPosition : leverDownPosition);
	}

	@Override
	public String getTelemetryData() {
		return String.format("Lever Up Position: %f\nLever Is Up: %b", leverUpPosition, isLeverTargetUp);
	}
}