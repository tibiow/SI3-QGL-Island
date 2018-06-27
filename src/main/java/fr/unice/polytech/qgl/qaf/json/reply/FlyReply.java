package fr.unice.polytech.qgl.qaf.json.reply;

import org.json.*;

/**
 * FlyReply Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week48
 * @since 24/11/2015
 **/

public class FlyReply {

    private int cost;

    public FlyReply(String reply) {
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
