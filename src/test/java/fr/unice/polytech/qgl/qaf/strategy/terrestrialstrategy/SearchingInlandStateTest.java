package fr.unice.polytech.qgl.qaf.strategy.terrestrialstrategy;

import fr.unice.polytech.qgl.qaf.model.Team;
import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.model.map.Position;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.strategy.terrestrialstrategy.SearchingInlandState;
import fr.unice.polytech.qgl.qaf.util.*;
import static fr.unice.polytech.qgl.qaf.util.Biome.*;

import org.json.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


/**
 * Class to test the class SearchingInland
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 **/
public class SearchingInlandStateTest {
    private SearchingInlandState state;
    private Map map;
    private Team explorers;
    
    @Before
    public void defineContext() {
	JSONObject contextJSON = new JSONObject();
	contextJSON.put("men", "10");
	contextJSON.put("budget", "8000");
	JSONArray contracts = new JSONArray();
	JSONObject c1 = new JSONObject();
	c1.put("amount", "20");
	c1.put("resource", "QUARTZ");
	contracts.put(0, c1);
	JSONObject c2 = new JSONObject();
	c2.put("amount", "10");
	c2.put("resource", "ORE");
	contracts.put(1, c2);
	contextJSON.put("contracts", contracts.toString());
	contextJSON.put("heading", "E");

	map = new Map(Heading.E);
	map.createMap(90, 90);
	Context context = new Context(contextJSON.toString());
	Strategy strategy = new Strategy(map,Heading.E,context);
	explorers = new Team(strategy.getDrone(), 10);
	explorers.setPosition(new Position(45, 45));
	state = new SearchingInlandState(strategy, context, explorers, null);
    }

    @Test
    public void testRunStep() {
	Position pos = explorers.getPosition();
	JSONObject command = state.runStep();
	assertEquals("glimpse", command.get("action"));
	assertEquals("E", command.getJSONObject("parameters").get("direction"));

	state.handleReply("{\"cost\": 3, \"extras\": {\"asked_range\": 4,\"report\": [ [ [ \"LAKE\", 59.35 ], [ \"OCEAN\", 40.65 ] ], [ [ \"OCEAN\", 70.2  ], [ \"LAKE\", 29.8  ] ], [ \"OCEAN\", \"LAKE\" ], [ \"OCEAN\" ] ] }, \"status\": \"OK\"}");
	assertTrue(map.getArea(pos.getX(), pos.getY()).getBiomeSet().contains(LAKE));
	assertTrue(map.getArea(pos.getX(), pos.getY()).getBiomeSet().contains(OCEAN));
	assertTrue(map.getArea(pos.getX(), pos.getY() + 1).getBiomeSet().contains(LAKE));
	assertTrue(map.getArea(pos.getX(), pos.getY() + 1).getBiomeSet().contains(OCEAN));
	assertTrue(map.getArea(pos.getX(), pos.getY() + 2).getBiomeSet().contains(LAKE));
	assertTrue(map.getArea(pos.getX(), pos.getY() + 2).getBiomeSet().contains(OCEAN));
	assertTrue(map.getArea(pos.getX(), pos.getY() + 3).getBiomeSet().contains(OCEAN));

	command = state.runStep();
	assertEquals("glimpse", command.get("action"));
	assertEquals("W", command.getJSONObject("parameters").get("direction"));
	
	state.handleReply("{\"cost\": 3, \"extras\": {\"asked_range\": 4,\"report\": [ [ [ \"LAKE\", 59.35 ], [ \"OCEAN\", 40.65 ] ], [ [ \"OCEAN\", 70.2  ], [ \"LAKE\", 29.8  ] ], [ \"OCEAN\", \"LAKE\" ], [ \"OCEAN\" ] ] }, \"status\": \"OK\"}");
	assertTrue(map.getArea(pos.getX(), pos.getY() - 1).getBiomeSet().contains(LAKE));
	assertTrue(map.getArea(pos.getX(), pos.getY() - 1).getBiomeSet().contains(OCEAN));
	assertTrue(map.getArea(pos.getX(), pos.getY() - 2).getBiomeSet().contains(LAKE));
	assertTrue(map.getArea(pos.getX(), pos.getY() - 2).getBiomeSet().contains(OCEAN));
	assertTrue(map.getArea(pos.getX(), pos.getY() - 3).getBiomeSet().contains(OCEAN));

	
	command = state.runStep();
	assertEquals("glimpse", command.get("action"));
	assertEquals("S", command.getJSONObject("parameters").get("direction"));

	System.out.println("FOO");
	state.handleReply("{\"cost\": 3, \"extras\": {\"asked_range\": 4,\"report\": [ [ [ \"LAKE\", 59.35 ], [ \"OCEAN\", 40.65 ] ], [ [ \"OCEAN\", 70.2  ], [ \"LAKE\", 29.8  ] ], [ \"OCEAN\", \"LAKE\" ], [ \"TEMPERATE_DESERT\" ] ] }, \"status\": \"OK\"}");
	System.out.println("BAR");
	assertTrue(map.getArea(pos.getX() + 1, pos.getY()).getBiomeSet().contains(LAKE));
	assertTrue(map.getArea(pos.getX() + 1, pos.getY()).getBiomeSet().contains(OCEAN));
	assertTrue(map.getArea(pos.getX() + 2, pos.getY()).getBiomeSet().contains(LAKE));
	assertTrue(map.getArea(pos.getX() + 2, pos.getY()).getBiomeSet().contains(OCEAN));
	assertTrue(map.getArea(pos.getX() + 3, pos.getY()).getBiomeSet().contains(TEMPERATE_DESERT));
	
	command = state.runStep();
	assertEquals("glimpse", command.get("action"));
	assertEquals("N", command.getJSONObject("parameters").get("direction"));

	assertEquals(Heading.S, state.getDirectionToExplore());
	assertEquals(3, state.getDistanceToBiome());
    }
}
