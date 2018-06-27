package fr.unice.polytech.qgl.qaf.util;

import java.util.logging.*;
import java.io.IOException;


/**
 * An enum that can takes four cardinal directions for the heading
 * SI3 - 2015-2016
 * @author  Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week47
 * @since 28/11/2015
 */
public enum Heading {
  N, E, S, W;

  private static final Heading[] headings = values();
  private Logger logger;
  
  Heading() {
      		logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.Heading");
		try {
		    FileHandler fh = new FileHandler("log/HeadingTestLog.log");
		    fh.setFormatter(new SimpleFormatter());
		    logger.addHandler(fh);
		} catch (IOException e) { }

  }
  /**
   * Get to clockwise rotation of the current heading
   */
  public Heading getRotateCw() {
      return headings[(ordinal() + 1) % 4];
  }

  /**
   * Get to counter clockwise rotation of the current heading
   */
  public Heading getRotateCcw() {
      return headings[Math.floorMod((ordinal() - 1), 4)];
  }
}
