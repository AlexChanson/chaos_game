package geometry;

import utility.Rand;
import java.util.HashSet;

public class Paper {
    private double width, height;
    private HashSet<Vector> points;

    public Paper(double width, double height) {
        this.width = width;
        this.height = height;
        points = new HashSet<>(200);
    }

    public Vector[] placeRandomPoints(int n){
        Vector[] placed = new Vector[n];
        for (int i = 0; i < n; i++) {
            Vector temp = new Vector(Rand.randDouble(width), Rand.randDouble(height));
            placed[i] = temp;
            points.add(temp);
        }
        return placed;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public HashSet<Vector> getPoints() {
        return points;
    }
}
