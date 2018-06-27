package fr.unice.polytech.qgl.qaf.util;

import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;

import static fr.unice.polytech.qgl.qaf.util.resource.ResourceType.*;

import java.util.logging.*;
import java.io.IOException;

public enum Biome {
    OCEAN                      (FISH),
    LAKE                       (FISH),
    BEACH                      (QUARTZ),
    GRASSLAND                  (),
    MANGROVE                   (FLOWER),
    TROPICAL_RAIN_FOREST       (FRUITS, SUGAR_CANE, WOOD),
    TROPICAL_SEASONAL_FOREST   (FRUITS, SUGAR_CANE, WOOD),
    TEMPERATE_DECIDUOUS_FOREST (WOOD),
    TEMPERATE_RAIN_FOREST      (FUR, WOOD),
    TEMPERATE_DESERT           (ORE, QUARTZ),
    TAIGA                      (WOOD),
    SNOW                       (),
    TUNDRA                     (FUR),
    ALPINE                     (FLOWER, ORE),
    GLACIER                    (FLOWER),
    SHRUBLAND                  (FUR),
    SUB_TROPICAL_DESERT        (ORE, QUARTZ);

    private ResourceType[] productedResources;
    private Logger logger;
    
    Biome(ResourceType... productedResources) {
	this.productedResources = productedResources;

	logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.util.Biome");
	try {
	    FileHandler fh = new FileHandler("log/BiomeLog.log");
	    fh.setFormatter(new SimpleFormatter());
	    logger.addHandler(fh);
	}
	catch (IOException e) {
	    
	}
    }

    public static boolean isProducing(Biome b, ResourceType resource) {
      if (resource == FISH)
	return (b == Biome.OCEAN) || (b == Biome.LAKE);
      if (resource == QUARTZ)
	return (b == Biome.BEACH) || (b == Biome.TEMPERATE_DESERT) || (b == Biome.SUB_TROPICAL_DESERT);
      if (resource == FLOWER)
	return (b == Biome.MANGROVE) || (b == Biome.ALPINE) || (b == Biome.GLACIER);
      if (resource == FRUITS)
	return (b == Biome.TROPICAL_RAIN_FOREST) || (b == Biome.TROPICAL_SEASONAL_FOREST);
      if (resource == SUGAR_CANE)
	return (b == Biome.TROPICAL_RAIN_FOREST) || (b == Biome.TROPICAL_SEASONAL_FOREST);
      if (resource == WOOD)
	return (b == Biome.TROPICAL_RAIN_FOREST) || (b == Biome.TROPICAL_SEASONAL_FOREST) || (b == Biome.TEMPERATE_DECIDUOUS_FOREST) || (b == Biome.TEMPERATE_RAIN_FOREST) || ((b == Biome.TAIGA));
      if (resource == FUR)
	return (b == Biome.TEMPERATE_RAIN_FOREST) || (b == Biome.TUNDRA) || (b == Biome.SHRUBLAND);
      if (resource == ORE)
	return (b == Biome.ALPINE) || (b == Biome.SUB_TROPICAL_DESERT);
      return false;
    }
}



