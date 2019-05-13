package com.sarny.spocone.publicclasses.shot;

import java.util.Objects;

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

    public ShotResult() {
    }

    public ShotOutcome getShotOutcome() {
        return shotOutcome;
    }

    public int getField() {
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShotResult that = (ShotResult) o;
        return field == that.field &&
                shotOutcome == that.shotOutcome;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shotOutcome, field);
    }
}
