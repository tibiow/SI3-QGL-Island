package fr.unice.polytech.qgl.qaf.model.map;

import fr.unice.polytech.qgl.qaf.model.map.MiniMap;
import fr.unice.polytech.qgl.qaf.model.map.MiniSquare;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MiniMapTest Class to test the MiniMape
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dallé, Antoine Steyer
 **/

public class MiniMapTest {

  private MiniMap map;

  /**
   * To test the construtor whit two param
   */
  @Test
  public void testConstructorFull() {
    map = new MiniMap(2,3);
    MiniSquare square = new MiniSquare();

    assertEquals(map.getLimX(), 2);
    assertEquals(map.getLimY(), 3);
    assertEquals(map.getTab().size(), 2);
    for (int i = 0; i < 2; i++) {
      assertEquals(map.getTab().get(i).size(), 3);
      for (int j = 0; j < 3; j++) {
        assertEquals(map.getSquare(i, j).getType(), square.getType());
        assertEquals(map.getSquare(i, j).getNbrScan(), square.getNbrScan());
      }
    }
  }

  /**
   * To test the construtor whit two param but null
   */
  @Test
  public void testConstructorFullNull() {
    map = new MiniMap(0,0);
    MiniSquare square = new MiniSquare();

    assertEquals(map.getLimX(), 0);
    assertEquals(map.getLimY(), 0);
    assertEquals(map.getTab().size(), 0);
    for (int i = 0; i < 0; i++) {
      assertEquals(map.getTab().get(i).size(), 0);
      for (int j = 0; j < 0; j++) {
        assertEquals(map.getSquare(i, j).getType(), square.getType());
        assertEquals(map.getSquare(i, j).getNbrScan(), square.getNbrScan());
      }
    }
  }

  /**
   * To test the construtor whit one param 
   */
  @Test
  public void testConstructorOne() {
    map = new MiniMap(2);
    MiniSquare square = new MiniSquare();

    assertEquals(map.getLimX(), 2);
    assertEquals(map.getLimY(), 1);
    assertEquals(map.getTab().size(), 2);
    for (int i = 0; i < 2; i++) {
      assertEquals(map.getTab().get(i).size(), 1);
      for (int j = 0; j < 1; j++) {
        assertEquals(map.getSquare(i, j).getType(), square.getType());
        assertEquals(map.getSquare(i, j).getNbrScan(), square.getNbrScan());
      }
    }
  }

  /**
   * To test the methode newLine(0) 
   */
  @Test
  public void testNewLine0() {
    map = new MiniMap(3,2);
    MiniSquare square = new MiniSquare();

    map.newLine(0);

    assertEquals(map.getLimX(), 3);
    assertEquals(map.getLimY(), 3);
    assertEquals(map.getTab().size(), 3);
    for (int i = 0; i < 3; i++) {
      assertEquals(map.getTab().get(i).size(), 3);
      for (int j = 0; j < 3; j++) {
        assertEquals(map.getSquare(i, j).getType(), square.getType());
        assertEquals(map.getSquare(i, j).getNbrScan(), square.getNbrScan());
      }
    }
  }

  /**
   * To test the methode newLine(7) 
   */
  @Test
  public void testNewLine7() {
    map = new MiniMap(10,2);
    MiniSquare square = new MiniSquare();

    map.newLine(7);

    assertEquals(map.getLimX(), 10);
    assertEquals(map.getLimY(), 10);
    assertEquals(map.getTab().size(), 10);
    for (int i = 0; i < 10; i++) {
      assertEquals(map.getTab().get(i).size(), 10);
      for (int j = 0; j < 10; j++) {
        assertEquals(map.getSquare(i, j).getType(), square.getType());
        assertEquals(map.getSquare(i, j).getNbrScan(), square.getNbrScan());
      }
    }
  }

}
