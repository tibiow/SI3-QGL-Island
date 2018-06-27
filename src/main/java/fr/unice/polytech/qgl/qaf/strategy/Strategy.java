package fr.unice.polytech.qgl.qaf.strategy;

import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.model.Drone;
import fr.unice.polytech.qgl.qaf.strategy.aerialstrategy.LocalizingState;
import fr.unice.polytech.qgl.qaf.util.Heading;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import org.json.*;

/**
 * Abstract class for exploration strategy
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week50
 * @since 13/11/2015 *
 */
public class Strategy {
    private Drone drone;
    private StrategyState state;

    public Strategy(Map map, Heading initialHeading, Context context) {
        this.drone = new Drone(initialHeading, map);
        this.state = new LocalizingState(this, context);
    }

    /**
     * Run the next step and return the decision taken
     *
     * @return A json object which contain the decision taken
     */
    public JSONObject runStep() {
        return state.runStep();
    }

    /**
     * Handle the response given by the engine according to the
     * current state of the stategy
     *
     * @param reply the response given by the engine
     */
    public void handleReply(String reply) {
        state.handleReply(reply);
    }

    /**
     * Switch the current state to another
     *
     * @param state the new state of the strategy
     */
    public void switchState(StrategyState state) {
        this.state = state;
    }

    public Drone getDrone() {
        return this.drone; 
    }

}
