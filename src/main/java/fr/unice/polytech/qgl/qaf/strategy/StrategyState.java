package fr.unice.polytech.qgl.qaf.strategy;

import fr.unice.polytech.qgl.qaf.model.context.Context;
import org.json.*;

/**
 * Abstract class for strategy state
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week50
 * @since 13/11/2015 *
 */
public abstract class StrategyState {
    protected Strategy strategy;
    protected Context context;
    protected JSONObject command;

    /**
     * Constructor for StrategyState
     *
     * @param strategy The strategy related to the step
     */
    public StrategyState(Strategy strategy, Context context) {
        this.strategy = strategy;
        this.context = context;
    }

    /**
     * Do the next step and return the decision taken
     *
     * @return The next command to send to the engine.
     */
    public abstract JSONObject runStep();

    /**
     * Handle the response given by the engine according to the
     * current state of the stategy
     *
     * @param reply the response given by the engine
     */
    public abstract void handleReply(String reply);
}
