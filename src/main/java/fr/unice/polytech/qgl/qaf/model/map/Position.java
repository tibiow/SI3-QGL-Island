package fr.unice.polytech.qgl.qaf.model.map;

/**
 * Class for representing cartesian coordinates of an element in the map
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the horizontal coordinates of the position
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the vertical coordinates of the position
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Set the horizontal coordinates of the position
     * @param x the horizontal coordinates
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the vertical coordinates of the position
     * @param y the vertical coordinates
     */
    public void setY(int y) {
        this.y = y;
    }

}
