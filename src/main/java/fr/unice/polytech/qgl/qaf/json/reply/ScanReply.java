package fr.unice.polytech.qgl.qaf.json.reply;

import fr.unice.polytech.qgl.qaf.util.Biome;
import org.json.*;
import java.util.*;

import java.util.logging.*;
import java.io.IOException;

/**
 * Explorer Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week47
 * @since 29/11/2015
 */
public class ScanReply {
    private int cost;
    private String creek;
    private ArrayList<Biome> biomes;
    private Logger logger;

    public ScanReply(String reply) {
	this.initLogger();

	this.creek = null;
        this.biomes = new ArrayList<>();

	parseObject(new JSONObject(reply));
    }

    public ScanReply(int cost, Biome[] biomes, String creek) {
	this.cost = cost;
	this.creek = creek;
	this.biomes = new ArrayList<>(Arrays.asList(biomes));
    }

    public void parseObject(JSONObject json) {
	try {
	    this.cost = json.getInt("cost");
	} catch(JSONException e) {
	    this.cost = 0;
	    logger.log(Level.SEVERE, "Can't find Scan cost field");
	}

	try {
	    JSONObject extras = new JSONObject(json.get("extras").toString());
	    try {
		JSONArray creeksArray = new JSONArray(extras.get("creeks").toString());
		if (creeksArray.length() > 0)
		    this.creek = creeksArray.get(0).toString();
	    } catch(JSONException e) {
		logger.log(Level.SEVERE, "Can't find Scan creeks field");
	    }
	    
	    try {
		JSONArray biomesArray = new JSONArray(extras.get("biomes").toString());
		for (int i = 0; i < biomesArray.length(); i++)
		  this.biomes.add(Biome.valueOf(biomesArray.get(i).toString()));
	    } catch(JSONException e) {
		logger.log(Level.SEVERE, "Can't find Scan biomes field");
	    }
	} catch(JSONException e) {
	    logger.log(Level.SEVERE, "Can't find Scan extras");
	}

    }

    public int getCost() {
	return this.cost;
    }

    public String getCreek() {
	return this.creek;
    }
   
    public ArrayList<Biome> getBiomes() {
      return this.biomes;
    }

    private void initLogger() {
	this.logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.reply.ScanReply");
	try {
	    FileHandler fh = new FileHandler("log/ScanReplyLog.log");
	    fh.setFormatter(new SimpleFormatter());
	    this.logger.addHandler(fh);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public String toString() {
	String result = "{\"cost\": ";
	result += cost + ", ";
	result += "\"extras\": { \"biomes\": [";
	for (Iterator iter = biomes.iterator(); iter.hasNext(); ) {
	    result += "\"" + iter.next().toString() + "\"";
	    if (iter.hasNext()) result += ", ";
	}
	result += "], ";
	result += "\"creeks\": [";
	result += (creek == null)? "" : "\"" + creek + "\"";
	result += "]}, \"status\": \"OK\"}";
	return result;
    }
     
}
