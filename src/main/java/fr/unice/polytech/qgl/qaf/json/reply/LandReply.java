package fr.unice.polytech.qgl.qaf.json.reply;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * LandReply Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week02
 * @since 18/01/2016
 **/
public class LandReply {
    private int cost;

    public LandReply(String reply) {
        this.parseJSON(new JSONObject(reply));
    }

    public void parseJSON(JSONObject json) {
        try {
            this.cost = json.getInt("cost");
        } catch (JSONException e) {
            this.cost = 0;
        }
    }

    public int getCost() {
        return this.cost;
    }
}
