package fr.unice.polytech.qgl.qaf.model.context;

import fr.unice.polytech.qgl.qaf.model.context.Contract;
import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.junit.Test;
import org.junit.Before;
import org.json.JSONObject;

import static org.junit.Assert.*;

public class ContractTest {
    private JSONObject contractFishJSON;
    private JSONObject contractFlowerJSON;
    private JSONObject contractFruitsJSON;
    private JSONObject contractFurJSON;
    private JSONObject contractOreJSON;
    private JSONObject contractQuartzJSON;
    private JSONObject contractSugarJSON;
    private JSONObject contractWoodJSON;
    private JSONObject contractGlassJSON;
    private JSONObject contractIngotJSON;
    private JSONObject contractLeatherJSON;
    private JSONObject contractPlankJSON;
    private JSONObject contractRumJSON;
    private JSONObject contractUnknownJSON;

    private JSONObject badContractJSON;

    @Before
    public void defineContext() {
        contractFishJSON = new JSONObject();
        contractFishJSON.put("amount", "200");
        contractFishJSON.put("resource", ResourceType.FISH.toString());

        contractFlowerJSON = new JSONObject();
        contractFlowerJSON.put("amount", "200");
        contractFlowerJSON.put("resource", ResourceType.FLOWER.toString());

        contractFruitsJSON = new JSONObject();
        contractFruitsJSON.put("amount", "200");
        contractFruitsJSON.put("resource", ResourceType.FRUITS.toString());

        contractFurJSON = new JSONObject();
        contractFurJSON.put("amount", "200");
        contractFurJSON.put("resource", ResourceType.FUR.toString());

        contractOreJSON = new JSONObject();
        contractOreJSON.put("amount", "200");
        contractOreJSON.put("resource", ResourceType.ORE.toString());

        contractQuartzJSON = new JSONObject();
        contractQuartzJSON.put("amount", "200");
        contractQuartzJSON.put("resource", ResourceType.QUARTZ.toString());

        contractSugarJSON = new JSONObject();
        contractSugarJSON.put("amount", "200");
        contractSugarJSON.put("resource", ResourceType.SUGAR_CANE.toString());

        contractWoodJSON = new JSONObject();
        contractWoodJSON.put("amount", "200");
        contractWoodJSON.put("resource", ResourceType.WOOD.toString());

        contractGlassJSON = new JSONObject();
        contractGlassJSON.put("amount", "200");
        contractGlassJSON.put("resource", ResourceType.GLASS.toString());

        contractIngotJSON = new JSONObject();
        contractIngotJSON.put("amount", "200");
        contractIngotJSON.put("resource", ResourceType.INGOT.toString());

        contractLeatherJSON = new JSONObject();
        contractLeatherJSON.put("amount", "200");
        contractLeatherJSON.put("resource", ResourceType.LEATHER.toString());

        contractPlankJSON = new JSONObject();
        contractPlankJSON.put("amount", "200");
        contractPlankJSON.put("resource", ResourceType.PLANK.toString());

        contractRumJSON = new JSONObject();
        contractRumJSON.put("amount", "200");
        contractRumJSON.put("resource", ResourceType.RUM.toString());

        contractUnknownJSON = new JSONObject();
        contractUnknownJSON.put("amount","200");
        contractUnknownJSON.put("resource", "UNKNOWN");

        badContractJSON = new JSONObject();
        badContractJSON.put("Amount", "130");
        badContractJSON.put("ressource", ResourceType.QUARTZ.toString());
    }

    @Test
    public void testParseObject() {
        Contract c1 = new Contract(contractQuartzJSON.toString());
        assertEquals(c1.getAmount(), 200);
        assertEquals(c1.getResourceType(), ResourceType.QUARTZ);
        assertEquals(c1.isCompleted(), false);

        Contract c2 = new Contract(badContractJSON.toString());
        assertEquals(c2.getAmount(), 0);
        assertEquals(c2.getResourceType(), null);
        assertEquals(c2.isCompleted(), false);

        Contract c3 = new Contract(contractFishJSON.toString());
        assertEquals(c3.getResourceType(), ResourceType.FISH);
        assertTrue(c3.isPrimaryResource());

        Contract c4 = new Contract(contractFlowerJSON.toString());
        assertEquals(c4.getResourceType(), ResourceType.FLOWER);

        Contract c5 = new Contract(contractFruitsJSON.toString());
        assertEquals(c5.getResourceType(), ResourceType.FRUITS);

        Contract c6 = new Contract(contractFurJSON.toString());
        assertEquals(c6.getResourceType(), ResourceType.FUR);

        Contract c7 = new Contract(contractOreJSON.toString());
        assertEquals(c7.getResourceType(), ResourceType.ORE);

        Contract c8 = new Contract(contractSugarJSON.toString());
        assertEquals(c8.getResourceType(), ResourceType.SUGAR_CANE);

        Contract c9 = new Contract(contractWoodJSON.toString());
        assertEquals(c9.getResourceType(), ResourceType.WOOD);

        Contract c10 = new Contract(contractGlassJSON.toString());
        assertEquals(c10.getResourceType(), ResourceType.GLASS);

        Contract c11 = new Contract(contractIngotJSON.toString());
        assertEquals(c11.getResourceType(), ResourceType.INGOT);

        Contract c12 = new Contract(contractLeatherJSON.toString());
        assertEquals(c12.getResourceType(), ResourceType.LEATHER);

        Contract c13 = new Contract(contractPlankJSON.toString());
        assertEquals(c13.getResourceType(), ResourceType.PLANK);

        Contract c14 = new Contract(contractRumJSON.toString());
        assertEquals(c14.getResourceType(), ResourceType.RUM);

        Contract c15 = new Contract(contractUnknownJSON.toString());
        assertEquals(c15.getResourceType(), null);
    }
}
