package fr.unice.polytech.qgl.qaf.json.reply;

import fr.unice.polytech.qgl.qaf.util.Biome;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScanReplyTest {
    private ScanReply scanReply;
    private ScanReply scanReply2;

    @Before
    public void defineContext() {
        JSONObject scanReplyJSON = new JSONObject();
        scanReplyJSON.put("cost", "2");
        JSONObject extrasJSON = new JSONObject();
        JSONArray creeksJSON = new JSONArray();
        JSONArray biomesJSON = new JSONArray();
        creeksJSON.put("id");
        extrasJSON.put("creeks", creeksJSON.toString());
        biomesJSON.put(Biome.BEACH.toString());
        extrasJSON.put("biomes", biomesJSON.toString());
        scanReplyJSON.put("extras", extrasJSON.toString());

        this.scanReply = new ScanReply(scanReplyJSON.toString());

        JSONObject scanReplyJSON2 = new JSONObject();
        scanReplyJSON2.put("cost", "2");
        JSONObject extrasJSON2 = new JSONObject();
        JSONArray creeksJSON2 = new JSONArray();
        JSONArray biomesJSON2 = new JSONArray();
        extrasJSON2.put("creeks", creeksJSON2.toString());
        biomesJSON2.put(Biome.ALPINE.toString());
        extrasJSON2.put("biomes", biomesJSON2.toString());
        scanReplyJSON2.put("extras", extrasJSON2.toString());

        scanReply2 = new ScanReply(scanReplyJSON2.toString());

    }

    @Test
    public void testParseObject() {
        assertEquals(scanReply.getCost(), 2);
        assertEquals(scanReply.getCreek(), "id");
        assertTrue(scanReply.getBiomes().contains(Biome.BEACH));

        assertEquals(scanReply2.getCost(), 2);
        assertEquals(scanReply2.getCreek(), null);
	assertTrue(scanReply2.getBiomes().contains(Biome.ALPINE));
    }

    @Test
    public void testConstructor() {
	ScanReply reply = new ScanReply(2, new Biome[] { Biome.BEACH, Biome.ALPINE }, "id");

	assertEquals(reply.getCost(), 2);
        assertEquals(reply.getCreek(), "id");
        assertTrue(reply.getBiomes().contains(Biome.BEACH));
	assertTrue(reply.getBiomes().contains(Biome.ALPINE));
    }

    @Test
    public void testToString() {
	assertEquals("{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\"], \"creeks\": [\"id\"]}, \"status\": \"OK\"}", scanReply.toString());
    }
}
