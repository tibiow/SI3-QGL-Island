package fr.unice.polytech.qgl.qaf.model;

import fr.unice.polytech.qgl.qaf.model.Drone;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import fr.unice.polytech.qgl.qaf.json.Commands;
import fr.unice.polytech.qgl.qaf.util.Heading;
import org.json.*;

public class DroneTest {

    private Drone drone;

  @Before
  public void defineContext() {
    Heading initialHeading = Heading.E;
    Map map = new Map(initialHeading);
    this.drone = new Drone(initialHeading, map);
    drone.createAerialMap(5,2);
    drone.setDimX(5);
    drone.setDimY(2);
  }

  /**
   * To test the construtor
   */
  @Test
  public void testConstructor() {
    assertEquals(drone.getInitialHeading(), Heading.E);
    assertEquals(drone.getCurrentHeading(), Heading.E);
    assertEquals(drone.getMovesRemaining(), 0);
    assertEquals(drone.getDimX(), 5);
    assertEquals(drone.getDimY(), 2);
  }

  /**
   * To test the method echo()
   */
  @Test
  public void testEcho() {
    assertEquals(drone.echo(Heading.E).toString(), Commands.echo(Heading.E).toString());
  }

  /**
   * To test the method scan()
   */
  @Test
  public void testScan() {
    assertEquals(drone.scan().toString(), Commands.scan().toString());
    assertEquals(drone.getAerialMap().getSquare(this.drone.getPosition().getX(), this.drone.getPosition().getY()).getNbrScan(), 1);
  }

  /**
   * To test the method stop()
   */
  @Test
  public void testStop() {
    assertEquals(drone.stop().toString(), Commands.stop().toString());
  }

  @Test
  public void testMoveForward(){
    JSONObject json = new JSONObject();
    
    json = drone.moveForward();
    assertEquals(json.toString(), Commands.fly().toString());
    assertEquals(drone.getPosition().getY(), 1);

    json = drone.moveForward();
    assertEquals(json.toString(), Commands.fly().toString());
    assertEquals(drone.getPosition().getY(), 2);
    assertEquals(drone.getDimY(), 3);

    drone.setCurrentHeading(Heading.S);
    json = drone.moveForward();
    assertEquals(json.toString(), Commands.fly().toString());
    assertEquals(drone.getPosition().getX(), 1);

    drone.setCurrentHeading(Heading.W);
    json = drone.moveForward();
    assertEquals(json.toString(), Commands.fly().toString());
    assertEquals(drone.getPosition().getY(), 1);

    drone.setCurrentHeading(Heading.N);
    json = drone.moveForward();
    assertEquals(json.toString(), Commands.fly().toString());
    assertEquals(drone.getPosition().getX(), 0);
  }

  @Test
  public void testTurnDrone(){
    JSONObject json = new JSONObject();

    json = drone.turnDroneRight();
    assertEquals(json.toString(), Commands.heading(Heading.S).toString());
    assertEquals(drone.getPosition().getY(), 1);
    assertEquals(drone.getPosition().getX(), 1);

    json = drone.turnDroneLeft();
    assertEquals(json.toString(), Commands.heading(Heading.E).toString());
    assertEquals(drone.getPosition().getY(), 2);
    assertEquals(drone.getPosition().getX(), 2);
    assertEquals(drone.getDimY(), 3);

    json = drone.turnDroneLeft();
    assertEquals(json.toString(), Commands.heading(Heading.N).toString());
    assertEquals(drone.getPosition().getY(), 3);
    assertEquals(drone.getPosition().getX(), 1);
    assertEquals(drone.getDimY(), 4);

    json = drone.turnDroneLeft();
    assertEquals(json.toString(), Commands.heading(Heading.W).toString());
    assertEquals(drone.getPosition().getY(),2);
    assertEquals(drone.getPosition().getX(),0);

    json = drone.turnDroneLeft();
    assertEquals(json.toString(), Commands.heading(Heading.S).toString());
    assertEquals(drone.getPosition().getY(),1);
    assertEquals(drone.getPosition().getX(),1);
  }

}
