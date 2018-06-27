package fr.unice.polytech.qgl.qaf.strategy.aerialstrategy;

import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.json.reply.EchoReply;
import fr.unice.polytech.qgl.qaf.model.Drone;
import fr.unice.polytech.qgl.qaf.strategy.Strategy;
import fr.unice.polytech.qgl.qaf.strategy.StrategyState;
import fr.unice.polytech.qgl.qaf.json.Commands;
import fr.unice.polytech.qgl.qaf.util.Heading;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import org.json.*;

import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * State for stopping the game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week50
 * @since 13/11/2015 *
 */
public class LocalizingState extends StrategyState {
    private Drone drone;
    private Map map;
    private Heading initialHeading;
    private Queue<Heading> directions;
    private int casesAtRight;
    private int casesAtLeft;
    private int casesAhead;
    private Logger logger;

    public LocalizingState(Strategy strategy, Context context) {
        super(strategy, context);
        this.drone = strategy.getDrone();
        this.map = this.drone.getEarthMap();
        this.directions = new LinkedList<>();
        this.directions.offer(drone.getInitialHeading().getRotateCw());
        this.directions.offer(drone.getInitialHeading().getRotateCcw());
        this.directions.offer(drone.getInitialHeading());
        this.initLogger();
    }

    public JSONObject runStep() {
        return Commands.echo(directions.poll());

    }

    public void handleReply(String reply) {
        // TODO : throw an exception if the commands have failed
        EchoReply echoReply = new EchoReply(reply);
        context.updateBudgetRemaining(echoReply.getCost());

        switch (directions.size()) {
            case 2:
                this.casesAtRight = echoReply.getRange();
                break;
            case 1:
                this.casesAtLeft = echoReply.getRange();
                this.drone.setDimX(this.casesAtLeft + this.casesAtRight + 1);
                break;
            case 0:
                this.map.dimMapX(this.casesAtLeft, this.casesAtRight);
                this.drone.setPosition(this.casesAtLeft, 0);

                if (echoReply.hasFoundGround()) {
                    this.drone.setGroundAhead(true);
                    this.drone.setMovesRemaining(echoReply.getRange());
                    this.drone.setDimY(echoReply.getRange()+1);
                } else {
                    this.map.dimMapY(echoReply.getRange());
                    this.drone.setDimY(echoReply.getRange()+1);
                }
                this.drone.createAerialMap(this.drone.getDimX(), this.drone.getDimY());
                this.map.createMap(this.drone.getDimX(), this.drone.getDimY());
                logger.info("Localize -> SearchingGround");
                this.strategy.switchState(new SearchingGroundState(this.strategy, this.context));
        }
    }

    private void initLogger() {
        logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.strategy.LocalizingState");
        try {
            FileHandler fh = new FileHandler("log/LocalizingStateLog.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
        }
    }
}
