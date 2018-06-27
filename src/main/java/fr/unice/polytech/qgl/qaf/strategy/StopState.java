package fr.unice.polytech.qgl.qaf.strategy;

import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.json.Commands;
import org.json.*;

/**
 * State for stopping the game
 * SI3 - 2015-2016
 * @author  Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week50
 * @since 13/11/2015 *
 */
public class StopState extends StrategyState {
  public StopState(Strategy strategy, Context context) {
    super(strategy, context);
  }

  public JSONObject runStep() {
    return Commands.stop();
  }
  
  public void handleReply(String reply) {
    // TODO : throw an exception if the commands have failed
  }
}
