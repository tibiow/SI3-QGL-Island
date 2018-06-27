package fr.unice.polytech.qgl.qaf;

import eu.ace_design.island.bot.IExplorerRaid;
import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.json.Commands;
import fr.unice.polytech.qgl.qaf.model.map.Map;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Explorer Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week47
 * @since 13/11/2015
 **/
public class Explorer implements IExplorerRaid {
    private Logger logger;
    private Context context;
    private Strategy strategy;
    private Map map;

    /**
     * Default constructor for the Explorer Class
     **/
    public Explorer() {
        this.initLogger();
    }

    /**
     * Explorer initialization
     *
     * @param context : context of the contract
     **/
    public void initialize(String context) {
        this.context = new Context(context);
        this.map = new Map(this.context.getHeading());
        this.strategy = new Strategy(map, this.context.getHeading(), this.context);
    }


    /**
     * Method to take a decision
     * Analyse the results of the acknowledgeResults method
     *
     * @return JSONObject , our decision
     **/
    public String takeDecision() {
        if (this.context.enoughBudget()) {
            try {
                return strategy.runStep().toString();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error");
		for (StackTraceElement ste : e.getStackTrace()) {
		    logger.info(ste.toString());
		}
                return Commands.stop().toString();
            }
        }
        else return Commands.stop().toString();
    }


    /**
     * Result of our decision
     *
     * @param results : result of our decision
     **/
    public void acknowledgeResults(String results) {
        strategy.handleReply(results);
    }

    private void initLogger() {
        logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.Explorer");
        try {
            FileHandler fh = new FileHandler("log/ExplorerLog.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
        }
    }
}
