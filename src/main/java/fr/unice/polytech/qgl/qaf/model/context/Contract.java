package fr.unice.polytech.qgl.qaf.model.context;

import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.json.*;


/**
 * Contract Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week47
 * @since 15/11/2015
 **/

public class Contract {
    private ResourceType resourceType;
    private int amount;
    private boolean completed;
    private boolean isPrimary;

    public Contract(String contract) {
        parseObject(new JSONObject(contract));
        this.completed = false;
    }

    private void parseObject(JSONObject json) {

        try {
            String resourceStr = json.getString("resource");
            switch (resourceStr) {
                case "FISH":
                    resourceType = ResourceType.FISH;
                    isPrimary = true;
                    break;
                case "FLOWER":
                    resourceType = ResourceType.FLOWER;
                    isPrimary = true;
                    break;
                case "FRUITS":
                    resourceType = ResourceType.FRUITS;
                    isPrimary = true;
                    break;
                case "FUR":
                    resourceType = ResourceType.FUR;
                    isPrimary = true;
                    break;
                case "ORE":
                    resourceType = ResourceType.ORE;
                    isPrimary = true;
                    break;
                case "QUARTZ":
                    resourceType = ResourceType.QUARTZ;
                    isPrimary = true;
                    break;
                case "SUGAR_CANE":
                    resourceType = ResourceType.SUGAR_CANE;
                    isPrimary = true;
                    break;
                case "WOOD":
                    resourceType = ResourceType.WOOD;
                    isPrimary = true;
                    break;
                case "GLASS":
                    resourceType = ResourceType.GLASS;
                    isPrimary = false;
                    break;
                case "INGOT":
                    resourceType = ResourceType.INGOT;
                    isPrimary = false;
                    break;
                case "LEATHER":
                    resourceType = ResourceType.LEATHER;
                    isPrimary = false;
                    break;
                case "PLANK":
                    resourceType = ResourceType.PLANK;
                    isPrimary = false;
                    break;
                case "RUM":
                    resourceType = ResourceType.RUM;
                    isPrimary = false;
                    break;
                default:
                    resourceType = null;
                    isPrimary = false;
            }
        } catch (JSONException e) {
            resourceType = null;
        }

        try {
            amount = json.getInt("amount");
        } catch (JSONException e) {
            amount = 0;
        }
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public int getAmount() {
        return this.amount;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public boolean isPrimaryResource() {
        return this.isPrimary;
    }
}

