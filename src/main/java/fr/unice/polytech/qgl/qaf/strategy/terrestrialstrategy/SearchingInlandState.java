package fr.unice.polytech.qgl.qaf.strategy.terrestrialstrategy;

import java.util.logging.*;
import java.io.IOException;
import fr.unice.polytech.qgl.qaf.json.*;
import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.model.context.Contract;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.strategy.StrategyState;
import fr.unice.polytech.qgl.qaf.model.Team;
import fr.unice.polytech.qgl.qaf.util.*;
import fr.unice.polytech.qgl.qaf.json.reply.GlimpseReply;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.model.map.Area;
import fr.unice.polytech.qgl.qaf.model.map.Position;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.HashMap;
import java.util.Random;

/**
 * State for searching the inland during Terrestrial Phase
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week08
 * @since 26/02/2016
 */
public class SearchingInlandState extends StrategyState {
    private Team team;
    private Map map;
    private Queue<Heading> directions;
    private Heading directionToExplore;
    private HashMap<Heading, Integer> movements;
    
    // Distance to the biome to explore
    // Default value : 5
    private int distanceToBiome = 4;
    private Heading defaultDirection;
    
    private static final int DEFAULT_RANGE = 4;

    private Logger logger;
    
    public SearchingInlandState(Strategy strategy,
				Context context,
				Team team,
				Heading defaultDirection) {
        super(strategy, context);
        this.team = team;
	defaultDirection = defaultDirection;
	map = team.getMap();
        directions = new LinkedList<>();
	movements = new HashMap<>();
	
	initLogger();

	for (Heading h : Heading.values())
	    movements.put(h, 4);
	
	Heading ih = map.getHeading();
	Heading[] dir = { ih, ih.getRotateCw().getRotateCw(), ih.getRotateCw(), ih.getRotateCcw() };
	for (int i = 0; i < dir.length; ++i)
		directions.offer(dir[i]);

	
	if (defaultDirection != null) {
	    //movements.remove(defaultDirection.getRotateCw().getRotateCw());
	    directions.remove(defaultDirection.getRotateCw().getRotateCw());
	}
    }

    @Override
    public JSONObject runStep() {
	while(! directions.isEmpty()) {
	    if (hasToGlimpse(directions.peek())) {
		return Commands.glimpse(directions.peek(), DEFAULT_RANGE);		
	    } else {
		determineBestDirection(directions.peek());
		directions.poll();
	    }
	}

	nextStep();
	return strategy.runStep();
    }

    @Override
    public void handleReply(String reply) {
	logger.info("handleReply");
	GlimpseReply glimpseReply = new GlimpseReply(reply);
        context.updateBudgetRemaining(glimpseReply.getCost());

        setBiomesToMap(directions.peek(), glimpseReply);
	determineBestDirection(directions.peek());

	directions.poll();

	if (directions.isEmpty()) nextStep();
    }

    private void setBiomesToMap(Heading direction, GlimpseReply glimpseReply) {
	Heading ih = map.getHeading();
	Position p = team.getPosition();

 	if (direction == ih) {
	    team.getMap().getArea(p.getX(), p.getY()).add(glimpseReply.getFirstTileBiomes().keySet());
	    team.getMap().getArea(p.getX(), p.getY()).setGlimpsed();
	    team.getMap().getArea(p.getX(), p.getY() + 1).add(glimpseReply.getSecondTileBiomes().keySet());
	    team.getMap().getArea(p.getX(), p.getY() + 1).setGlimpsed();
	    team.getMap().getArea(p.getX(), p.getY() + 2).add(glimpseReply.getThirdTileBiomes());
	    team.getMap().getArea(p.getX(), p.getY() + 2).setGlimpsed();
	    team.getMap().getArea(p.getX(), p.getY() + 3).add(glimpseReply.getFourthTileBiome());
	    team.getMap().getArea(p.getX(), p.getY() + 3).setGlimpsed();
	} else if (direction.getRotateCw() == ih) {
	    team.getMap().getArea(p.getX(), p.getY()).add(glimpseReply.getFirstTileBiomes().keySet());
	    team.getMap().getArea(p.getX(), p.getY()).setGlimpsed();
	    team.getMap().getArea(p.getX() - 1, p.getY()).add(glimpseReply.getSecondTileBiomes().keySet());
	    team.getMap().getArea(p.getX() - 1, p.getY()).setGlimpsed();
	    team.getMap().getArea(p.getX() - 2, p.getY()).add(glimpseReply.getThirdTileBiomes());
	    team.getMap().getArea(p.getX() - 2, p.getY()).setGlimpsed();
	    team.getMap().getArea(p.getX() - 3, p.getY()).add(glimpseReply.getFourthTileBiome());
	    team.getMap().getArea(p.getX() - 3, p.getY()).setGlimpsed();
	} else if (direction.getRotateCcw() == ih) {
	    team.getMap().getArea(p.getX(), p.getY()).add(glimpseReply.getFirstTileBiomes().keySet());
	    team.getMap().getArea(p.getX(), p.getY()).setGlimpsed();
	    team.getMap().getArea(p.getX() + 1, p.getY()).add(glimpseReply.getSecondTileBiomes().keySet());
	    team.getMap().getArea(p.getX() + 1, p.getY()).setGlimpsed();
	    team.getMap().getArea(p.getX() + 2, p.getY()).add(glimpseReply.getThirdTileBiomes());
	    team.getMap().getArea(p.getX() + 2, p.getY()).setGlimpsed();
	    team.getMap().getArea(p.getX() + 3, p.getY()).add(glimpseReply.getFourthTileBiome());
	    team.getMap().getArea(p.getX() + 3, p.getY()).setGlimpsed();
	} else {
	    team.getMap().getArea(p.getX(), p.getY()).add(glimpseReply.getFirstTileBiomes().keySet());
	    team.getMap().getArea(p.getX(), p.getY()).setGlimpsed();
	    team.getMap().getArea(p.getX(), p.getY() - 1).add(glimpseReply.getSecondTileBiomes().keySet());
	    team.getMap().getArea(p.getX(), p.getY() - 1).setGlimpsed();
	    team.getMap().getArea(p.getX(), p.getY() - 2).add(glimpseReply.getThirdTileBiomes());
	    team.getMap().getArea(p.getX(), p.getY() - 2).setGlimpsed();
	    team.getMap().getArea(p.getX(), p.getY() - 3).add(glimpseReply.getFourthTileBiome());
	    team.getMap().getArea(p.getX(), p.getY() - 3).setGlimpsed();

        }
    }

    private void determineBestDirection(Heading direction) {
	Set<Biome> secondTileBiomes = getAdjacentBiomes(direction, 1);
	Set<Biome> thirdTileBiomes = getAdjacentBiomes(direction, 2);
	Set<Biome> fourthTileBiome = getAdjacentBiomes(direction, 3);
	Contract c = context.getContracts().get(0);

 	// Second tile
	if (! hasExplored(direction, 1)) {
	    for (Biome b : secondTileBiomes) {
		if (Biome.isProducing(b, c.getResourceType()) && distanceToBiome > 1) {
		    distanceToBiome = 1;
		    directionToExplore = direction;
		}
	    }
	}
	// Avoid oceans
	if ((secondTileBiomes.contains(Biome.OCEAN) || secondTileBiomes.contains(Biome.LAKE)) && secondTileBiomes.size() == 1) {
	    movements.remove(direction);
	    if(direction == defaultDirection)
		defaultDirection = null;
	    return;
	}

	// Third tile
	if (! hasExplored(direction, 2)) {
	    for (Biome b : thirdTileBiomes) {
		if (Biome.isProducing(b, c.getResourceType()) && distanceToBiome > 2) {
		    distanceToBiome = 2;
		    directionToExplore = direction;
		}
	    }
	}
	// Avoid oceans
	if ((thirdTileBiomes.contains(Biome.OCEAN) || thirdTileBiomes.contains(Biome.LAKE)) && thirdTileBiomes.size() == 1) {
	    movements.put(direction, 1);
	    if(direction == defaultDirection)
		defaultDirection = null;
	    return;
	}

	// fourth tile
	if (! hasExplored(direction, 3)) {
	    for (Biome b : fourthTileBiome) {
		if (Biome.isProducing(b, c.getResourceType()) && distanceToBiome > 3) {
		    distanceToBiome = 3;
		    directionToExplore = direction;
		}
	    }
	}
	// Avoid oceans
	if ((fourthTileBiome.contains(Biome.OCEAN) || fourthTileBiome.contains(Biome.LAKE)) && fourthTileBiome.size() == 1) {
	    movements.put(direction, 2);
	    if(direction == defaultDirection)
		defaultDirection = null;
	}
    }

    private boolean hasExplored(Heading direction, int distance) {
	Heading ih = map.getHeading();
	Position p = team.getPosition();
	Area a;
	
	if(direction == ih) {
	    a = team.getMap().getArea(p.getX(), p.getY() + distance);
	} else if (direction.getRotateCw() == ih) {
	    a = team.getMap().getArea(p.getX() - distance, p.getY());
	} else if (direction.getRotateCcw() == ih) {
	    a = team.getMap().getArea(p.getX() + distance, p.getY() + distance);
	} else {
	    a = team.getMap().getArea(p.getX(), p.getY() - distance);
	}

	return a.hasBeenExplored();
    }

    private Set<Biome> getAdjacentBiomes(Heading direction, int distance) {
	Set<Biome> biomes = new HashSet<Biome>();
	Heading ih = map.getHeading();
	Position p = team.getPosition();
	
	if(direction == ih) {
	    biomes.addAll(team.getMap().getArea(p.getX(), p.getY() + distance).getBiomeSet());
	} else if (direction.getRotateCw() == ih) {
	    biomes.addAll(team.getMap().getArea(p.getX() - distance, p.getY()).getBiomeSet());
	} else if (direction.getRotateCcw() == ih) {
	    biomes.addAll(team.getMap().getArea(p.getX() + distance, p.getY()).getBiomeSet());
	} else {
	    biomes.addAll(team.getMap().getArea(p.getX(), p.getY() - distance).getBiomeSet());
	}

	return biomes;
    }

    public boolean hasToGlimpse(Heading direction) {
	Heading ih = map.getHeading();
	Position p = team.getPosition();
	Area a;
	boolean glimpse = false;
	
	if(direction == ih) {
	    for (int i = 1; i < 4 && ! glimpse; ++i) 
		if (! team.getMap().getArea(p.getX(), p.getY() + i).hasBeenGlimpsed())
		    glimpse = true;
	} else if (direction.getRotateCw() == ih) {
	    for (int i = 1; i < 4 && ! glimpse; ++i) 
		if (! team.getMap().getArea(p.getX() - i, p.getY()).hasBeenGlimpsed())
		    glimpse = true;
	} else if (direction.getRotateCcw() == ih) {
	    for (int i = 1; i < 4 && ! glimpse; ++i) 
		if (! team.getMap().getArea(p.getX() + i, p.getY()).hasBeenGlimpsed())
		    glimpse = true;
	} else {
	    for (int i = 1; i < 4 && ! glimpse; ++i) 
		if (! team.getMap().getArea(p.getX(), p.getY() - i).hasBeenGlimpsed())
		    glimpse = true;
	}
	
	return glimpse;
    }

    private void nextStep() {
	boolean exploit = true;
	if (directionToExplore == null) {
	    distanceToBiome = 3;
	    exploit = false;
	    if (defaultDirection == null) {
		List<Heading> keys = new ArrayList<>(movements.keySet());
		directionToExplore = keys.get(new Random().nextInt(keys.size()));
		distanceToBiome = movements.get(directionToExplore);
	    } else {
		directionToExplore = defaultDirection;
	    }
	} 

	strategy.switchState(new SearchingResourcesState(strategy,
							 context,
							 team,
							 directionToExplore,
							 distanceToBiome,
							 exploit));
    }

    public int getDistanceToBiome() {
	return distanceToBiome;
    }

    public Heading getDirectionToExplore() {
	return directionToExplore;
    }

    private void initLogger() {
	logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.strategy.terrestrialstrategy.SearchingInlandState");
	try {
	    FileHandler fh = new FileHandler("log/SearchingInlandStateLog.log");
	    fh.setFormatter(new SimpleFormatter());
	    logger.addHandler(fh);
	} catch (IOException e) {
	}
    }
}
