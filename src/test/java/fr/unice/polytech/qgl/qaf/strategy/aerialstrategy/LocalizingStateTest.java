package fr.unice.polytech.qgl.qaf.strategy.aerialstrategy;

import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.strategy.StrategyState;
import fr.unice.polytech.qgl.qaf.strategy.aerialstrategy.LocalizingState;
import fr.unice.polytech.qgl.qaf.util.*;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class to test the class LocalizingState
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 **/
public class LocalizingStateTest {
    @Test
    public void testHandleReply() {
	int casesAtRight = 2;
	int casesAtLeft = 27;
	int casesAhead = 30;
	Heading initialHeading = Heading.E;
	Context c = new Context("{}");
	Map map = new Map();
	Strategy s = new Strategy(map, initialHeading, c);
	StrategyState localizingState = new LocalizingState(s, c);
	
	localizingState.runStep();
	localizingState.handleReply("{ \"cost\": 1, \"extras\": { \"range\": " + casesAtRight + ", \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
	localizingState.runStep();
	localizingState.handleReply("{ \"cost\": 1, \"extras\": { \"range\": " + casesAtLeft + ", \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
	localizingState.runStep();
	localizingState.handleReply("{ \"cost\": 1, \"extras\": { \"range\": " + casesAhead + ", \"found\": \"GROUND\" }, \"status\": \"OK\" }");

	assertEquals(casesAhead, s.getDrone().getMovesRemaining());
	assertEquals((casesAtRight + casesAtLeft + 1) * 3, map.getLimX());
    }

}
