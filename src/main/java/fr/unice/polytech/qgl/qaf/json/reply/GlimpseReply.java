package fr.unice.polytech.qgl.qaf.json.reply;

import fr.unice.polytech.qgl.qaf.util.Biome;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.*;
import java.io.IOException;

/**
 * GlimpseReply Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week008
 * @since 25/02/2016
 **/
public class GlimpseReply {
    private int cost;
    private int asked_range;
    private HashMap<Biome, Float> firstTileBiomes; // Information about the first Tile
    private HashMap<Biome, Float> secondTileBiomes; // Information about the second Tile
    private Set<Biome> thirdTileBiomes; // Information about the third Tile
    private Biome fourthTileBiome; // Information about the fourth Tile
    private Logger logger;

    public GlimpseReply(String reply) {
        this.firstTileBiomes = new HashMap<>();
        this.secondTileBiomes = new HashMap<>();
        this.thirdTileBiomes = new HashSet<>();
        this.fourthTileBiome = null;
        parseObject(new JSONObject(reply));
	initLogger();
    }

    private void parseObject(JSONObject json) {

        try {
            this.cost = json.getInt("cost");
        } catch (JSONException e) {
            this.cost = 0;
        }
        try {
            JSONObject extrasJSON = new JSONObject(json.get("extras").toString());

            try {
                this.asked_range = extrasJSON.getInt("asked_range");
            } catch (JSONException e) {
                this.asked_range = 0;
            }
            try {
                JSONArray report = new JSONArray(extrasJSON.get("report").toString());
                for (int i = 0; i < report.length(); i++) {
                    if (i == 0) {
                        JSONArray firstTile = new JSONArray(report.get(i).toString());
                        recupTileInformation(firstTile, 1);
                    } else if (i == 1) {
                        JSONArray secondTile = new JSONArray(report.get(i).toString());
                        recupTileInformation(secondTile, 2);
                    } else if (i == 2) {
                        JSONArray thirdTile = new JSONArray(report.get(i).toString());
                        recupTileInformation(thirdTile, 3);
                    } else if (i == 3) {
                        JSONArray fourthTile = new JSONArray(report.get(i).toString());
                        recupTileInformation(fourthTile, 4);
                    }
                }


            } catch (JSONException e) {
	      //logger.log(Level.SEVERE, "Failed to parse report");
	      //logger.info(e.getMessage());
            }
        } catch (JSONException e) {
	  //logger.log(Level.SEVERE, "Failed to parse glimpse reply");
	  //logger.info(e.getMessage());
        }
    }


    /**
     * Method to store information about a specific Tile
     *
     * @param tile    The tile where there is the information
     * @param numTile The tile's number
     */
    private void recupTileInformation(JSONArray tile, int numTile) {
        switch (numTile) {
            case 1:
                JSONArray oneBiome1;
                for (int i = 0; i < tile.length(); i++) {
                    oneBiome1 = new JSONArray(tile.get(i).toString());
                    firstTileBiomes.put(Biome.valueOf(oneBiome1.get(0).toString()), new Float(oneBiome1.get(1).toString()));
                }
                break;
            case 2:
                JSONArray oneBiome2;
                for (int i = 0; i < tile.length(); i++) {
                    oneBiome2 = new JSONArray(tile.get(i).toString());
                    secondTileBiomes.put(Biome.valueOf(oneBiome2.get(0).toString()), new Float(oneBiome2.get(1).toString()));
                }
                break;
            case 3:
                for (int i = 0; i < tile.length(); i++) {
                    thirdTileBiomes.add(Biome.valueOf(tile.get(i).toString()));
                }
                break;
            case 4:
                fourthTileBiome = Biome.valueOf(tile.get(0).toString());
                break;
        }
    }

    public int getCost() {
        return this.cost;
    }

    public int getAskedRange() {
        return this.asked_range;
    }

    public HashMap<Biome, Float> getFirstTileBiomes() {
        return this.firstTileBiomes;
    }

    public HashMap<Biome, Float> getSecondTileBiomes() {
        return this.secondTileBiomes;
    }

    public Set<Biome> getThirdTileBiomes() {
        return this.thirdTileBiomes;
    }

    public Biome getFourthTileBiome() {
        return this.fourthTileBiome;
    }

    private void initLogger() {
    logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.json.reply");
    try {
	FileHandler fh = new FileHandler("log/GlimpseReplyLog.log");
	fh.setFormatter(new SimpleFormatter());
	logger.addHandler(fh);
    } catch (IOException e) {
    }
}

}
