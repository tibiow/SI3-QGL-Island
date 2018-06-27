package fr.unice.polytech.qgl.qaf.json.reply;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoveToReplyTest {
    private MoveToReply movetoreply;
    private MoveToReply badmovetoreply;

    @Before
    public void defineContext() {
        JSONObject movetoreplyJSON = new JSONObject();
        movetoreplyJSON.put("cost",2);
        JSONObject extrasJSON = new JSONObject();
        movetoreplyJSON.put("extras",extrasJSON.toString());

        this.movetoreply = new MoveToReply(movetoreplyJSON.toString());

        JSONObject badmovetoreplyJSON = new JSONObject();
        badmovetoreplyJSON.put("cist",2);
        JSONObject badextrasJSON = new JSONObject();
        movetoreplyJSON.put("extras",badextrasJSON.toString());

        this.badmovetoreply = new MoveToReply(badmovetoreplyJSON.toString());

    }

    @Test
    public void testParseObject() {
        assertEquals(this.movetoreply.getCost(),2);
        assertEquals(this.badmovetoreply.getCost(),0);
    }
}
