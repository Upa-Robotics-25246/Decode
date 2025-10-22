package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.directives.DefaultSpindexer;
import org.firstinspires.ftc.teamcode.interstellar.Subsystem;
import org.firstinspires.ftc.teamcode.interstellar.hardwaremapwrappers.StellarServo;

public final class Spindexer extends Subsystem {
	private final static double DEGREES_TO_SERVO = 1.0 / 360.0;
	private int selectedSegment = 0;
	private boolean isIntakePosition = true;
	private final static double[] INTAKE_DEGREE_POSITIONS = {0.0, 240.0, 120.0};
	private final static double[] TRANSFER_DEGREE_POSITIONS = {180.0, 60.0, 300.0};

	private StellarServo spindexer;
	private DigitalChannel beamBreak;
	private ColorSensor colorSensor;

	@Override
	public void init(HardwareMap hardwareMap) {
		spindexer = new StellarServo(hardwareMap, "spindexer");
		beamBreak = hardwareMap.get(DigitalChannel.class, "beamBreak"); //unused
		colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
	}

	@Override
	public void setGamepads(Gamepad gamepad1, Gamepad gamepad2) {
		setDefaultDirective(new DefaultSpindexer(this, gamepad1));
	}

	@Override
	public void update() {
		updateServoPosition();
	}

	public void setSelectedSegment(int selectedSegment) {
		this.selectedSegment = selectedSegment;
	}

	public void toggleIsIntakePosition() {
		isIntakePosition = !isIntakePosition;
	}

	public void updateServoPosition() {
		spindexer.setPosition(
				isIntakePosition ?
						INTAKE_DEGREE_POSITIONS[selectedSegment] * DEGREES_TO_SERVO :
						TRANSFER_DEGREE_POSITIONS[selectedSegment] * DEGREES_TO_SERVO
		);
	}

	@Override
	public String getTelemetryData() {
		return String.format(
				"selectedSegment: %d\n" +
				"isIntakePosition: %b\n" +
				"beamBreak: %b\n" +
				"colorSensorRGB: %d, %d, %d",
				selectedSegment, isIntakePosition, beamBreak.getState(),
				colorSensor.red(), colorSensor.green(), colorSensor.blue());
	}
}