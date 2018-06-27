package fr.unice.polytech.qgl.qaf.model.map;

import fr.unice.polytech.qgl.qaf.util.Biome;
import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Class for representing cartesian coordinates of an element of the map
 */
public class Area {
    private boolean supposedBiomes;
    private ArrayList<Biome> biomes;
    private Set<Biome> biomesSet;
    private HashMap<ResourceType, Integer> amountOfResources;
    private boolean explored = false;
    private boolean glimpsed = false;
    
    public Area() {
        this(new ArrayList<Biome>());
    }

    public Area(ArrayList<Biome> biomes) {
	this.biomesSet = new HashSet<>();
        this.biomes = biomes;
        this.amountOfResources = new HashMap<>();
        this.supposedBiomes = false;
    }

    /**
     * Return true if the biome is presents on the position
     * @param biome the biome we search
     * @return if the biome is presents
     */
    public boolean biomeOnPosition(Biome biome) {
        return biomes.contains(biome);
    }


    ////////////////////
    /**
     * Add a biome on this area
     * @param newBiome new biome to add
     */
    public void add(Collection<Biome> biomes) {
        biomesSet.addAll(biomes);
    }

    /**
     * Add a biome on this area
     * @param newBiome new biome to add
     */
    public void add(Biome biome) {
        biomesSet.add(biome);
    }

    
    /**
     * Return the biomes contains on the position
     * @return return position's biomes
     */
    public Set<Biome> getBiomeSet() {
        return biomesSet;
    }


    ////////////////////

    /**
     * Return the biomes contains on the position
     * @return return position's biomes
     */
    public ArrayList<Biome> getBiomes() {
        return biomes;
    }

    /**
     * Return if the biomes is supposed else scaned
     * @return return true if biomes supposed, false else if biomes scaned
     */
    public boolean getSupposedBiomes() {
        return supposedBiomes;
    }

    /**
     * Modify the list of biomes
     * @param b list of biomes
     */
    public void setBiomes(ArrayList<Biome> b) {
        biomes = b;
    }

    /**
     * Modify the boolean SupposedBiomes
     * @param s boolean
     */
    public void setSupposedBiomes(boolean s) {
        supposedBiomes = s;
    }

    public HashMap<ResourceType, Integer> getAmountOfResources() {
        return amountOfResources;
    }

    public void setAmountOfResources(HashMap<ResourceType, Integer> a) {
        amountOfResources = a;
    }

    public void setExplored() {
	explored = true;
    }

    public void setGlimpsed() {
	glimpsed = true;
    }

    public boolean hasBeenGlimpsed() {
	return glimpsed;
    }
    
    public boolean hasBeenExplored() {
	return explored;
    }
}
