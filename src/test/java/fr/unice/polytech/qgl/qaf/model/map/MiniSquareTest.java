package fr.unice.polytech.qgl.qaf.model.map;

import fr.unice.polytech.qgl.qaf.model.map.MiniSquare;
import org.junit.Test;
import fr.unice.polytech.qgl.qaf.util.TypeOfLand;

import static org.junit.Assert.*;

/**
 * MiniSquareTest Class to test the MiniSquare
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dallé, Antoine Steyer
 **/

public class MiniSquareTest {

  private MiniSquare square;

  /**
   * To test the construtor whitout param
   */
  @Test
  public void testConstructorVoid() {
    square = new MiniSquare();
    assertEquals(square.getNbrScan(), 0);
    assertEquals(square.getType(), TypeOfLand.WATER);
  }

  /**
   * To test the construtor whit param
   */
  @Test
  public void testConstructorFull() {
    square = new MiniSquare(1, TypeOfLand.MUD);
    assertEquals(square.getNbrScan(), 1);
    assertEquals(square.getType(), TypeOfLand.MUD);
  }

  /**
   * To test the methode incrementScanCounter()
   */
  @Test
  public void testIncrementScanCounter() {
    square = new MiniSquare();
    assertEquals(square.getNbrScan(), 0);
    assertEquals(square.getType(), TypeOfLand.WATER);
    square.incrementScanCounter();
    assertEquals(square.getNbrScan(), 1);
    assertEquals(square.getType(), TypeOfLand.WATER);
  }

}
