package fr.unice.polytech.qgl.qaf.model.map;

import fr.unice.polytech.qgl.qaf.util.*;
import java.util.*;

/**
 * Map Class
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week47
 * @since 17/11/2015
 **/

public class Map {
    private int limX;
    private int limY;
    private Heading heading;
    private HashMap<Biome, Integer> presentBiomes;
    private ArrayList<Area> theMap;

    /**
     * Map constructor
     **/
    public Map() {
        this(null);
    }

    /**
     * Map constructor
     **/
    public Map(Heading heading) {
        this.limX = 0;
        this.limY = 0;
        this.heading = heading;
        this.theMap = new ArrayList<>();
        this.presentBiomes = new HashMap<>();
    }

    /**
     * Method to create the map
     **/
    public void createMap(int limitX, int limitY) {
        limY = limitY * 3;
        limX = limitX * 3;
        for (int i = 0; i < limY; i++) {
            for (int j = 0; j < limX; j++) {
                theMap.add(new Area());
            }
        }
    }

    /**
     * Create a nex line of map
     * @param : int n the number of new line
     */
    public void newLine(int n) {
        for (int i = 0; i < (n + 1); i++) {
            for (int j = 0; j < limX; j++) {
                theMap.add(new Area());
            }
        }
        limY = limY + n + 1;
    }

    /**
     * Getter for Area(posX, posY)
     * @param : int posX
     * @param : int posY
     * @return : Area(posX, posY)
     */
    public Area getArea(int posX, int posY) {
        return theMap.get(posX + (posY * limY));
    }

    /**
     * Method to modify list of biome for Area * 9 (posX, posY)
     * @param : int posX
     * @param : int posY
     * @param : ArrayList<Biome> b
     */
    public void setBiomes9Area(int posX, int posY, ArrayList<Biome> b) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                theMap.get(((posX*3) + j) + (((posY*3) + i) * limY)).setBiomes(b);
            }
        }
        for (int i = 0; i < b.size(); i++) {
            if (presentBiomes.containsKey(b.get(i))) {
              presentBiomes.put(b.get(i), presentBiomes.get(b.get(i)) + 1);
            }
            else {
              presentBiomes.put(b.get(i), 1);
            }
        }
    }

    public void probabiliseMap() {
        for (int y = 7; y < limY - 7; y = y + 3) {
            for (int x = 7; x < limX - 7; x = x + 3) {
                if (theMap.get(x + (y * limY)).getBiomes().size() == 0) {
                    setBiomes9Area( x / 3, y / 3, lookAround(x, y));
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            theMap.get(x + j + ((y + i) * limY)).setSupposedBiomes(true);
                        }
                    }
                } 
            }
        }
    }

    public ArrayList<Biome> lookAround(int x, int y) {
        ArrayList<Biome> list = new ArrayList<>();
        for (int i = -3; i <= 3; i = i + 3) {
            for (int j = -3; j <= 3; j = j + 3) {
                if (theMap.get(x + j + ((y + i) * limY)).getBiomes().size() != 0 && !  theMap.get(x + j + ((y + i) * limY)).getSupposedBiomes()) {
                    ArrayList<Biome> listPotential = theMap.get(x + j + ((y + i) * limY)).getBiomes();
                    for (int k = 0; k < listPotential.size(); k++) {
                        if (! list.contains(listPotential.get(k))) {
                            list.add(listPotential.get(k));
                        }
                    } 
                }
            }
        }
        return list;
    }

    /**
     * Method to save the dimensions x of the map and the position of the plane
     * @param left, right, :
     **/
    public void dimMapX(int left, int right) {
        limX = 3 * (1 + left + right);
    }

    /**
     * Method to save the dimensions y of the map and the position of the plane
     * @param up, left, right, heading :
     **/
    public void dimMapY(int up) {
        limY = 3 * (1 + up);
    }

    /**
     * accessor returning the attribute limX
     **/
    public int getLimX() {
        return limX;
    }

    /**
     * accessor returning the attribute limY
     **/
    public int getLimY() {
        return limY;
    }

    /**
     * accessor returning the attribute heading
     */
    public Heading getHeading() {
        return heading;
    }

    /**
     * accessor returning the attribute presentBiomes
     */
    public HashMap<Biome, Integer> getPresentBiomes() {
        return presentBiomes;
    }

    /**
     * settor for the attribute limY
     **/
    public void setLimY(int y) {
        limY = y;
    }

    /**
     * settor for the attribute presentBiomes
     */
    public void setPresentBiomes(HashMap<Biome, Integer> p) {
        presentBiomes = p;
    }
}
