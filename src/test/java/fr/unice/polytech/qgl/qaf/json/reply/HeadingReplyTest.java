package fr.unice.polytech.qgl.qaf.json.reply;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeadingReplyTest {

    private HeadingReply headingReply;
    private HeadingReply badHeadingReply;

    @Before
    public void defineContext() {
        JSONObject headingReplyJSON = new JSONObject();
        headingReplyJSON.put("cost",4);
        JSONObject extrasJSON = new JSONObject();
        headingReplyJSON.put("extras",extrasJSON.toString());

        this.headingReply = new HeadingReply(headingReplyJSON.toString());

        JSONObject badheadingReplyJSON = new JSONObject();
        badheadingReplyJSON.put("cist",4);
        JSONObject badextrasJSON = new JSONObject();
        badheadingReplyJSON.put("extras",badextrasJSON.toString());

        this.badHeadingReply = new HeadingReply(badheadingReplyJSON.toString());
    }

    @Test
    public void testParseObject() {
        assertEquals(this.headingReply.getCost(),4);
        assertEquals(this.badHeadingReply.getCost(),0);
    }
}
