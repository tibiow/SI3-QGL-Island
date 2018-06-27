package fr.unice.polytech.qgl.qaf.json.reply;

import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * ScoutReply Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week03
 * @since 26/01/2016
 **/
public class ScoutReply {
    private int cost;
    private int altitude;
    private Set<ResourceType> resources;

    public ScoutReply(String reply) {
        this.resources = new HashSet<>();
        parseObject(new JSONObject(reply));
    }

    public void parseObject(JSONObject json) {
        try {
            this.cost = json.getInt("cost");
        } catch (JSONException e) {
            this.cost = 0;
        }
        try {
            JSONObject extras = new JSONObject(json.get("extras").toString());
            try {
                this.altitude = extras.getInt("altitude");
            } catch (JSONException e) {
                this.altitude = 0;
            }
            try {
                JSONArray resourcesArray = new JSONArray(extras.get("resources").toString());

                for (int i = 0; i < resourcesArray.length(); i++)
                    this.resources.add(ResourceType.valueOf(resourcesArray.get(i).toString()));
            } catch (JSONException e) {

            }
        } catch (JSONException e) {

        }
    }

    public int getCost() {
        return this.cost;
    }

    public int getAltitude() {
        return this.altitude;
    }

    public Set<ResourceType> getArrayResource() {
        return this.resources;
    }

}
