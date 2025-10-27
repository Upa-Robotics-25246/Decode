package org.firstinspires.ftc.teamcode.core;

import org.firstinspires.ftc.teamcode.stellarstructure.StellarBot;
import org.firstinspires.ftc.teamcode.subsystems.*;

public class TarsBase extends StellarBot {
	public TarsBase() {
		super(
				new Drivebase(1.00, 1.00),
				new Intake(),
				new LeverTransfer(0.28, 0.00),
				new Spindexer()
		);
	}
}