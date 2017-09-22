package utility;

import geometry.Vector;

public class Pixel implements Comparable{
    private int x,y;
    private byte[] color;

    public Pixel(int x, int y, byte[] color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Pixel(Vector v, byte[] color){
        x = (int) v.getX();
        y = (int) v.getY();
        this.color = color;
    }


    public Pixel(Vector v){
        x = (int) v.getX();
        y = (int) v.getY();
        this.color = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff};
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public byte[] getColor() {
        return color;
    }

    public void setColor(byte[] color) {
        this.color = color;
    }

    @Override
    public int compareTo(Object o) {
        //TODO make pixels sortable
        return 0;
    }
}
