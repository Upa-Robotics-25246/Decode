package org.firstinspires.ftc.teamcode.stellarstructure.runnables;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.stellarstructure.Scheduler;
import org.firstinspires.ftc.teamcode.stellarstructure.Subsystem;

public abstract class Runnable {
    //no required subsystems by default
    private Subsystem[] requiredSubsystems = {};

    //interruptible by default
    private boolean interruptible = true;

    private boolean hasFinished = false;

    public abstract void start(boolean hadToInterruptToStart);

    public abstract void update();

    public abstract void stop(boolean interrupted);

    public abstract boolean isFinished();

    public final void setRequires(@NonNull Subsystem... subsystems) {
        requiredSubsystems = subsystems;
    }

    public final Subsystem[] getRequiredSubsystems() {
        return requiredSubsystems;
    }

    public final void setInterruptible(boolean interruptible) {
        this.interruptible = interruptible;
    }

    public final boolean isInterruptible() {
        return interruptible;
    }

    public final boolean getHasFinished() {
        return hasFinished;
    }

    public final void setHasFinished(boolean finished) {
        hasFinished = finished;
    }

    public final void schedule() {
        Scheduler.getInstance().schedule(this);
    }
}
