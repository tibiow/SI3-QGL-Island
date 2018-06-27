package fr.unice.polytech.qgl.qaf.model;

import fr.unice.polytech.qgl.qaf.json.Commands;
import fr.unice.polytech.qgl.qaf.model.map.Map;
import fr.unice.polytech.qgl.qaf.model.map.MiniMap;
import fr.unice.polytech.qgl.qaf.model.map.Position;
import fr.unice.polytech.qgl.qaf.util.Heading;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Class that represents a drone
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week50
 * @since 13/11/2015 *
 */
public class Drone {
    private Heading initialHeading;
    private Heading currentHeading;
    private MiniMap aerialMap;
    private Map earthMap;
    private int dimX;
    private int dimY;
    private Position position;
    private Logger logger;

    /**
     * A counter of fly commands
     */
    private int movesRemaining;
    private boolean hasGroundAhead = false;
    private JSONObject command;

    public Drone(Heading initialHeading, Map map) {
        this.initialHeading = initialHeading;
        this.currentHeading = initialHeading;
        this.movesRemaining = 0;
        this.dimX = 0;
        this.dimY = 0;
        this.position = new Position(0, 0);
        this.initLogger();
        this.earthMap = map;
    }

    public Heading getInitialHeading() {
        return this.initialHeading;
    }

    public Heading getCurrentHeading() {
        return this.currentHeading;
    }

    public void setPosition(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void setCurrentHeading(Heading current){
        this.currentHeading = current;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setMovesRemaining(int moves) {
        this.movesRemaining = moves;
    }

    public int getMovesRemaining() {
        return this.movesRemaining;
    }

    public void setGroundAhead(boolean hasGroundAhead) {
        this.hasGroundAhead = hasGroundAhead;
    }

    public boolean hasGroundAhead() {
        return this.hasGroundAhead;
    }


    public JSONObject echo(Heading heading) {
        this.command = Commands.echo(heading);
        return Commands.echo(heading);
    }

    public JSONObject scan() {
        this.aerialMap.getSquare(this.position.getX(), this.position.getY()).incrementScanCounter();
        this.command = Commands.scan();
        return Commands.scan();
    }

    public JSONObject stop() {
        this.command = Commands.stop();
        return Commands.stop();
    }

    /**
     * Move the drone forward.
     * Update the drone coordinates and return the fly command.
     *
     * @return The fly command
     */
    public JSONObject moveForward() {
        if (this.currentHeading.equals(this.initialHeading)) {

            this.position.setY(this.position.getY() + 1);
            if (this.position.getY() >= this.aerialMap.getLimY()) {
                this.aerialMap.newLine(this.position.getY() - this.aerialMap.getLimY());
                this.dimY = this.position.getY() + 1;
            }
            if (this.position.getY() * 3 >= this.earthMap.getLimY()) {
                this.earthMap.newLine((3 * this.position.getY()) - this.earthMap.getLimY());
                this.dimY = this.position.getY() + 1;
            }
        } else if (this.currentHeading.getRotateCw().equals(this.initialHeading)) {
            this.position.setX(this.position.getX() - 1);
        } else if (this.currentHeading.getRotateCcw().equals(this.initialHeading)) {
            this.position.setX(this.position.getX() + 1);
        } else {
            this.position.setY(this.position.getY() - 1);
        }

        this.command = Commands.fly();
        return Commands.fly();
    }

    public JSONObject turnDroneRight() {
        return this.turnDrone(this.currentHeading.getRotateCw());
    }

    public JSONObject turnDroneLeft() {
        return this.turnDrone(this.currentHeading.getRotateCcw());
    }

    private JSONObject turnDrone(Heading direction) {

        // Compute the new coordinates according to the current and initial heading
        if (this.currentHeading.equals(this.initialHeading)) {
            this.position.setY(this.position.getY() + 1);
            if (this.position.getY() >= this.aerialMap.getLimY()) {
                this.aerialMap.newLine(this.position.getY() - this.aerialMap.getLimY());
                this.dimY = this.position.getY() + 1;
            }
            if (this.position.getY() * 3 >= this.earthMap.getLimY()) {
                this.earthMap.newLine((3 * this.position.getY()) - this.earthMap.getLimY());
                this.dimY = this.position.getY() + 1;
            }
        } else if (this.currentHeading.getRotateCw().equals(this.initialHeading)) {
            this.position.setX(this.position.getX() - 1);
        } else if (this.currentHeading.getRotateCcw().equals(this.initialHeading)) {
            this.position.setX(this.position.getX() + 1);
        } else {
            this.position.setY(this.position.getY() - 1);
        }

        if (direction.equals(this.initialHeading)) {
            this.position.setY(this.position.getY() + 1);
            if (this.position.getY() >= this.aerialMap.getLimY()) {
                this.aerialMap.newLine(this.position.getY() - this.aerialMap.getLimY());
                this.dimY = this.position.getY() + 1;
            }
            if (this.position.getY() * 3 >= this.earthMap.getLimY()) {
                this.earthMap.newLine((3 * this.position.getY()) - this.earthMap.getLimY());
                this.dimY = this.position.getY() + 1;
            }
        } else if (direction.getRotateCw().equals(this.initialHeading)) {
            this.position.setX(this.position.getX() - 1);
        } else if (direction.getRotateCcw().equals(this.initialHeading)) {
            this.position.setX(this.position.getX() + 1);
        } else {
            this.position.setY(this.position.getY() - 1);
        }

        this.currentHeading = direction;
        this.command = Commands.heading(direction);
        return Commands.heading(direction);
    }

    public JSONObject getCommand() {
        return this.command;
    }

    public void createAerialMap(int limX, int limY) {
        this.aerialMap = new MiniMap(limX, limY);
    }

    public MiniMap getAerialMap() {
        return this.aerialMap;
    }

    public Map getEarthMap() {
        return this.earthMap;
    }

    public int getDimX() {
        return this.dimX;
    }

    public int getDimY() {
        return this.dimY;
    }

    public void setDimX(int dimX) {
        this.dimX = dimX;
    }

    public void setDimY(int dimY) {
        this.dimY = dimY;
    }

    private void initLogger() {
        logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.strategy.Drone");
        try {
            FileHandler fh = new FileHandler("log/DroneLog.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
        }
    }
}
