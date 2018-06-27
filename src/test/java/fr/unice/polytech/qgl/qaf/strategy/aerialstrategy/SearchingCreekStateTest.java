package fr.unice.polytech.qgl.qaf.strategy.aerialstrategy;

import fr.unice.polytech.qgl.qaf.json.reply.*;
import fr.unice.polytech.qgl.qaf.model.Drone;
import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.model.map.MiniMap;
import fr.unice.polytech.qgl.qaf.model.map.Position;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.strategy.aerialstrategy.SearchingCreekState;
import fr.unice.polytech.qgl.qaf.util.*;

import org.json.*;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class to test the class SearchingGround
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 **/
public class SearchingCreekStateTest {
    @Test
    public void testRunStepAndHandleReply() {
	Map map = new Map(Heading.E);
	map.createMap(90, 90);
	Context c = new Context("{}");
	Strategy s = new Strategy(map, Heading.E, c);
	Drone d = s.getDrone();
	Position pDrone = d.getPosition();
	d.setPosition(22, 15);
	d.setDimX(30);
	d.setDimY(30);
	d.createAerialMap(d.getDimX(), d.getDimY());
	MiniMap miniMap = d.getAerialMap();
	SearchingCreekState state = new SearchingCreekState(s, c);
	state.runStep();
	
	// scan
	assertEquals("scan", d.getCommand().get("action"));
	state.handleReply(new ScanReply(1, new Biome[] { Biome.BEACH, Biome.OCEAN }, null).toString());

	// Turn right
	JSONObject command = state.runStep();
	assertEquals("heading", command.get("action"));
	assertEquals("S", command.getJSONObject("parameters").get("direction"));
	assertEquals(Heading.S, d.getCurrentHeading());

	// scan
	command = state.runStep();
	assertEquals("scan", d.getCommand().get("action"));	
	state.handleReply(new ScanReply(1, new Biome[] { Biome.BEACH, Biome.OCEAN }, null).toString());	
	
	// heading right
	command = state.runStep();
	assertEquals("fly", command.get("action"));

	// scan
	command = state.runStep();
	assertEquals("scan", d.getCommand().get("action"));	
	state.handleReply(new ScanReply(1, new Biome[] { Biome.BEACH, Biome.OCEAN }, null).toString());	
	
	// fly
	command = state.runStep();
	assertEquals("fly", command.get("action"));

	// scan
	command = state.runStep();
	assertEquals("scan", d.getCommand().get("action"));	
	state.handleReply(new ScanReply(1, new Biome[] { Biome.OCEAN }, null).toString());

	// heading left
	command = state.runStep();
	assertEquals("heading", command.get("action"));
	assertEquals("E", command.getJSONObject("parameters").get("direction"));
	assertEquals(Heading.E, d.getCurrentHeading());

	// scan
	command = state.runStep();
	assertEquals("scan", d.getCommand().get("action"));	
	state.handleReply(new ScanReply(1, new Biome[] { Biome.OCEAN }, null).toString());

	// heading left
	command = state.runStep();
	assertEquals("heading", command.get("action"));
	assertEquals("N", command.getJSONObject("parameters").get("direction"));
	assertEquals(Heading.N, d.getCurrentHeading());

	// scan
	command = state.runStep();
	assertEquals("scan", d.getCommand().get("action"));	
	state.handleReply(new ScanReply(1, new Biome[] { Biome.BEACH, Biome.OCEAN }, null).toString());

	// heading left
	command = state.runStep();
	assertEquals("heading", command.get("action"));
	assertEquals("E", command.getJSONObject("parameters").get("direction"));
	assertEquals(Heading.E, d.getCurrentHeading());

	// scan
	command = state.runStep();
	assertEquals("scan", d.getCommand().get("action"));	
	state.handleReply(new ScanReply(1, new Biome[] { Biome.BEACH, Biome.OCEAN }, null).toString());

	// fly
	command = state.runStep();
	assertEquals("fly", command.get("action"));

	// scan
	command = state.runStep();
	assertEquals("scan", d.getCommand().get("action"));	
	state.handleReply(new ScanReply(1, new Biome[] { Biome.OCEAN }, null).toString());

	// heading left
	command = state.runStep();
	assertEquals("heading", command.get("action"));
	assertEquals("N", command.getJSONObject("parameters").get("direction"));
	assertEquals(Heading.N, d.getCurrentHeading());
    }

    @Test
    public void testHandleScan() {
	Map map = new Map(Heading.E);
	map.createMap(90, 90);
	Context c = new Context("{\"budget\" : 10000}");
	Strategy s = new Strategy(map, Heading.E, c);
	Drone d = s.getDrone();
	Position pDrone = d.getPosition();
	d.setPosition(22, 15);
	d.setDimX(30);
	d.setDimY(30);
	d.createAerialMap(d.getDimX(), d.getDimY());
	MiniMap miniMap = d.getAerialMap();
	SearchingCreekState state = new SearchingCreekState(s, c);

	d.scan();
	state.handleReply(new ScanReply(1, new Biome[] { Biome.BEACH, Biome.OCEAN }, null).toString());
	assertEquals(9999, c.getBudgetRemaining());
	for (int i = 0; i < 3; ++i) {
	    for (int j = 0; j < 3; ++j) {
		assertTrue(map.getArea(pDrone.getX() * 3 + i, pDrone.getY() * 3 + j).biomeOnPosition(Biome.BEACH));
		assertTrue(map.getArea(pDrone.getX() * 3 + i, pDrone.getY() * 3 + j).biomeOnPosition(Biome.OCEAN));
	    }
	}
	assertEquals(TypeOfLand.MUD, state.getLastScan());

	d.moveForward();
	d.scan();
	state.handleReply(new ScanReply(1, new Biome[] { Biome.OCEAN }, null).toString());
	for (int i = 0; i < 3; ++i) {
	    for (int j = 0; j < 3; ++j) {
		assertFalse(map.getArea(pDrone.getX() * 3 + i, pDrone.getY() * 3 + j).biomeOnPosition(Biome.BEACH));
		assertTrue(map.getArea(pDrone.getX() * 3 + i, pDrone.getY() * 3 + j).biomeOnPosition(Biome.OCEAN));
	    }
	}	
	assertEquals(TypeOfLand.WATER, state.getLastScan());

	d.moveForward();
	d.scan();
	state.handleReply(new ScanReply(1, new Biome[] { Biome.BEACH }, null).toString());
	for (int i = 0; i < 3; ++i) {
	    for (int j = 0; j < 3; ++j) {
		assertTrue(map.getArea(pDrone.getX() * 3 + i, pDrone.getY() * 3 + j).biomeOnPosition(Biome.BEACH));
		assertFalse(map.getArea(pDrone.getX() * 3 + i, pDrone.getY() * 3 + j).biomeOnPosition(Biome.OCEAN));
	    }
	}
	assertEquals(TypeOfLand.GROUND, state.getLastScan());	

	d.moveForward();
	d.scan();
    }

    @Test
    public void testHandleHeading() {
	Map map = new Map(Heading.E);
	Context c = new Context("{\"budget\" : 10000}");
	Strategy s = new Strategy(map, Heading.E, c);
	Drone d = s.getDrone();
	d.setPosition(22, 15);
	d.setDimX(30);
	d.setDimY(30);
	d.createAerialMap(d.getDimX(), d.getDimY());
	Position pDrone = d.getPosition();
	d.turnDroneRight();
	
	s.handleReply("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
	assertEquals(9996, c.getBudgetRemaining());
    }

    @Test
    public void testHandleFly() {
	Map map = new Map(Heading.E);
	Context c = new Context("{\"budget\" : 10000}");
	Strategy s = new Strategy(map, Heading.E, c);
	Drone d = s.getDrone();
	d.setPosition(22, 15);
	d.setDimX(30);
	d.setDimY(30);
	d.createAerialMap(d.getDimX(), d.getDimY());
	Position pDrone = d.getPosition();
	
	d.moveForward();
	s.handleReply("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
	assertEquals(9996, c.getBudgetRemaining());
    }
}
