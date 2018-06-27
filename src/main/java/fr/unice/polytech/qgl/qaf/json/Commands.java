package fr.unice.polytech.qgl.qaf.json;

import fr.unice.polytech.qgl.qaf.util.Heading;
import fr.unice.polytech.qgl.qaf.util.resource.*;
import org.json.*;

public class Commands {
    private Commands() {
    }

    public static JSONObject stop() {
        return new JSONObject().
                put("action", "stop");
    }

    public static JSONObject echo(Heading direction) {
        return new JSONObject()
                .put("action", "echo")
                .put("parameters", new JSONObject()
                        .put("direction", direction.name()));
    }

    public static JSONObject fly() {
        return new JSONObject()
                .put("action", "fly");
    }

    public static JSONObject scan() {
        return new JSONObject()
                .put("action", "scan");
    }

    public static JSONObject heading(Heading direction) {
        return new JSONObject()
                .put("action", "heading")
                .put("parameters", new JSONObject()
                        .put("direction", direction.name()));
    }

    public static JSONObject land(String creekID, int nbMen) {
        return new JSONObject()
                .put("action", "land")
                .put("parameters", new JSONObject()
                        .put("creek", creekID)
                        .put("people", nbMen));
    }

    public static JSONObject glimpse(Heading direction, int range) {
        return new JSONObject()
                .put("action", "glimpse")
                .put("parameters", new JSONObject()
		     .put("direction", direction.name())
		     .put("range", range));
    }

    public static JSONObject moveTo(Heading direction) {
        return new JSONObject()
                .put("action", "move_to")
                .put("parameters", new JSONObject()
                        .put("direction", direction.name()));
    }

    public static JSONObject exploit(ResourceType resource) {
        return new JSONObject()
                .put("action", "exploit")
                .put("parameters", new JSONObject()
                        .put("resource", resource.name()));
    }

    public static JSONObject explore() {
        return new JSONObject()
	    .put("action", "explore");
    }
}
