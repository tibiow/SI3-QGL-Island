package fr.unice.polytech.qgl.qaf.json.reply;

import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoutReplyTest {
    private ScoutReply scoutReply;
    private ScoutReply badscoutReply;

    @Before
    public void defineContext() {
        JSONObject scoutReplyJSON = new JSONObject();
        scoutReplyJSON.put("cost", "5");
        JSONObject extrasJSON = new JSONObject();
        extrasJSON.put("altitude", "1");
        JSONArray resourcesJSON = new JSONArray();
        resourcesJSON.put(ResourceType.FISH.toString());
        extrasJSON.put("resources", resourcesJSON.toString());
        scoutReplyJSON.put("extras", extrasJSON.toString());

        this.scoutReply = new ScoutReply(scoutReplyJSON.toString());

        JSONObject badscoutReplyJSON = new JSONObject();
        scoutReplyJSON.put("coost", "5");
        JSONObject badextrasJSON = new JSONObject();
        badextrasJSON.put("alttitude", "1");
        JSONArray badresourcesJSON = new JSONArray();
        badresourcesJSON.put(ResourceType.FISH.toString());
        badextrasJSON.put("ressources", badresourcesJSON.toString());
        badscoutReplyJSON.put("extras", badextrasJSON.toString());

        this.badscoutReply = new ScoutReply(badscoutReplyJSON.toString());
    }

    @Test
    public void testParseObject() {
        assertEquals(this.scoutReply.getCost(), 5);
        assertEquals(this.scoutReply.getAltitude(), 1);
        assertTrue(this.scoutReply.getArrayResource().contains(ResourceType.FISH));

        assertEquals(this.badscoutReply.getCost(), 0);
        assertEquals(this.badscoutReply.getAltitude(),0);
        assertFalse(this.badscoutReply.getArrayResource().contains(ResourceType.FISH));
    }
}
