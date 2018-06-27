package fr.unice.polytech.qgl.qaf.json.reply;

import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * TransformReply Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week11
 * @since 14/03/2016
 **/
public class TransformReply {

    private int cost;
    private int production;
    private ResourceType resourceProduced;

    public TransformReply(String reply) {
        parseObject(new JSONObject(reply));
    }

    private void parseObject(JSONObject json) {
        try {
            cost = json.getInt("cost");
        } catch (JSONException e) {
            cost = 0;
        }
        try {
            JSONObject extras = new JSONObject(json.get("extras").toString());
            try {
                production = extras.getInt("production");
            } catch (JSONException e) {
                production = 0;
            }
            try {
                resourceProduced = ResourceType.valueOf(extras.get("kind").toString());
            } catch (JSONException e) {
                resourceProduced = null;
            }
        } catch (JSONException e) {

        }
    }


    public int getCost() {
        return cost;
    }

    public int getProduction() {
        return production;
    }

    public ResourceType getResourceProduced() {
        return resourceProduced;
    }
}
