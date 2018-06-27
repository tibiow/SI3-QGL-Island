package fr.unice.polytech.qgl.qaf.json.reply;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EchoReplyTest {

    private EchoReply echoreply1;
    private EchoReply echoreply2;
    private EchoReply badechoreply;

    @Before
    public void defineContext() {
        JSONObject echoreply1JSON = new JSONObject();
        echoreply1JSON.put("cost", 1);
        JSONObject extrasJSON = new JSONObject();
        extrasJSON.put("range", 2);
        extrasJSON.put("found", "GROUND");
        echoreply1JSON.put("extras", extrasJSON.toString());
        echoreply1JSON.put("status","OK");

        this.echoreply1 = new EchoReply(echoreply1JSON.toString());

        JSONObject echoreply2JSON = new JSONObject();
        echoreply2JSON.put("cost", 1);
        JSONObject extrasJSON2 = new JSONObject();
        extrasJSON2.put("range", 0);
        extrasJSON2.put("found", "OUT_OF_RANGE");
        echoreply2JSON.put("extras", extrasJSON2.toString());

        this.echoreply2 = new EchoReply(echoreply2JSON.toString());

        JSONObject badechoreplyJSON = new JSONObject();
        badechoreplyJSON.put("cast", 1);
        JSONObject badextrasJSON = new JSONObject();
        badextrasJSON.put("ronge", 2);
        badextrasJSON.put("fiund", "GROUND");
        badechoreplyJSON.put("extras", badextrasJSON.toString());
        badechoreplyJSON.put("status","NOTOK");

        this.badechoreply = new EchoReply(badechoreplyJSON.toString());
    }

    @Test
    public void testParseObject() {
        assertEquals(this.echoreply1.getCost(), 1);
        assertEquals(this.echoreply1.getRange(), 2);
        assertEquals(this.echoreply1.hasFoundGround(), true);
        assertEquals(this.echoreply1.getStatus(),true);

        assertEquals(this.echoreply2.getCost(), 1);
        assertEquals(this.echoreply2.getRange(), 0);
        assertEquals(this.echoreply2.hasFoundGround(), false);

        assertEquals(this.badechoreply.getCost(), 0);
        assertEquals(this.badechoreply.getRange(), 0);
        assertEquals(this.badechoreply.hasFoundGround(), false);
        assertEquals(this.badechoreply.getStatus(),false);

    }
}
