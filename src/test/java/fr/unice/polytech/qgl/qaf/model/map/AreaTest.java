package fr.unice.polytech.qgl.qaf.model.map;

import fr.unice.polytech.qgl.qaf.model.map.Area;
import fr.unice.polytech.qgl.qaf.util.*;
import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class AreaTest {

    private Area area;

    @Test
    public void constructorTest() {
        area = new Area();
        assertEquals(false, area.getSupposedBiomes());

        ArrayList<Biome> b = new ArrayList<>();
        b.add(Biome.OCEAN);
        area = new Area(b);
	assertEquals(b, area.getBiomes());
    }

    @Test
    public void biomeOnPositionTest() {
        ArrayList<Biome> b = new ArrayList<>();
        b.add(Biome.OCEAN);
        area = new Area(b);
        assertEquals(true, area.biomeOnPosition(Biome.OCEAN)); 
        assertEquals(false, area.biomeOnPosition(Biome.BEACH)); 
    }


    /*
    @Test
    public void addBiomesTest() {
        ArrayList<Biome> b = new ArrayList<>();
        b.add(Biome.OCEAN);
        area = new Area(b);
        area.add(Biome.BEACH);
        assertEquals(true, area.biomeOnPosition(Biome.OCEAN));
        assertEquals(true, area.biomeOnPosition(Biome.BEACH));
    }
    */
}
