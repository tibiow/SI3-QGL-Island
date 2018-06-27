package fr.unice.polytech.qgl.qaf.model;

import fr.unice.polytech.qgl.qaf.model.map.Position;
import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.model.context.Contract;
import fr.unice.polytech.qgl.qaf.util.*;
import fr.unice.polytech.qgl.qaf.json.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.*;
import java.io.IOException;

import static fr.unice.polytech.qgl.qaf.util.Heading.*;

/**
 * Team class for Island Game
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week04
 * @since 01/01/2016
 */
public class Team {

    // The map of the island
    private Map map;
    // Number of men who composed the team
    protected int nbMen;
    private static final int DEFAULT_NBMEN = 1;

    // Position of the team
    private Position position;

    // The current action decided by the team
    private JSONObject command;

    // A List containing all Biomes the team is searching for in order to finish a contract
    private Set<Biome> searchedBiomes;

    private Logger logger;

    private HashMap<ResourceType,Integer> resourcesCollected;

    /**
     * The team's creation
     *
     * @param drone The team's drone
     * @param nbmen The number of men who composed the team
     */
    public Team(Drone drone, int nbmen) {
        this.map = drone.getEarthMap();
        this.nbMen = nbmen;
        this.position = new Position(drone.getPosition().getX() * 3 + 1, drone.getPosition().getY() * 3 + 1);
        this.searchedBiomes = new HashSet<>();
        resourcesCollected = new HashMap<>();
        resourcesCollected.put(ResourceType.FISH,0);
        resourcesCollected.put(ResourceType.FLOWER,0);
        resourcesCollected.put(ResourceType.FRUITS,0);
        resourcesCollected.put(ResourceType.FUR,0);
        resourcesCollected.put(ResourceType.ORE,0);
        resourcesCollected.put(ResourceType.QUARTZ,0);
        resourcesCollected.put(ResourceType.SUGAR_CANE,0);
        resourcesCollected.put(ResourceType.WOOD,0);
        resourcesCollected.put(ResourceType.GLASS,0);
        resourcesCollected.put(ResourceType.INGOT,0);
        resourcesCollected.put(ResourceType.LEATHER,0);
        resourcesCollected.put(ResourceType.PLANK,0);
        resourcesCollected.put(ResourceType.RUM,0);
        initLogger();
    }

    public Team(Drone drone) {
        this(drone, DEFAULT_NBMEN);
    }

    /**
     * Method to organize contracts
     *
     * @param context the context where contracts have been stored
     */
    public void organize(Context context) {
        ArrayList<Contract> contracts = context.getContracts();
        ArrayList<Integer> list = new ArrayList<>();
        int somme = 0;

        for (int i = 0; i < contracts.size(); i++) {
            Set<Biome> listBiomes = new HashSet<>();
            updateSearchedBiomes(contracts.get(i).getResourceType(), listBiomes);
            somme = 0;
            for (Biome b : listBiomes) {
                if (map.getPresentBiomes().containsKey(b)) {
                    somme = somme + map.getPresentBiomes().get(b);
                }
            }
            if (contracts.get(i).isPrimaryResource()) {
                list.add(somme);
            } else {
                list.add(0);
            }
            somme = 0;
        }
        context.organizeContracts(list);
    }

    /**
     * change biomes in the ArrayList searchedBiomes. searchedBiomes contains biomes that the team is searching.
     *
     * @param context the context where contracts have been stored
     */


    /**
     * change resourceType in the ArrayList biome where there is the resource type
     *
     * @param t      resource type
     * @param biomes list of biome
     */
    public void updateSearchedBiomes(ResourceType t, Set<Biome> biomes) {
        switch (t) {
            case FISH:
                biomes.add(Biome.OCEAN);
                biomes.add(Biome.LAKE);
                break;
            case FLOWER:
                biomes.add(Biome.ALPINE);
                biomes.add(Biome.GLACIER);
                biomes.add(Biome.MANGROVE);
                break;
            case FRUITS:
                biomes.add(Biome.TROPICAL_RAIN_FOREST);
                biomes.add(Biome.TROPICAL_SEASONAL_FOREST);
                break;
            case FUR:
                biomes.add(Biome.GRASSLAND);
                biomes.add(Biome.TEMPERATE_RAIN_FOREST);
                break;
            case ORE:
                biomes.add(Biome.ALPINE);
                biomes.add(Biome.SUB_TROPICAL_DESERT);
                biomes.add(Biome.TEMPERATE_DESERT);
                break;
            case QUARTZ:
                biomes.add(Biome.BEACH);
                biomes.add(Biome.SUB_TROPICAL_DESERT);
                biomes.add(Biome.TEMPERATE_DESERT);
                break;
            case SUGAR_CANE:
                biomes.add(Biome.TROPICAL_RAIN_FOREST);
                biomes.add(Biome.TROPICAL_SEASONAL_FOREST);
                break;
            case WOOD:
                biomes.add(Biome.MANGROVE);
                biomes.add(Biome.TROPICAL_RAIN_FOREST);
                biomes.add(Biome.TROPICAL_SEASONAL_FOREST);
                biomes.add(Biome.TEMPERATE_DECIDUOUS_FOREST);
                biomes.add(Biome.TEMPERATE_RAIN_FOREST);
                break;
            case GLASS:
                biomes.add(Biome.BEACH);
                biomes.add(Biome.SUB_TROPICAL_DESERT);
                biomes.add(Biome.TEMPERATE_DESERT);
                biomes.add(Biome.MANGROVE);
                biomes.add(Biome.TROPICAL_RAIN_FOREST);
                biomes.add(Biome.TROPICAL_SEASONAL_FOREST);
                biomes.add(Biome.TEMPERATE_DECIDUOUS_FOREST);
                biomes.add(Biome.TEMPERATE_RAIN_FOREST);
                break;
            case INGOT:
                biomes.add(Biome.ALPINE);
                biomes.add(Biome.SUB_TROPICAL_DESERT);
                biomes.add(Biome.TEMPERATE_DESERT);
                biomes.add(Biome.MANGROVE);
                biomes.add(Biome.TROPICAL_RAIN_FOREST);
                biomes.add(Biome.TROPICAL_SEASONAL_FOREST);
                biomes.add(Biome.TEMPERATE_DECIDUOUS_FOREST);
                biomes.add(Biome.TEMPERATE_RAIN_FOREST);
                break;
            case LEATHER:
                biomes.add(Biome.GRASSLAND);
                biomes.add(Biome.TEMPERATE_RAIN_FOREST);
                break;
            case PLANK:
                biomes.add(Biome.MANGROVE);
                biomes.add(Biome.TROPICAL_RAIN_FOREST);
                biomes.add(Biome.TROPICAL_SEASONAL_FOREST);
                biomes.add(Biome.TEMPERATE_DECIDUOUS_FOREST);
                biomes.add(Biome.TEMPERATE_RAIN_FOREST);
                break;
            case RUM:
                biomes.add(Biome.TROPICAL_RAIN_FOREST);
                biomes.add(Biome.TROPICAL_SEASONAL_FOREST);
                break;
        }
    }




    /**
     * Return the team's map
     *
     * @return the team's map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Set a new cartography
     *
     * @param map The new map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Return the team's position
     *
     * @return the team's position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Change the team's position
     *
     * @param position The new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Return the current command of the team
     *
     * @return The current command of the team
     */
    public JSONObject getCommand() {
        return command;
    }

    /**
     * Return the list of the biomes the team is searching for
     *
     * @return The list of the biomes the team is searching for
     */
    public Set<Biome> getSearchedBiomes() {
        return this.searchedBiomes;
    }

    public JSONObject glimpse(Heading direction, int range) {
        command = Commands.glimpse(direction, range);

        return command;
    }

    public JSONObject moveTo(Heading direction) {
        if (direction.equals(map.getHeading())) {
            position.setY(position.getY() + 1);

            if (position.getY() >= map.getLimY()) {
                map.newLine(0);
                map.setLimY(map.getLimY() + 1);
            }
        } else if (direction.getRotateCw().equals(map.getHeading())) {
            position.setX(position.getX() - 1);
        } else if (direction.getRotateCcw().equals(map.getHeading())) {
            position.setX(position.getX() + 1);
        } else {
            position.setY(position.getY() - 1);
        }

	map.getArea(position.getX(), position.getY()).setExplored();
	
        command = Commands.moveTo(direction);

        return command;
    }

    public JSONObject exploit(ResourceType resource) {
        command = Commands.exploit(resource);

        return command;
    }


    public JSONObject explore() {
        command = Commands.explore();

        return command;
    }

    private void initLogger() {
        logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.model.Team");
        try {
            FileHandler fh = new FileHandler("log/TeamLog.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
        }
    }

}
