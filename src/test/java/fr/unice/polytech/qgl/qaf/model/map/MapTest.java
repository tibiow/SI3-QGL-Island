package fr.unice.polytech.qgl.qaf.model.map;

import fr.unice.polytech.qgl.qaf.util.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapTest {

    private fr.unice.polytech.qgl.qaf.model.map.Map map;
    private fr.unice.polytech.qgl.qaf.model.map.Map map2;
    private fr.unice.polytech.qgl.qaf.model.map.Map map3;

    @Before
    public void defineContext() {
        Heading defaultHeading = Heading.N;
        map = new fr.unice.polytech.qgl.qaf.model.map.Map(defaultHeading);
        map2 = new fr.unice.polytech.qgl.qaf.model.map.Map(defaultHeading);
        map3 = new fr.unice.polytech.qgl.qaf.model.map.Map(defaultHeading);
        map.createMap(5,5);
        map2.dimMapX(0,10);
        map2.dimMapY(11);
        map3.createMap(20,20);
    }

    @Test
    public void testCreateMap() {
        assertEquals(15, map.getLimX());
        assertEquals(15, map.getLimY());
    }

    @Test
    public void testDimMapX() {
        assertEquals(33, map2.getLimX());
    }

    @Test
    public void testDimMapY() {
        assertEquals(36, map2.getLimY());
    }

    @Test
    public void testSetBiomes9Area() {
        ArrayList<Biome> b = new ArrayList<>();
        b.add(Biome.OCEAN);
        b.add(Biome.TAIGA);
        map.setBiomes9Area(0, 0, b);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(b, map.getArea(j, i).getBiomes());
            }
        }
        assertEquals(true, map.getPresentBiomes().containsKey(Biome.OCEAN));
        assertEquals(true, map.getPresentBiomes().containsKey(Biome.TAIGA));
        assertEquals(false, map.getPresentBiomes().containsKey(Biome.SNOW));

        assertEquals(1, map.getPresentBiomes().get(Biome.OCEAN).intValue());
        assertEquals(1, map.getPresentBiomes().get(Biome.TAIGA).intValue());

        map.setBiomes9Area(1, 1, b);

        assertEquals(2, map.getPresentBiomes().get(Biome.OCEAN).intValue());
        assertEquals(2, map.getPresentBiomes().get(Biome.TAIGA).intValue());
    }

    @Test
    public void probabiliseMapTest() {
        ArrayList<Biome> b = new ArrayList<>();
        b.add(Biome.OCEAN);
        b.add(Biome.TAIGA);
        map3.setBiomes9Area(10, 10,b);
        map3.probabiliseMap();

        for (int i = 0; i < 3; i++) { 
            for (int j = 0; j < 3; j++) {
                assertEquals(b, map3.getArea((11*3) + j, (11*3) + i).getBiomes());
                assertEquals(b, map3.getArea((11*3) + j, (10*3) + i).getBiomes());
                assertEquals(b, map3.getArea((10*3) + j, (11*3) + i).getBiomes());
                assertEquals(true, map3.getArea((11*3) + j, (11*3) + i).getSupposedBiomes());
                assertEquals(true, map3.getArea((11*3) + j, (10*3) + i).getSupposedBiomes());
                assertEquals(true, map3.getArea((10*3) + j, (11*3) + i).getSupposedBiomes());

                assertEquals(b, map3.getArea((9*3) + j, (9*3) + i).getBiomes());
                assertEquals(b, map3.getArea((9*3) + j, (10*3) + i).getBiomes());
                assertEquals(b, map3.getArea((10*3) + j, (9*3) + i).getBiomes());
                assertEquals(true, map3.getArea((9*3) + j, (9*3) + i).getSupposedBiomes());
                assertEquals(true, map3.getArea((9*3) + j, (10*3) + i).getSupposedBiomes());
                assertEquals(true, map3.getArea((10*3) + j, (9*3) + i).getSupposedBiomes());

            }
        }
    }

    @Test
    public void probabiliseMapTest2() {
        ArrayList<Biome> b1 = new ArrayList<>();
        b1.add(Biome.OCEAN);
        ArrayList<Biome> b2 = new ArrayList<>();
        b2.add(Biome.TAIGA);
        ArrayList<Biome> b3 = new ArrayList<>();
        b3.add(Biome.OCEAN);
        b3.add(Biome.TAIGA);
        map3.setBiomes9Area(10, 10, b1);
        map3.setBiomes9Area(11, 11, b2);
        map3.probabiliseMap();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(b3, map3.getArea((11*3) + j, (10*3) + i).getBiomes());
                assertEquals(b3, map3.getArea((10*3) + j, (11*3) + i).getBiomes());
                assertEquals(true, map3.getArea((11*3) + j, (10*3) + i).getSupposedBiomes());
                assertEquals(true, map3.getArea((10*3) + j, (11*3) + i).getSupposedBiomes());

                assertEquals(b1, map3.getArea((9*3) + j, (9*3) + i).getBiomes());
                assertEquals(b1, map3.getArea((9*3) + j, (10*3) + i).getBiomes());
                assertEquals(b1, map3.getArea((10*3) + j, (9*3) + i).getBiomes());
                assertEquals(true, map3.getArea((9*3) + j, (9*3) + i).getSupposedBiomes());
                assertEquals(true, map3.getArea((9*3) + j, (10*3) + i).getSupposedBiomes());
                assertEquals(true, map3.getArea((10*3) + j, (9*3) + i).getSupposedBiomes());

            }
        }
    }


}
