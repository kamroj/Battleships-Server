package com.sarny.spocone.publicclasses.shot;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which provides information about actions which have been taken by the player in one round.
 *
 * @author Agnieszka Trzewik
 * @see ShotResult
 */
public class ShotsSummary {

    private List<ShotResult> shotResults;
    private boolean isGameOver;

    public ShotsSummary() {
        this.shotResults = new ArrayList<>();
        this.isGameOver = false;
    }

    public List<ShotResult> getShotResults() {
        return shotResults;
    }

    public void addShot(ShotResult shotResult){
        shotResults.add(shotResult);
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void gameOver(){
        isGameOver = true;
    }
}