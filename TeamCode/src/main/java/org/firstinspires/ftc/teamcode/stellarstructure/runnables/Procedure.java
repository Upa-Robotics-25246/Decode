package org.firstinspires.ftc.teamcode.stellarstructure.runnables;

import androidx.annotation.NonNull;

public class Procedure extends Runnable {
    Directive[] directives;
    private int currentDirectiveId = 0;

    public Procedure(@NonNull Directive... directives) {
        if (directives.length == 0) {
            throw new IllegalArgumentException("No directives provided");
        }

        this.directives = directives;
    }

    @Override
    public void start(boolean hadToInterruptToStart) {
        directives[0].start(false);
    }

    /*
    package org.firstinspires.ftc.teamcode.stellarstructure.runnables;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.stellarstructure.Subsystem;
import java.util.HashSet;
import java.util.Set;

// A Procedure is a Directive that runs other Directives in a sequence.
// To the Scheduler, it looks just like any other single Directive.
public class Procedure extends Directive {
    private final Directive[] directives;
    private int currentDirectiveIndex = -1; // Start at -1, so the first start() call increments to 0

    public Procedure(@NonNull Directive... directives) {
        if (directives.length == 0) {
            throw new IllegalArgumentException("Procedure cannot be created with zero directives.");
        }
        this.directives = directives;

        // A Procedure must require ALL subsystems that its child directives require.
        // This is critical for the Scheduler to correctly handle resource conflicts.
        Set<Subsystem> allRequiredSubsystems = new HashSet<>();
        for (Directive d : directives) {
            for (Subsystem s : d.getRequiredSubsystems()) {
                allRequiredSubsystems.add(s);
            }
        }
        requires(allRequiredSubsystems.toArray(new Subsystem[0]));
    }

    @Override
    public void start(boolean hadToInterruptToStart) {
        super.start(hadToInterruptToStart);
        // Start the first directive in the sequence.
        currentDirectiveIndex = 0;
        directives[currentDirectiveIndex].start(false); // The procedure was already "interrupted" if needed
    }

    @Override
    public void update() {
        // If the current directive is invalid or the whole procedure is done, do nothing.
        if (currentDirectiveIndex < 0 || currentDirectiveIndex >= directives.length) {
            return;
        }

        // Get the current directive
        Directive currentDirective = directives[currentDirectiveIndex];

        // If the current directive has finished...
        if (currentDirective.getHasFinished()) {
            currentDirective.stop(false); // Cleanly stop the finished directive
            currentDirectiveIndex++; // Move to the next one

            // If there are more directives left, start the next one.
            if (currentDirectiveIndex < directives.length) {
                directives[currentDirectiveIndex].start(false);
            } else {
                // If we've run out of directives, the procedure is finished.
                // The main update loop will now stop calling this.
                return;
            }
        }

        // If the procedure is still running, update the (potentially new) current directive.
        // This handles the first frame of a new directive right after the old one finishes.
        if (currentDirectiveIndex < directives.length) {
            directives[currentDirectiveIndex].update();
        }
    }

    @Override
    public void stop(boolean interrupted) {
        // If the procedure is stopped (or interrupted), stop the currently running child directive.
        if (interrupted && currentDirectiveIndex >= 0 && currentDirectiveIndex < directives.length) {
            directives[currentDirectiveIndex].stop(true);
        }
        super.stop(interrupted); // Mark the procedure itself as stopped.
    }

    @Override
    public boolean isFinished() {
        // The procedure is finished if the index is past the end of the array,
        // which only happens after the last directive finishes.
        return currentDirectiveIndex >= directives.length;
    }
}

     */
    @Override
    public void update() {
        Directive currentDirective = directives[currentDirectiveId];
        //todo: implement logic

        if (directives[currentDirectiveId].getHasFinished()) {
            currentDirectiveId++;

            if (currentDirectiveId < directives.length) {
                directives[currentDirectiveId].start(false);
            }
        }



        directives[currentDirectiveId].update();
    }

    @Override
    public void stop(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return directives[directives.length - 1].getHasFinished();
    }
}
