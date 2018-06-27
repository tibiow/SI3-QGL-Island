package fr.unice.polytech.qgl.qaf.json.reply;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlyReplyTest {

    private FlyReply flyreply;
    private FlyReply badflyreply;

    @Before
    public void defineContext() {
        JSONObject flyreplyJSON = new JSONObject();
        flyreplyJSON.put("cost",2);
        JSONObject extrasJSON = new JSONObject();
        flyreplyJSON.put("extras",extrasJSON.toString());

        this.flyreply = new FlyReply(flyreplyJSON.toString());

        JSONObject badflyreplyJSON = new JSONObject();
        badflyreplyJSON.put("cist",2);
        JSONObject badextrasJSON = new JSONObject();
        flyreplyJSON.put("extras",badextrasJSON.toString());

        this.badflyreply = new FlyReply(badflyreplyJSON.toString());

    }

    @Test
    public void testParseObject() {
        assertEquals(this.flyreply.getCost(),2);
        assertEquals(this.badflyreply.getCost(),0);
    }

}
