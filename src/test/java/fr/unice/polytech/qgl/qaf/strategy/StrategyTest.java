package fr.unice.polytech.qgl.qaf.strategy;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.util.Heading;
import fr.unice.polytech.qgl.qaf.json.Commands;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import org.json.*;

public class StrategyTest {
  private Strategy strategy;

  @Before
  public void defineContext() {
      Map map = new Map(Heading.E);
      Context context = new Context("{}");
      strategy = new Strategy(map,Heading.E,context);
  }

    @Test
    public void testStrategy(){
	int casesAtRight = 2;
	int casesAtLeft = 27;
	
	assertEquals(Commands.echo(Heading.S).toString(), strategy.runStep().toString());
	strategy.handleReply("{ \"cost\": 1, \"extras\": { \"range\": " + casesAtRight + ", \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
	assertEquals(Commands.echo(Heading.N).toString(), strategy.runStep().toString());
	strategy.handleReply("{ \"cost\": 1, \"extras\": { \"range\": " + casesAtLeft + ", \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");

	assertEquals(casesAtRight + casesAtLeft + 1, strategy.getDrone().getDimX());

	strategy.switchState(new StopState(strategy, new Context("{}")));
	assertEquals(Commands.stop().toString(), strategy.runStep().toString());
    }
  
}
