package fr.unice.polytech.qgl.qaf.util;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class HeadingTest{
	private Heading headingN;
	private Heading headingE;
	private Heading headingS;
	private Heading headingW;
	
	@Before
	public void defineContext(){
		headingN = Heading.N;
		headingE = Heading.E;
		headingS = Heading.S;
		headingW = Heading.W;

	}


	@Test
	public void testGetRotateCw(){
	    assertEquals(Heading.E, headingN.getRotateCw());
	    assertEquals(Heading.S, headingE.getRotateCw());
	    assertEquals(Heading.W, headingS.getRotateCw());
	    assertEquals(Heading.N, headingW.getRotateCw());
	}

	@Test
	public void testGetRotateCcw(){
	    assertEquals(Heading.W, headingN.getRotateCcw());
	    assertEquals(Heading.N, headingE.getRotateCcw());
	    assertEquals(Heading.E, headingS.getRotateCcw());
	    assertEquals(Heading.S, headingW.getRotateCcw());
	}
}
