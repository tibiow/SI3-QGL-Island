package fr.unice.polytech.qgl.qaf.strategy.terrestrialstrategy;

import fr.unice.polytech.qgl.qaf.strategy.*;
import fr.unice.polytech.qgl.qaf.model.*;
import fr.unice.polytech.qgl.qaf.model.context.*;
import fr.unice.polytech.qgl.qaf.json.*;
import fr.unice.polytech.qgl.qaf.json.reply.*;
import fr.unice.polytech.qgl.qaf.util.*;
import fr.unice.polytech.qgl.qaf.util.resource.*;

import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.logging.*;
import java.io.IOException;

import org.json.*;

/**
 * State for searching and collecting resources in the island
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week08
 * @since 13/11/2015 *
 */
public class SearchingResourcesState extends StrategyState {
    private ResourceType resourceAhead = null;
    private Team team;
    private Heading directionToExplore;
    private int distance;
    private boolean explore;
    private Action currentAction;
    private Logger logger;
    
    public SearchingResourcesState(Strategy strategy,
				   Context context,
				   Team team,
				   Heading directionToExplore,
				   int distance,
				   boolean explore) {
	super(strategy, context);
	this.team = team;
	this.team.updateSearchedBiomesContracts(context);
	this.directionToExplore = directionToExplore;
	this.distance = distance;
	this.explore = explore;

	currentAction = explore? Action.EXPLORE : Action.MOVE;
	initLogger();
    }

    /**
     * Do the next step and return the decision taken
     *
     * @return The next command to send to the engine.
     */
    public JSONObject runStep() {
	JSONObject command;

	switch(currentAction) {
	case EXPLORE:
	    command = Commands.explore();
	    break;
	case MOVE:
	    command = Commands.moveTo(directionToExplore);
	    break;
	case EXPLOIT:
	    command = Commands.exploit(resourceAhead);
	    break;
	case STOP:
		command = Commands.stop();
		break;
	default:
	    command = Commands.stop();
	}

	return command;
    }

    /**
     * Handle the response given by the engine according to the
     * current state of the stategy
     *
     * @param reply the response given by the engine
     */
    public void handleReply(String reply) {
	switch (currentAction) {
	case EXPLORE:
	    this.handleExploreReply(new ExploreReply(reply));
	    break;
	case EXPLOIT:
	    this.handleExploitReply(new ExploitReply(reply));
	    break;
	case MOVE:
	    this.handleMoveToReply(new MoveToReply(reply));
	    break;
        }
    }

    public void handleExploreReply(ExploreReply reply) {
        context.updateBudgetRemaining(reply.getCost());
	currentAction = Action.MOVE;
	if (reply.getResources().isEmpty()) return;
	ArrayList<Contract> contracts = context.getContracts();

	for (Contract c : contracts) {
	    for (ResourceType r : reply.getResources()) {
		if (r == c.getResourceType()) {
		    currentAction = Action.EXPLOIT;
		    resourceAhead = r;
		}
	    }
	}
    }

    public void handleExploitReply(ExploitReply reply) {
        context.updateBudgetRemaining(reply.getCost());
        team.updateResourcesCollected(resourceAhead,reply.getAmount(),context);
		if(context.getContracts().get(context.getContracts().size()-1).isCompleted())
			currentAction = Action.STOP;
		else
			currentAction = Action.MOVE;	
		
		
    }

    public void handleMoveToReply(MoveToReply reply) {
        context.updateBudgetRemaining(reply.getCost());
	
	if (--distance <= 0)
	    strategy.switchState(new SearchingInlandState(strategy, context, team));
	
	currentAction = explore? Action.EXPLORE : Action.MOVE;
    }

    private enum Action {
	MOVE, EXPLORE, EXPLOIT,STOP
    }
    
    private void initLogger() {
        logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.SearchingResourcesState");
        try {
            FileHandler fh = new FileHandler("log/SearchingResourcesStateLog.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
        }
    }
}
