package fr.unice.polytech.qgl.qaf.json.reply;


import fr.unice.polytech.qgl.qaf.util.Biome;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GlimpseReplyTest {

    private GlimpseReply glimpseReply;

    @Before
    public void defineContext() {
        JSONObject glimpseReplyJSON = new JSONObject();

        glimpseReplyJSON.put("cost", 3);

        JSONObject extrasJSON = new JSONObject();

        extrasJSON.put("asked_range", 4);

        JSONArray reportJSON = new JSONArray();
        JSONArray tile = new JSONArray();

        JSONArray tileBiomes = new JSONArray();

        // First Tile
        tileBiomes.put(Biome.BEACH.toString());
        tileBiomes.put(new Float(59.35));
        tile.put(tileBiomes.toString());
        tileBiomes.put(0, Biome.OCEAN.toString());
        tileBiomes.put(1, new Float(40.65));
        tile.put(tileBiomes.toString());

        reportJSON.put(tile.toString());

        // Second Tile
        tileBiomes.put(0, Biome.OCEAN.toString());
        tileBiomes.put(1, new Float(70.2));
        tile.put(0, tileBiomes.toString());
        tileBiomes.put(0, Biome.BEACH.toString());
        tileBiomes.put(1, new Float(29.8));
        tile.put(1, tileBiomes.toString());

        reportJSON.put(tile.toString());

        // Third Tile
        tile.put(0, Biome.OCEAN.toString());
        tile.put(1, Biome.BEACH.toString());

        reportJSON.put(tile.toString());

        // Fourth Tile
        tile.remove(1);
        tile.put(0, Biome.OCEAN.toString());

        reportJSON.put(tile.toString());

        extrasJSON.put("report", reportJSON.toString());

        glimpseReplyJSON.put("extras", extrasJSON.toString());
        glimpseReplyJSON.put("status", "OK");

        this.glimpseReply = new GlimpseReply(glimpseReplyJSON.toString());
    }

    @Test
    public void testParseObject() {
        assertEquals(this.glimpseReply.getCost(), 3);
        assertEquals(this.glimpseReply.getAskedRange(), 4);
        assertEquals(this.glimpseReply.getFourthTileBiome().toString(), Biome.OCEAN.toString());
        assertTrue(this.glimpseReply.getThirdTileBiomes().contains(Biome.OCEAN));
        assertEquals(this.glimpseReply.getFirstTileBiomes().get(Biome.BEACH), new Float(59.35));
        assertTrue(this.glimpseReply.getSecondTileBiomes().containsKey(Biome.OCEAN));

    }


}

