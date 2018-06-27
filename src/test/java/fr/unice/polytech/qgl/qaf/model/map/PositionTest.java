package fr.unice.polytech.qgl.qaf.model.map;

import fr.unice.polytech.qgl.qaf.model.map.Position;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing for class Position
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dallé, Antoine Steyer
 */
public class PositionTest {
    private Position position1;
    private Position position2;

    @Before
    public void defineContext() {
        position1 = new Position(0, 0);
        position2 = new Position(1, 1);
    }

    @Test
    public void testCoordinates() {
        assertEquals(position1.getX(), 0);
        assertEquals(position1.getY(), 0);

        assertEquals(position2.getX(), 1);
        assertEquals(position2.getY(), 1);
    }
}
