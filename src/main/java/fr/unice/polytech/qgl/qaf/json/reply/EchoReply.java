package fr.unice.polytech.qgl.qaf.json.reply;

import org.json.*;

/**
 * EchoReply Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week48
 * @since 24/11/2015
 **/

public class EchoReply {
    private int cost;
    private int range;
    private boolean ground;
    private boolean status;

    private static final String GROUND = "GROUND";
    private static final String OUT_OF_RANGE = "OUT_OF_RANGE";
    private static final String OK = "OK";

    public EchoReply(String reply) {
        this.parseJSON(new JSONObject(reply));
    }

    private void parseJSON(JSONObject json) {
        try {
            this.cost = json.getInt("cost");
        } catch (JSONException e) {
            this.cost = 0;
        }

        JSONObject extras = new JSONObject(json.get("extras").toString());

        try {
            this.range = extras.getInt("range");
        } catch (JSONException e) {
            this.range = 0;
        }

        try {
            String found = extras.getString("found");

            if (found.equals(GROUND)) {
                this.ground = true;
            } else if (found.equals(OUT_OF_RANGE)) {
                this.ground = false;
            }
        } catch (JSONException e) {
            this.ground = false;
        }

        try {
            String status = json.getString("status");

            switch (status) {
                case OK:
                    this.status = true;
                    break;
                default:
                    this.status = false;
            }
        } catch (JSONException e) {
            this.status = false;
        }
    }

    public int getCost() {
        return this.cost;
    }

    public int getRange() {
        return this.range;
    }

    public boolean hasFoundGround() {
        return this.ground;
    }

    public boolean getStatus() {
        return this.status;
    }
}
