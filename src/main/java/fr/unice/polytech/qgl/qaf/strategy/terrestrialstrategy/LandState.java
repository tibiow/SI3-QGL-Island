package fr.unice.polytech.qgl.qaf.strategy.terrestrialstrategy;

import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.json.reply.LandReply;
import fr.unice.polytech.qgl.qaf.model.Drone;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.strategy.StrategyState;
import fr.unice.polytech.qgl.qaf.model.Team;
import fr.unice.polytech.qgl.qaf.json.Commands;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * State for landing men on a creek
 * SI3 - 2015-2016
 * @author  Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week50
 * @since 20/01/2016 *
 */
public class LandState extends StrategyState {

    private String creekID;
    private Map map;
    private Logger logger;
    private Drone drone;

    // Default number of men which have to land
    private static final int DEFAULT_NB_MEN = 1;

    public LandState(Strategy strategy, Context context, String creekID) {
        super(strategy,context);
        this.creekID = creekID;
        this.drone = this.strategy.getDrone();
        this.map = this.drone.getEarthMap();
        this.initLogger();
	    logger.info("size : " + context.getContracts().size());
    }

    @Override
    public JSONObject runStep() {
        return Commands.land(this.creekID, DEFAULT_NB_MEN);
    }

    @Override
    public void handleReply(String reply) {
        // TODO : throw an exception if the commands have failed
        LandReply landreply = new LandReply(reply);
        this.context.updateBudgetRemaining(landreply.getCost());
        Team explorers = new Team(drone, DEFAULT_NB_MEN);

        logger.info("Position du drone : "+this.drone.getPosition().getX()+","+this.drone.getPosition().getY());
        logger.info("Position de l'équipe : "+explorers.getPosition().getX()+
		    ","+ explorers.getPosition().getY());
	
	explorers.organize(context);
	this.strategy.switchState(new SearchingInlandState(strategy, context, explorers, null));
     }
    

    private void initLogger() {
        logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.strategy.LandState");
        try {
            FileHandler fh = new FileHandler("log/LandStateLog.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
        }
    }
}
