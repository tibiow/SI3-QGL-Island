package fr.unice.polytech.qgl.qaf.json.reply;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LandReplyTest {
    private LandReply landReply;
    private LandReply badlandreply;

    @Before
    public void defineContext() {
        JSONObject landreplyJSON = new JSONObject();
        landreplyJSON.put("cost",2);
        JSONObject extrasJSON = new JSONObject();
        landreplyJSON.put("extras",extrasJSON.toString());

        this.landReply = new LandReply(landreplyJSON.toString());

        JSONObject badlandreplyJSON = new JSONObject();
        badlandreplyJSON.put("cist",2);
        JSONObject badextrasJSON = new JSONObject();
        landreplyJSON.put("extras",badextrasJSON.toString());

        this.badlandreply = new LandReply(badlandreplyJSON.toString());

    }

    @Test
    public void testParseObject() {
        assertEquals(this.landReply.getCost(),2);
        assertEquals(this.badlandreply.getCost(),0);
    }
}
