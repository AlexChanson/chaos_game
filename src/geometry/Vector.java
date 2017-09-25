package geometry;

import java.lang.Math;


public class Vector {
    private double x;
    private double y;


    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param other vector to be copied
     */
    public Vector(Vector other){
        this.x = other.x;
        this.y = other.y;
    }

    /**
     *
     * @return a new vector with (0,0) as coordinates
     */
    public static Vector getNull(){ return new Vector(0,0); }

    /**
     *
     * @param norm norm of the vector to be created
     * @param angle angle on the unit circle
     * @return a Vector created in the polar plane
     */
    public static Vector createFromAngle(double norm, double angle){
        return new Vector(norm*Math.cos(angle), norm*Math.sin(angle));
    }

    /**
     *
     * @return a real, mathematically: ||u||
     */
    public double norm(){
        return Math.sqrt(x*x+y*y);
    }

    /**
     *
     * @return return the norm squared, use when the square root operation isn't needed
     */
    public double normSquared(){
        return x*x+y*y;
    }

    /**
     *
     * @param other the compared vector
     * @return the distance between the two vectors, seen as points from the origin
     */
    public double distanceTo(Vector other){
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    /**
     *
     * @param other the compared vector
     * @return the distance squared between the two vectors, seen as points from the origin.
     */
    public double distanceToSquared(Vector other){
        return (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y);
    }

    public Vector add(double x, double y){
        return new Vector(this.x + x, this.y + y);
    }

    public Vector add(Vector other){
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector minus(double x, double y){
        return new Vector(this.x - x, this.y - y);
    }

    public Vector minus(Vector other){
        return new Vector(this.x - other.x, this.y - other.y);
    }

    /**
     *
     * @return the angle on the unit circle in radiants
     */
    public double angle(){
        if (x >= 0){
            return Math.acos(this.x/this.norm());
        }

        return Math.PI+Math.acos(this.x/this.norm());
    }

    /**
     *
     * @return the angle on the unit circle in degrees
     */
    public double angleDegree(){
        if ( x >= 0 ){
            return 180*Math.atan(this.y/this.x)/Math.PI;
        }
        return 180+180*Math.atan(this.y/this.x)/Math.PI;
    }

    /**
     *
     * @param other vector to compare to
     * @return the angle between the two vectors in radiants
     */
    public double angleBetween(Vector other){
        double angle = Math.atan(this.y/this.x);
        double otherAngle = Math.atan(other.y/other.x);

        return angle-otherAngle;
    }

    public double angleBetweenAbs(Vector other){
        double angle = Math.atan(this.y/this.x);
        double otherAngle = Math.atan(other.y/other.x);

        return Math.abs(angle-otherAngle);
    }

    /**
     *
     * @param scale
     * @return the new vector scaled accordingly
     */
    public Vector multiply(double scale){
        return new Vector(this.x*scale, this.y*scale);
    }

    public Vector multiply(double sX, double sY){
        return new Vector(this.x*sX, this.y*sY);
    }

    /**
     *
     * @param scale
     * @return a new vector with each coordinate divided by the scale
     */
    public Vector Divide(double scale){
        return new Vector(this.x/scale, this.y/scale);
    }

    /**
     *
     * @param angle angle used to rotate in radiants, positive goes is the opposite rotation of a clock's
     */
    public void rotate(double angle){
        double x = this.x;
        this.x = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        this.y =      x * Math.sin(angle) + this.y * Math.cos(angle);
    }

    public void rotate90(boolean b){
        double x = this.x;
        if (b){
            this.x = -this.y;
            this.y =       x;
        }
        else{
            this.x = this.y;
            this.y =     -x;
        }
    }

    /**
     *
     * @return a new vector with flipped coordinates
     */
    public Vector getOpposite(){
        return new Vector(-this.x, -this.y);
    }

    /**
     * divide vector by his norm
     */
    public void normalize(){
        double norm = this.norm();
        this.x /= norm;
        this.y /= norm;
    }

    /**
     *
     * @return return a new unit vector with the same angle but a length of 1
     */
    public Vector getNormalized(){
        double norm = this.norm();
        return new Vector(this.x/norm, this.y/norm);
    }

    /**
     *
     * @param other
     * @return the mathematical scalar product
     */
    public double scalarProduct( Vector other ){
        return this.x*other.x + this.y*other.y;
    }

    public Vector projectOn(Vector other){
        return other.getNormalized().multiply(other.scalarProduct(this));
    }

    /**
     *
     * @return a formatted string in the form "x: (x)
     *                                         y: (y)"
     */
    public String toString(){
        return "x: " + this.x + "\ny: " + this.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}