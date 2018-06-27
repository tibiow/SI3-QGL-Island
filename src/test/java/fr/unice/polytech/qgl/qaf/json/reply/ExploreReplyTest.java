package fr.unice.polytech.qgl.qaf.json.reply;

import fr.unice.polytech.qgl.qaf.util.resource.Amount;
import fr.unice.polytech.qgl.qaf.util.resource.DifficultyToExploit;
import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExploreReplyTest {

    private ExploreReply exploreReply;

    @Before
    public void defineContext() {
        JSONObject exploreReplyJSON = new JSONObject();
        exploreReplyJSON.put("cost", 5);
        JSONObject extrasJSON = new JSONObject();
        JSONArray resources = new JSONArray();
        JSONObject oneResource = new JSONObject();
        oneResource.put("amount", Amount.HIGH);
        oneResource.put("resource", ResourceType.FUR);
        oneResource.put("cond", DifficultyToExploit.FAIR);
        resources.put(oneResource.toString());
        JSONObject oneResource2 = new JSONObject();
        oneResource2.put("amount", Amount.LOW);
        oneResource2.put("resource", ResourceType.WOOD);
        oneResource2.put("cond", DifficultyToExploit.HARSH);
        resources.put(oneResource2.toString());
        extrasJSON.put("resources", resources.toString());
        extrasJSON.put("pois", "id");
        exploreReplyJSON.put("extras", extrasJSON.toString());

        this.exploreReply = new ExploreReply(exploreReplyJSON.toString());
    }

    @Test
    public void testParseObject() {
        assertEquals(exploreReply.getCost(),5);
        assertEquals(exploreReply.getPois(),"id");

        assertEquals(exploreReply.getResourcesAvailable().get(0).getAmount(), Amount.HIGH );
        assertEquals(exploreReply.getResourcesAvailable().get(0).getDifficultyToExploit(), DifficultyToExploit.FAIR);
        assertEquals(exploreReply.getResourcesAvailable().get(0).getName(), ResourceType.FUR);

        assertEquals(exploreReply.getResourcesAvailable().get(1).getAmount(), Amount.LOW);
        assertEquals(exploreReply.getResourcesAvailable().get(1).getDifficultyToExploit(), DifficultyToExploit.HARSH);
        assertEquals(exploreReply.getResourcesAvailable().get(1).getName(), ResourceType.WOOD);

    }
}
