package fr.unice.polytech.qgl.qaf.strategy.aerialstrategy;

import fr.unice.polytech.qgl.qaf.model.Drone;
import fr.unice.polytech.qgl.qaf.strategy.terrestrialstrategy.LandState;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.strategy.StrategyState;
import fr.unice.polytech.qgl.qaf.util.TypeOfLand;
import fr.unice.polytech.qgl.qaf.util.Biome;
import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.json.reply.FlyReply;
import fr.unice.polytech.qgl.qaf.json.reply.HeadingReply;
import fr.unice.polytech.qgl.qaf.json.reply.ScanReply;
import fr.unice.polytech.qgl.qaf.model.map.Position;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.model.map.MiniMap;
import org.json.*;

import java.util.logging.*;
import java.io.IOException;
import java.util.*;
/**
 * State for searching the ground
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week50
 * @since 13/11/2015 *
 */
public class SearchingCreekState extends StrategyState {
    private Boolean reachedCreek = false;
    private int memory0 = 0;
    private LinkedList<TypeOfLand> scan = new LinkedList(); // TypeOfLand.GROUND ground, TypeOfLand.MUD ground & water, TypeOfLand.WATER water
    private JSONObject command;
    private Logger logger;
    private Drone drone;
    private Map map;
    private MiniMap minimap;

    public SearchingCreekState(Strategy strategy, Context context) {
        super(strategy, context);
        this.initLogger();
        this.drone = this.strategy.getDrone();
        this.map = drone.getEarthMap();
        this.minimap = drone.getAerialMap();
        for (int i = 0; i < 8; i++)
            this.scan.addFirst(TypeOfLand.WATER);
    }

    /**
     * Do the next step and return the decision taken
     *
     * @return The next command to send to the engine.
     */
    public JSONObject runStep() {
        int posX = drone.getPosition().getX();
        int posY = drone.getPosition().getY();

        if (reachedCreek) {
            return drone.moveForward();
        } else if ((memory0 % 2) == 0) {
            memory0++;
            if (minimap.getSquare(posX, posY).getNbrScan() == 0) {
                return drone.scan();
            } else {
                scan.addFirst(minimap.getSquare(posX, posY).getType());
                minimap.getSquare(posX, posY).incrementScanCounter();
                memory0++;
                if (minimap.getSquare(posX, posY).getNbrScan() > 4) {
                    return drone.moveForward();
                } else {
		    return move();
                }
            }
        } else {
            minimap.getSquare(posX, posY).setType(scan.getFirst());
            memory0++;
            return move();
        }

    }

    public TypeOfLand getLastScan() {
	return scan.getFirst();
    }

    /**
     * Method to choose the next order
     */
    private JSONObject move() {
        if ((scan.getFirst() == TypeOfLand.WATER &&
	     scan.get(1) == TypeOfLand.WATER &&
	     scan.get(2) == TypeOfLand.WATER &&
	     scan.get(3) == TypeOfLand.WATER) ||
	    (scan.getFirst() == TypeOfLand.GROUND &&
	     scan.get(1) == TypeOfLand.GROUND &&
	     scan.get(2) == TypeOfLand.GROUND &&
	     scan.get(3) == TypeOfLand.GROUND)) {
            return drone.moveForward();
	    
        } else if (scan.getFirst() == TypeOfLand.WATER &&
		   scan.get(1) == TypeOfLand.WATER &&
		   scan.get(2) == TypeOfLand.WATER &&
		   scan.get(3) == TypeOfLand.MUD &&
		   scan.get(4) == TypeOfLand.GROUND &&
		   scan.get(5) == TypeOfLand.GROUND &&
		   scan.get(6) == TypeOfLand.GROUND) {
            return drone.moveForward();
	    
        } else if (scan.getFirst() == TypeOfLand.MUD &&
		   scan.get(1) == TypeOfLand.WATER) {
            return drone.turnDroneRight();
	    
        } else if (scan.getFirst() == TypeOfLand.GROUND) {
            return drone.turnDroneRight();
	    
        } else if (scan.getFirst() == TypeOfLand.MUD &&
		   scan.get(1) == TypeOfLand.GROUND) {
            return drone.turnDroneLeft();
	    
        } else if (scan.getFirst() == TypeOfLand.WATER) {
            return drone.turnDroneLeft();
	    
        } else if (scan.getFirst() == TypeOfLand.MUD && scan.get(1) == TypeOfLand.MUD) {
            return drone.moveForward();
	    
        } else {
            return drone.stop();
        }
    }


    /**
     * Handle the response given by the engine according to the
     * current state of the stategy
     *
     * @param reply the response given by the engine
     */
    public void handleReply(String reply) {
        switch (drone.getCommand().get("action").toString()) {
            case "scan":
                handleScan(new ScanReply(reply));
                break;
            case "fly":
                handleFly(new FlyReply(reply));
                break;
            case "heading":
                handleHeading(new HeadingReply(reply));
                break;
        }
    }

    private void handleScan(ScanReply reply) {
        context.updateBudgetRemaining(reply.getCost());

        if (reply.getCreek() != null) {
            reachedCreek = true;
            logger.info(reply.getCreek());
            Position posDrone = new Position(drone.getPosition().getX(),
                    drone.getPosition().getY());
            strategy.switchState(new LandState(strategy, context, reply.getCreek()));
        }

        map.setBiomes9Area(drone.getPosition().getX(), drone.getPosition().getY(), reply.getBiomes());

        boolean ocean = reply.getBiomes().contains(Biome.OCEAN);
        boolean other = (ocean && (reply.getBiomes().size() > 1)) || !ocean;

        if (ocean && other) scan.addFirst(TypeOfLand.MUD);
        else if (ocean) scan.addFirst(TypeOfLand.WATER);
        else if (other) scan.addFirst(TypeOfLand.GROUND);
        else scan.addFirst(TypeOfLand.ERROR);
    }

    private void handleFly(FlyReply reply) {
        context.updateBudgetRemaining(reply.getCost());
    }

    private void handleHeading(HeadingReply reply) {
        context.updateBudgetRemaining(reply.getCost());
    }

    private void initLogger() {
        logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.SearchingCreekState");
        try {
            FileHandler fh = new FileHandler("log/SearchingCreekStateLog.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
        }
    }
}
