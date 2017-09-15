package utility;

import geometry.Vector;

public class State {
    private Vector current;
    private Vector[] startingPoints;

    public Vector[] getStartingPoints() {
        return startingPoints;
    }

    public void setStartingPoints(Vector[] startingPoints) {
        this.startingPoints = startingPoints;
    }

    public int getIterration() {
        return iterration;
    }

    public void setIterration(int iterration) {
        this.iterration = iterration;
    }

    public Vector getCurrent() {

        return current;
    }

    public void setCurrent(Vector current) {
        this.current = current;
    }

    public State(Vector current, Vector[] startingPoints, int iterration) {

        this.current = current;
        this.startingPoints = startingPoints;
        this.iterration = iterration;
    }

    private int iterration;
}
