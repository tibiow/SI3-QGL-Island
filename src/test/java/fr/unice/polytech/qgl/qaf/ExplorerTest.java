package fr.unice.polytech.qgl.qaf;

import org.junit.Test;
import org.junit.Before;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.List;
import java.util.ArrayList;

import fr.unice.polytech.qgl.qaf.*;
import fr.unice.polytech.qgl.qaf.util.Heading;
import fr.unice.polytech.qgl.qaf.json.Commands;

import static org.junit.Assert.*;

public class ExplorerTest {
    private JSONObject contextJSON;
    private JSONObject poorContextJSON;


    @Before
    public void defineContext() {
        contextJSON = new JSONObject();
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
        contextJSON.put("heading", "W");

        poorContextJSON = new JSONObject();
        poorContextJSON.put("men", "10");
        poorContextJSON.put("budget", "50");
        JSONArray poorcontracts = new JSONArray();
        JSONObject poorc1 = new JSONObject();
        poorc1.put("amount", "10");
        poorc1.put("resource", "GLASS");
        poorcontracts.put(0, poorc1);
        JSONObject poorc2 = new JSONObject();
        poorc2.put("amount", "20");
        poorc2.put("resource", "QUARTZ");
        contracts.put(1, poorc2);
        poorContextJSON.put("contracts", poorcontracts.toString());
        poorContextJSON.put("heading", "W");
    }

    @Test
    public void testTakeDecision() {
        Explorer ex = new Explorer();
        ex.initialize(contextJSON.toString());

	//<<<<<<< HEAD
        assertEquals(Commands.echo(Heading.N).toString(),ex.takeDecision());
	ex.initialize(poorContextJSON.toString());
	assertEquals(Commands.stop().toString(),ex.takeDecision());
	/*
	=======
        JSONObject json = new JSONObject();
        JSONObject suborder = new JSONObject();
        suborder.put("direction", "N");
        json.put("action", "echo");
        json.put("parameters", suborder);

        assertEquals(json.toString(),ex.takeDecision());



        JSONObject stopjson = new JSONObject();
        stopjson.put("action", "stop");

        assertEquals(stopjson.toString(),ex.takeDecision());

>>>>>>> 2ce877f26016920775eed3910029345bf1f4d408
	*/
    }

}
