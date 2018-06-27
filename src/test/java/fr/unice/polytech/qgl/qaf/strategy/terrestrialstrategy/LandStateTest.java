package fr.unice.polytech.qgl.qaf.strategy.terrestrialstrategy;

import fr.unice.polytech.qgl.qaf.json.reply.LandReply;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.strategy.terrestrialstrategy.LandState;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.util.Heading;

import org.json.JSONObject;
import org.json.JSONArray;

public class LandStateTest {

    private LandState landstate;

    @Before
    public void defineContext() {

        JSONObject contextJSON = new JSONObject();
        contextJSON.put("men", "10");
        contextJSON.put("budget", "8000");
        JSONArray contracts = new JSONArray();
        JSONObject c1 = new JSONObject();
        c1.put("amount", "10");
        c1.put("resource", "GLASS");
        contracts.put(0, c1);
        JSONObject c2 = new JSONObject();
        c2.put("amount", "20");
        c2.put("resource", "QUARTZ");
        contracts.put(1, c2);
        contextJSON.put("contracts", contracts.toString());
        contextJSON.put("heading", "E");

        String creekID = "id";
        Map map = new Map(Heading.E);
        Context context = new Context(contextJSON.toString());
        Strategy strategy = new Strategy(map, Heading.E, context);
        landstate = new LandState(strategy, context, creekID);

    }


    @Test
    public void testRunStep() {
        JSONObject json = new JSONObject();
        json = landstate.runStep();
        assertEquals(new JSONObject(json.get("parameters").toString()).getString("creek"), "id");
        assertEquals(new JSONObject(json.get("parameters").toString()).getInt("people"), 1);


    }

}
