package fr.unice.polytech.qgl.qaf.model.map;

import java.util.*;

/**
 * MiniMap Class for the Island Game
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 **/

public class MiniMap {

    private ArrayList<ArrayList<MiniSquare>> tab;
    private int limX;
    private int limY;

    /**
     * Constructor for MiniMap
     * @param : int dim_X for the dimention limX
     */
    public MiniMap(int dim_X) {
        this(dim_X, 1);
    }

    /**
     * Constructor for MiniMap
     * @param : int dim_X for the dimention limX
     * @param : int dim_Y for the dimention limY
     */
    public MiniMap(int dim_X, int dim_Y) {
        this.tab = new ArrayList<ArrayList<MiniSquare>>();
        this.limX = dim_X;
        this.limY = dim_Y;
        for (int i = 0; i < this.limX; i++) {
            this.tab.add(new ArrayList<MiniSquare>());
            for (int j = 0; j < this.limY; j++) {
                this.tab.get(i).add(new MiniSquare());
            }
        }
    }

    /**
     * Create a nex line of map
     * @param : int n the number of new line
     */
    public void newLine(int n) {
        for (int i = 0; i < this.limX; i++) {
            for (int j = 0; j < n + 1; j++) {
                this.tab.get(i).add(new MiniSquare());
            }
        }
        this.limY = this.limY + n + 1;
    }

    /**
     * Getter for Square(posX, posY)
     * @param : int posX
     * @param : int posY
     * @return : MiniSquare(posX, posY)
     */
    public MiniSquare getSquare(int posX, int posY) {
        return this.tab.get(posX).get(posY);
    }

    /**
     * Getter for limX
     * @return : dimention of limX
     */
    public int getLimX() {
        return this.limX;
    }

    /**
     * Getter for limY
     * @return : dimention of limY
     */
    public int getLimY() {
        return this.limY;
    }

    /**
     * Getter for tab
     * @return : tab
     */
    public ArrayList<ArrayList<MiniSquare>> getTab() {
        return this.tab;
    }

}
