package fr.unice.polytech.qgl.qaf.strategy.aerialstrategy;

import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.json.reply.EchoReply;
import fr.unice.polytech.qgl.qaf.json.reply.FlyReply;
import fr.unice.polytech.qgl.qaf.json.reply.HeadingReply;
import fr.unice.polytech.qgl.qaf.model.Drone;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.strategy.StrategyState;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import org.json.*;

import java.util.logging.*;
import java.io.IOException;

/**
 * State for searching the ground
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week50
 * @since 13/11/2015 *
 */
public class SearchingGroundState extends StrategyState {
    private Map map;
    private Drone drone;
    private boolean reachedGround = false;
    private Logger logger;

    /**
     * Constructor for SearchingGroundState
     *
     * @param strategy The strategy related to the phase
     * @param context  The context related to the game
     */
    public SearchingGroundState(Strategy strategy, Context context) {
        super(strategy, context);
        this.drone = this.strategy.getDrone();
        this.map = drone.getEarthMap();
        this.initLogger();
    }

    /**
     * Do the next step and return the decision taken
     *
     * @return The next command to send to the engine.
     */
    public JSONObject runStep() {
        if (this.drone.getMovesRemaining() > 1) {
            // If there's more than one case between drone and ground, we move forward
            this.drone.setMovesRemaining(this.drone.getMovesRemaining() - 1);
            return this.drone.moveForward();
        } else if (this.drone.hasGroundAhead() &&
                (this.drone.getMovesRemaining() == 1 || this.drone.getMovesRemaining() == 0)) {
            // If we reached ground we turn right and switch to next phase
            this.drone.setMovesRemaining(0);
            this.reachedGround = true;
            return this.drone.moveForward();
        } else if (!this.drone.hasGroundAhead() && this.drone.getMovesRemaining() == 1) {
            // If we have no ground ahead we turn the drone
            this.drone.setMovesRemaining(-1);
            if ((this.drone.getDimX() / 2) < this.drone.getPosition().getX()) {
                return this.drone.turnDroneLeft();
            } else {
                return this.drone.turnDroneRight();
            }
        } else if (this.drone.getMovesRemaining() == -1) {
            this.drone.setMovesRemaining(0);
            return this.drone.echo(this.drone.getCurrentHeading());
        } else if (!this.drone.hasGroundAhead() && this.drone.getMovesRemaining() == 0) {
            this.drone.setMovesRemaining((this.drone.getDimY() / 2) - 1);
            return this.drone.moveForward();
        } else {
            return this.drone.stop();
        }
    }

    /**
     * Handle the response given by the engine according to the
     * current state of the strategy
     *
     * @param reply the response given by the engine
     */
    public void handleReply(String reply) {
        if (this.reachedGround) {
            this.strategy.switchState(new SearchingCreekState(this.strategy, this.context));
            return;
        }

        switch (this.drone.getCommand().get("action").toString()) {
            case "echo":
                this.handleEcho(new EchoReply(reply));
                break;
            case "fly":
                this.handleFly(new FlyReply(reply));
                break;
            case "heading":
                this.handleHeading(new HeadingReply(reply));
                break;
        }
    }

    public void handleEcho(EchoReply reply) {
        this.context.updateBudgetRemaining(reply.getCost());
        this.drone.setMovesRemaining(reply.getRange());

        if (reply.hasFoundGround()) {
            this.drone.setGroundAhead(true);
        } else
            this.map.dimMapY(reply.getRange());
    }

    public void handleFly(FlyReply reply) {
        this.context.updateBudgetRemaining(reply.getCost());
    }

    public void handleHeading(HeadingReply reply) {
        this.context.updateBudgetRemaining(reply.getCost());
    }

    public boolean hasReachedGround() {
        return reachedGround;
    }

    private void initLogger() {
        logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.strategy.SearchingGroundState");
        try {
            FileHandler fh = new FileHandler("log/SearchingGroundStateLog.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
        }
    }
}
