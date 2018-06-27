package fr.unice.polytech.qgl.qaf.model;

import fr.unice.polytech.qgl.qaf.model.Drone;
import fr.unice.polytech.qgl.qaf.model.Team;
import fr.unice.polytech.qgl.qaf.util.*;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.model.context.Context;

import java.util.*;

import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeamTest {

    private Map map;
    private Team team;
    private Context context;

    @Before
    public void defineContext() {
        map = new Map(Heading.S);
	map.createMap(100, 100);
        Drone drone = new Drone(Heading.S, map);
        team = new Team(drone);

        JSONObject contextJSON = new JSONObject();
        contextJSON.put("men", "10");
        contextJSON.put("budget", "8000");
        JSONArray contracts = new JSONArray();
        contracts.put(0, new JSONObject().put("amount", "10").put("resource", "FISH"));
        contracts.put(1, new JSONObject().put("amount", "20").put("resource", "FLOWER"));
        contracts.put(2, new JSONObject().put("amount", "30").put("resource", "FRUITS"));
        contracts.put(3, new JSONObject().put("amount", "40").put("resource", "FUR"));
        contracts.put(4, new JSONObject().put("amount", "50").put("resource", "ORE"));
        contracts.put(5, new JSONObject().put("amount", "60").put("resource", "QUARTZ"));
        contracts.put(6, new JSONObject().put("amount", "70").put("resource", "SUGAR_CANE"));
        contracts.put(7, new JSONObject().put("amount", "80").put("resource", "WOOD"));
        contracts.put(8, new JSONObject().put("amount", "90").put("resource", "GLASS"));
        contracts.put(9, new JSONObject().put("amount", "100").put("resource", "INGOT"));
        contracts.put(10, new JSONObject().put("amount", "110").put("resource", "LEATHER"));
        contracts.put(11, new JSONObject().put("amount", "120").put("resource", "PLANK"));
        contracts.put(12, new JSONObject().put("amount", "130").put("resource", "RUM"));
        contextJSON.put("contracts", contracts.toString());
        contextJSON.put("heading", "W");
        context = new Context(contextJSON.toString());
    }


    @Test
    public void moveToTest() {
        JSONObject json = new JSONObject();
        json = team.moveTo(Heading.S);
        assertEquals(team.getPosition().getY(), 2);
        json = team.moveTo(Heading.E);
        assertEquals(team.getPosition().getX(), 0);
        json = team.moveTo(Heading.N);
        assertEquals(team.getPosition().getY(), 1);
        json = team.moveTo(Heading.W);
        assertEquals(team.getPosition().getX(), 1);

    }

    @Test
    public void organizeTest() {

        HashMap<Biome, Integer> presentBiomes = new HashMap<>();
        presentBiomes.put(Biome.OCEAN, 2);
        presentBiomes.put(Biome.LAKE, 8);
        presentBiomes.put(Biome.TROPICAL_RAIN_FOREST, 3);
        presentBiomes.put(Biome.TEMPERATE_RAIN_FOREST, 1);
        presentBiomes.put(Biome.TROPICAL_SEASONAL_FOREST, 10);
        map.setPresentBiomes(presentBiomes);

        team.organize(context);

        assertEquals(context.getContracts().get(0).getResourceType(), ResourceType.WOOD);
        assertEquals(context.getContracts().get(1).getResourceType(), ResourceType.FRUITS);
        assertEquals(context.getContracts().get(2).getResourceType(), ResourceType.SUGAR_CANE);
        assertEquals(context.getContracts().get(3).getResourceType(), ResourceType.FISH);
        assertEquals(context.getContracts().get(4).getResourceType(), ResourceType.FUR);
        assertEquals(context.getContracts().get(5).getResourceType(), ResourceType.QUARTZ);
        assertEquals(context.getContracts().get(6).getResourceType(), ResourceType.ORE);
        assertEquals(context.getContracts().get(7).getResourceType(), ResourceType.GLASS);
        assertEquals(context.getContracts().get(8).getResourceType(), ResourceType.INGOT);
        assertEquals(context.getContracts().get(9).getResourceType(), ResourceType.LEATHER);
        assertEquals(context.getContracts().get(10).getResourceType(), ResourceType.PLANK);
        assertEquals(context.getContracts().get(11).getResourceType(), ResourceType.RUM);
        assertEquals(context.getContracts().get(12).getResourceType(), ResourceType.FLOWER);


    }

    @Test
    public void updateSearchedBiomesTest() {
        team.updateSearchedBiomesContracts(context);
        assertEquals(team.getSearchedBiomes().contains(Biome.OCEAN), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.LAKE), true);

        team.updateResourcesCollected(ResourceType.FISH,10,context); 

        assertEquals(team.getSearchedBiomes().contains(Biome.ALPINE), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.GLACIER), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.MANGROVE), true);

        team.updateResourcesCollected(ResourceType.FLOWER,20,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_RAIN_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_SEASONAL_FOREST), true);

        team.updateResourcesCollected(ResourceType.FRUITS,30,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.GRASSLAND), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_RAIN_FOREST), true);

        team.updateResourcesCollected(ResourceType.FUR,40,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.ALPINE), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.SUB_TROPICAL_DESERT), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_DESERT), true);

        team.updateResourcesCollected(ResourceType.ORE,50,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.BEACH), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.SUB_TROPICAL_DESERT), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_DESERT), true);

        team.updateResourcesCollected(ResourceType.QUARTZ,60,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_RAIN_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_SEASONAL_FOREST), true);

        team.updateResourcesCollected(ResourceType.SUGAR_CANE,70,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.MANGROVE), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_RAIN_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_SEASONAL_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_DECIDUOUS_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_RAIN_FOREST), true);

        team.updateResourcesCollected(ResourceType.WOOD,80,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.BEACH), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.SUB_TROPICAL_DESERT), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_DESERT), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.MANGROVE), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_RAIN_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_SEASONAL_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_DECIDUOUS_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_RAIN_FOREST), true);

        team.updateResourcesCollected(ResourceType.GLASS,90,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.ALPINE), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.SUB_TROPICAL_DESERT), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_DESERT), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.MANGROVE), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_RAIN_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_SEASONAL_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_DECIDUOUS_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_RAIN_FOREST), true);

        team.updateResourcesCollected(ResourceType.INGOT,100,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.GRASSLAND), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_RAIN_FOREST), true);

        team.updateResourcesCollected(ResourceType.LEATHER,110,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.MANGROVE), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_RAIN_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_SEASONAL_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_DECIDUOUS_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_RAIN_FOREST), true);

        team.updateResourcesCollected(ResourceType.PLANK,120,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_RAIN_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_SEASONAL_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_DECIDUOUS_FOREST), false);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_RAIN_FOREST), false);
        
        team.updateResourcesCollected(ResourceType.PLANK,120,context);

        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_RAIN_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TROPICAL_SEASONAL_FOREST), true);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_DECIDUOUS_FOREST), false);
        assertEquals(team.getSearchedBiomes().contains(Biome.TEMPERATE_RAIN_FOREST), false);        
    }

}

