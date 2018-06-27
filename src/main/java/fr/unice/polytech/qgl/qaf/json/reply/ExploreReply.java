package fr.unice.polytech.qgl.qaf.json.reply;

import fr.unice.polytech.qgl.qaf.util.resource.Amount;
import fr.unice.polytech.qgl.qaf.util.resource.DifficultyToExploit;
import fr.unice.polytech.qgl.qaf.util.resource.ResourceCharacteristics;
import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * ExploreReply Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week08
 * @since 25/02/2016
 **/
public class ExploreReply {
    private int cost;
    private ArrayList<ResourceCharacteristics> resourcesAvailable; //
    private Set<ResourceType> resources;
    private String pois;

    public ExploreReply(String reply) {
        this.cost = 0;
        this.resourcesAvailable = new ArrayList<>();
	this.resources = new HashSet<>();
        parseObject(new JSONObject(reply));
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
                JSONArray resourcesAvailableJSON = new JSONArray(extrasJSON.get("resources").toString());
                JSONObject currentResource;
                for(int i=0; i<resourcesAvailableJSON.length(); i++) {
                    currentResource = new JSONObject(resourcesAvailableJSON.get(i).toString());
                    this.resourcesAvailable.add(
                            new ResourceCharacteristics(ResourceType.valueOf(currentResource.get("resource").toString()),
                                    Amount.valueOf(currentResource.get("amount").toString()),
                                    DifficultyToExploit.valueOf(currentResource.get("cond").toString())));
		    resources.add(ResourceType.valueOf(currentResource.get("resource").toString()));
                }
            } catch (JSONException e) {

            }

            try {
                pois = extrasJSON.get("pois").toString();
            } catch (JSONException e) {
                pois = "";
            }

        } catch (JSONException e) {

        }
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<ResourceCharacteristics> getResourcesAvailable() {
        return resourcesAvailable;
    }

    public String getPois() {
        return pois;
    }

    public Set<ResourceType> getResources() {
	return resources;
    }
}
