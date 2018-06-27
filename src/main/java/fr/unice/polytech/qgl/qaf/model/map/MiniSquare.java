package fr.unice.polytech.qgl.qaf.model.map;

import fr.unice.polytech.qgl.qaf.util.*;

/**
 * MiniSquare Class for the Island Game
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 **/

public class MiniSquare {

  private int nbrScan;
  private TypeOfLand type;
 
  /**
   * Constructor for MiniSquare
   */
  public MiniSquare() {
    this(0, TypeOfLand.WATER);
  }

  /**
   * Constructor for MiniSquare
   * @param : int i for value of nbrScan
   * @param : TypeOfLand for value of type
   */
  public MiniSquare(int i, TypeOfLand t) {
    this.nbrScan = i;
    this.type = t;
  }

  /**
   * Getter for nbrScan
   * @return : int nbrScan
   */
  public int getNbrScan() {
    return this.nbrScan;
  }

  /**
   * Getter for type
   * @return : TypeOfLand type
   */
  public TypeOfLand getType() {
    return this.type;
  }

  /**
   * Setter for type
   * @param : TypeOfLand t
   */
  public void setType(TypeOfLand t) {
    this.type = t;
  }


  /**
   * Setter +1 for nbrScan
   */
  public void incrementScanCounter() {
    this.nbrScan++;
  }

}
