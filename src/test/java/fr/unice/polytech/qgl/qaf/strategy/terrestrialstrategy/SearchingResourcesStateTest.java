package fr.unice.polytech.qgl.qaf.strategy.terrestrialstrategy;

import fr.unice.polytech.qgl.qaf.json.reply.ExploreReply;
import fr.unice.polytech.qgl.qaf.model.Team;
import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.model.map.Position;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.util.Heading;
import fr.unice.polytech.qgl.qaf.util.resource.Amount;
import fr.unice.polytech.qgl.qaf.util.resource.DifficultyToExploit;
import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.json.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Class to test the class SearchingResources
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 **/
public class SearchingResourcesStateTest {
    private SearchingResourcesState state;
    private Map map;
    private Team explorers;
    
    @Before
    public void defineContext() {
        JSONObject contextJSON = new JSONObject();
        contextJSON.put("men", "10");
        contextJSON.put("budget", "8000");
        JSONArray contracts = new JSONArray();
        JSONObject c1 = new JSONObject();
        c1.put("amount", "10");
        c1.put("resource", "ORE");
        contracts.put(0, c1);
        JSONObject c2 = new JSONObject();
        c2.put("amount", "20");
        c2.put("resource", "QUARTZ");
        contracts.put(1, c2);
        contextJSON.put("contracts", contracts.toString());
        contextJSON.put("heading", "E");

        map = new Map(Heading.E);
        map.createMap(90, 90);
        Context context = new Context(contextJSON.toString());
        Strategy strategy = new Strategy(map,Heading.E,context);
	explorers = new Team(strategy.getDrone(), 10);
        explorers.setPosition(new Position(45, 45));
        state = new SearchingResourcesState(strategy, context, explorers, Heading.S, 4, true);
    }

    @Test
    public void testRunStep() {
	Position pos = explorers.getPosition();
	
        JSONObject command = state.runStep();
        assertEquals("move_to", command.get("action"));
	state.handleReply("{ \"cost\": 6, \"extras\": { }, \"status\": \"OK\" }");
	assertTrue(map.getArea(pos.getX(), pos.getY()).hasBeenExplored());
	
	command = state.runStep();
        assertEquals("explore", command.get("action"));
	
        JSONObject exploreReplyJSON = new JSONObject();
        exploreReplyJSON.put("cost", 5);
        JSONObject extrasJSON = new JSONObject();

        JSONArray resources = new JSONArray();
        JSONObject oneResource = new JSONObject();
        oneResource.put("amount", Amount.HIGH);
        oneResource.put("resource", ResourceType.ORE);
        oneResource.put("cond", DifficultyToExploit.FAIR);
        resources.put(oneResource.toString());
        JSONObject oneResource2 = new JSONObject();
        oneResource2.put("amount", Amount.LOW);
        oneResource2.put("resource", ResourceType.WOOD);
        oneResource2.put("cond", DifficultyToExploit.HARSH);
        resources.put(oneResource2.toString());
        extrasJSON.put("resources", resources.toString());
        extrasJSON.put("pois", "id");
        exploreReplyJSON.put("extras", extrasJSON.toString());

        state.handleReply(exploreReplyJSON.toString());

        command = state.runStep();
        assertEquals("exploit",command.get("action"));
        assertEquals("ORE", command.getJSONObject("parameters").get("resource"));

        JSONObject exploitReplyJSON = new JSONObject();
        exploitReplyJSON.put("cost", 3);
        JSONObject extras2JSON = new JSONObject();
        extras2JSON.put("amount", 9);
        exploitReplyJSON.put("extras", extras2JSON.toString());

        state.handleReply(exploitReplyJSON.toString());

        command = state.runStep();
        assertEquals("move_to", command.get("action"));
        assertEquals("S", command.getJSONObject("parameters").get("direction"));
    }



}
