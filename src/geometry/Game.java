package geometry;

import utility.Rand;

import java.util.Arrays;
import java.util.HashSet;

public class Game {
    Vector[] startingPoints;
    HashSet<Vector> otherPoints;
    private double width, height;
    int fraction;
    private Vector current = null;

    public Game(double width, double height, int fraction, int startingPoints) {
        this.width = width;
        this.height = height;
        this.fraction = fraction;
        this.startingPoints = placeStartingPoints(startingPoints);
        otherPoints = new HashSet<>();
    }

    public Game(Vector[] startingPoints, double width, double height, int fraction) {
        this.startingPoints = startingPoints;
        this.width = width;
        this.height = height;
        this.fraction = fraction;
        otherPoints = new HashSet<>();
    }

    public void play(int moves){
        if (current == null)
            current = new Vector(Rand.randDouble(width), Rand.randDouble(height));
        for (int i = 0; i < moves; i++) {
            Vector direction = Rand.choose(startingPoints);
            otherPoints.add(current);
            current = direction.minus(current).multiply(1/fraction).add(current);
        }
    }

    private Vector[] placeStartingPoints(int n){
        Vector[] placed = new Vector[n];
        for (int i = 0; i < n; i++)
            placed[i] = new Vector(Rand.randDouble(width), Rand.randDouble(height));
        System.out.println(Arrays.toString(placed));
        return placed;
    }

    public Vector[] getStartingPoints() {
        return startingPoints;
    }

    public HashSet<Vector> getOtherPoints() {
        return otherPoints;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
