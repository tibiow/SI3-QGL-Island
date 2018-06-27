package fr.unice.polytech.qgl.qaf.strategy.aerialstrategy;

import fr.unice.polytech.qgl.qaf.json.reply.*;
import fr.unice.polytech.qgl.qaf.model.Drone;
import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.strategy.aerialstrategy.SearchingGroundState;
import fr.unice.polytech.qgl.qaf.util.*;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class to test the class SearchingGround
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 **/
public class SearchingGroundStateTest {
    @Test
    public void testRunStepAndHandleReply() {
	Map map = new Map();
	Context c = new Context("{}");
	Strategy s = new Strategy(map, Heading.E, c);
	Drone d = s.getDrone();
	d.setPosition(27, 0);
	d.setDimY(30);
	d.setMovesRemaining(0);
	d.setGroundAhead(false);
	d.createAerialMap(d.getDimX(), d.getDimY());
	SearchingGroundState state = new SearchingGroundState(s, c);

	for (int i = (d.getDimY() / 2) - 1; i > 0; --i) {
	    state.runStep();
	    assertEquals(i, d.getMovesRemaining());
	}
	
	state.runStep();
	assertEquals(-1, d.getMovesRemaining());
	assertEquals(Heading.N, d.getCurrentHeading());
	state.runStep();
	assertEquals(0, d.getMovesRemaining());
	
	state.handleReply("{ \"cost\": 1, \"extras\": { \"range\": 4, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
	assertEquals(true, d.hasGroundAhead());	
	
	for (int i = 3; i > 0; --i) {
	    state.runStep();
	    assertEquals(i, d.getMovesRemaining());
	}

	state.runStep();
	assertEquals(true, state.hasReachedGround());		
    }

    @Test
    public void testHandleEcho() {
	Map map = new Map();
	Context c = new Context("{\"budget\" : 10000}");
	Strategy s = new Strategy(map, Heading.E, c);
	Drone d = s.getDrone();
	SearchingGroundState state = new SearchingGroundState(s, c);

	state.handleEcho(new EchoReply("{ \"cost\": 1, \"extras\": { \"range\": 4, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
	assertEquals(true, d.hasGroundAhead());
	assertEquals(4, d.getMovesRemaining());
	assertEquals(9999, c.getBudgetRemaining());
    }

    @Test
    public void testHandleFly() {
	Map map = new Map();
	Context c = new Context("{\"budget\" : 10000}");
	Strategy s = new Strategy(map, Heading.E, c);
	SearchingGroundState state = new SearchingGroundState(s, c);

	state.handleFly(new FlyReply("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }"));
	assertEquals(9998, c.getBudgetRemaining());
    }

    @Test
    public void testHandleHeading() {
	Map map = new Map();
	Context c = new Context("{\"budget\" : 10000}");
	Strategy s = new Strategy(map, Heading.E, c);
	SearchingGroundState state = new SearchingGroundState(s, c);

	state.handleHeading(new HeadingReply("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }"));
	assertEquals(9996, c.getBudgetRemaining());
    }
}
