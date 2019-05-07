package com.sarny.spocone.publicclasses.shot;

/**
 * A class which represents field which was shot and what is an outcome of a shot.
 *
 * @author Agnieszka Trzewik
 * @see ShotOutcome
 */
public class ShotResult {

    private ShotOutcome shotOutcome;
    private int field;

    public ShotResult(ShotOutcome shotOutcome, int field) {
        this.shotOutcome = shotOutcome;
        this.field = field;
    }

    public ShotOutcome getShotOutcome() {
        return shotOutcome;
    }

    public int getField() {
        return field;
    }
}
