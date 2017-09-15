package geometry;

import utility.Rand;
import utility.State;

import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Game {
    private Vector[] startingPoints;
    private HashSet<Vector> otherPoints;
    private double width, height;
    private double fraction;
    private Vector current = null;

    public Game(double width, double height, double fraction, int startingPoints) {
        this.width = width;
        this.height = height;
        this.fraction = fraction;
        this.startingPoints = placeStartingPoints(startingPoints);
        otherPoints = new HashSet<>();
    }

    public Game(Function<Long,Vector[]> startingPoints, double width, double height, long seed) {
        this.startingPoints = startingPoints.apply(seed);
        this.width = width;
        this.height = height;
        otherPoints = new HashSet<>();
    }

    public void play(int moves){
        if (current == null)
            current = new Vector(Rand.randDouble(width), Rand.randDouble(height));
        for (int i = 0; i < moves; i++) {
            Vector temp = Rand.choose(startingPoints).minus(current).multiply(fraction).add(current);
            otherPoints.add(temp);
            current = new Vector(temp);
        }
    }

    public void playCustom(int moves, Function<State, Vector> rule){
        if(current == null)
            current = new Vector(Rand.randDouble(width), Rand.randDouble(height));
        for (int i = 0; i < moves; i++){
            Vector temp = rule.apply(new State(current, startingPoints, i));
            otherPoints.add(temp);
            current = new Vector(temp);
        }
    }

    private Vector[] placeStartingPoints(int n){
        Vector[] placed = new Vector[n];
        for (int i = 0; i < n; i++)
            placed[i] = new Vector(Rand.randDouble(width), Rand.randDouble(height));
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
