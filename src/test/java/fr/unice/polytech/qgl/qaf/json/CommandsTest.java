package fr.unice.polytech.qgl.qaf.json;


import org.junit.Test;
import fr.unice.polytech.qgl.qaf.util.Heading;
import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;

import static org.junit.Assert.*;

/**
 * Class to test the class Commands
 * SI3 - 2015-2016
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 **/
public class CommandsTest {
    @Test
    public void testCommands() {
	String stop = "{\"action\":\"stop\"}";
	assertEquals(stop, Commands.stop().toString());

	String echo = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}";
	assertEquals(echo, Commands.echo(Heading.E).toString());

	String fly = "{\"action\":\"fly\"}";
	assertEquals(fly, Commands.fly().toString());

	String scan = "{\"action\":\"scan\"}";
	assertEquals(scan, Commands.scan().toString());

	String heading = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}";
	assertEquals(heading, Commands.heading(Heading.E).toString());

	String land = "{\"action\":\"land\",\"parameters\":{\"creek\":\"id\",\"people\":42}}";
	assertEquals(land, Commands.land("id", 42).toString());
	
	// test glimpse

	String move_to = "{\"action\":\"move_to\",\"parameters\":{\"direction\":\"E\"}}";
	assertEquals(move_to, Commands.moveTo(Heading.E).toString());
	
	String exploit="{\"action\":\"exploit\",\"parameters\":{\"resource\":\"FUR\"}}";
	assertEquals(exploit, Commands.exploit(ResourceType.FUR).toString());

	String explore = "{\"action\":\"explore\"}";
	assertEquals(explore, Commands.explore().toString());
	
    }
}
