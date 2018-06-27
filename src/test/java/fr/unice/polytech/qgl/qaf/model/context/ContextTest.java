package fr.unice.polytech.qgl.qaf.model.context;

import fr.unice.polytech.qgl.qaf.model.context.Context;
import fr.unice.polytech.qgl.qaf.model.context.Contract;
import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.junit.Test;
import org.junit.Before;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

import fr.unice.polytech.qgl.qaf.util.*;

import static org.junit.Assert.*;

public class ContextTest {
    
    private JSONObject contextJSON;
    private JSONObject bigContextJSON;
    private JSONObject badContextJSON;

    @Before
    public void defineContext() {
        contextJSON = new JSONObject();
        contextJSON.put("men", "10");
        contextJSON.put("budget", "8000");
        JSONArray contracts = new JSONArray();
        JSONObject c1 = new JSONObject();
        c1.put("amount", "10");
        c1.put("resource", "GLASS");
        contracts.put(0, c1);
        JSONObject c2 = new JSONObject();
        c2.put("amount", "20");
        c2.put("resource", "QUARTZ");
        contracts.put(1, c2);
        contextJSON.put("contracts", contracts.toString());
        contextJSON.put("heading", "W");

        bigContextJSON = new JSONObject();
        bigContextJSON.put("men", "10");
        bigContextJSON.put("budget", "8000");
        JSONArray bigContracts = new JSONArray();
        bigContracts.put(0, c1);
        bigContracts.put(1, c2);
        JSONObject c3 = new JSONObject();
        c3.put("amount", "20");
        c3.put("resource", "FISH");
        bigContracts.put(2, c3);
        JSONObject c4 = new JSONObject();
        c4.put("amount", "20");
        c4.put("resource", "WOOD");
        bigContracts.put(3, c4);
        JSONObject c5 = new JSONObject();
        c5.put("amount", "20");
        c5.put("resource", "RUM");
        bigContracts.put(4, c5);
        bigContextJSON.put("contracts", bigContracts.toString());
        bigContextJSON.put("heading", "W");

        badContextJSON = new JSONObject();
        badContextJSON.put("mens", "10");
        badContextJSON.put("budget", "5000");
        badContextJSON.put("heading", "EAST");
    }

    @Test
    public void testParseObject() {
        Context c1 = new Context(contextJSON.toString());
        Context c2 = new Context(badContextJSON.toString());

        assertEquals(c1.getMen(), 10);
        assertEquals(c1.getBudget(), 8000);
        assertEquals(c1.getBudgetRemaining(), 8000);

        c1.setMen(2);
        c1.updateBudgetRemaining(5);
        assertEquals(c1.getMen(), 2);
        assertEquals(c1.getBudgetRemaining(), 7995);
        assertTrue(c1.enoughBudget());

        ArrayList<Contract> contracts = c1.getContracts();
        assertEquals(contracts.size(), 2);
        assertEquals(contracts.get(0).getAmount(), 10);
        assertEquals(contracts.get(0).getResourceType(), ResourceType.GLASS);
        assertEquals(contracts.get(1).getAmount(), 20);
        assertEquals(contracts.get(1).getResourceType(), ResourceType.QUARTZ);
        assertEquals(c1.getHeading(), Heading.W);

        assertEquals(c2.getMen(), 0);
        assertEquals(c2.getHeading(), null);
    }

    @Test
    public void testUpdateBudgetRemaining(){
        Context c1 = new Context(contextJSON.toString());
        Context c2 = new Context(badContextJSON.toString());

        c1.updateBudgetRemaining(50);
        assertEquals(c1.getBudgetRemaining(), 7950);
        c2.updateBudgetRemaining(-50);
        assertEquals(c2.getBudgetRemaining(), 5000);


    }
    

    @Test
    public void testEnoughBudget(){
        Context c1 = new Context(contextJSON.toString());
        Context c2 = new Context(badContextJSON.toString());

        c1.updateBudgetRemaining(5000);
        assertEquals(c1.enoughBudget(), true);
        c1.updateBudgetRemaining(2951);
        assertEquals(c1.enoughBudget(), false);
    }

    @Test
    public void testContractsSwitch() {
        Context c1 = new Context(contextJSON.toString());

        ArrayList<Integer> list = new ArrayList<>();
        list.add( (Integer) 3);
        list.add( (Integer) 6);

        assertEquals(c1.getContracts().get(0).getResourceType(), ResourceType.GLASS);
        assertEquals(c1.getContracts().get(1).getResourceType(), ResourceType.QUARTZ);
        assertEquals(list.get(0).intValue(), 3);
        assertEquals(list.get(1).intValue(), 6);
        
        c1.contractsSwitch(0, 1, list);
        assertEquals(c1.getContracts().get(1).getResourceType(), ResourceType.GLASS);
        assertEquals(c1.getContracts().get(0).getResourceType(), ResourceType.QUARTZ);
        assertEquals(list.get(1).intValue(), 3);
        assertEquals(list.get(0).intValue(), 6);
    }

    @Test
    public void testOrganizeContracts() {
        Context c1 = new Context(bigContextJSON.toString());
        ArrayList<Integer> list = new ArrayList<>();
        list.add( (Integer) 3);
        list.add( (Integer) 6);
        list.add( (Integer) 7);
        list.add( (Integer) 13);
        list.add( (Integer) 1);
        c1.organizeContracts(list);
        assertEquals(c1.getContracts().get(0).getResourceType(), ResourceType.WOOD);
        assertEquals(c1.getContracts().get(1).getResourceType(), ResourceType.FISH);
        assertEquals(c1.getContracts().get(2).getResourceType(), ResourceType.QUARTZ);
        assertEquals(c1.getContracts().get(3).getResourceType(), ResourceType.GLASS);
        assertEquals(c1.getContracts().get(4).getResourceType(), ResourceType.RUM);
    }

    @Test
    public void testSearchMin() {
        Context c1 = new Context(contextJSON.toString());
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add( (Integer) i);
        }
        assertEquals(c1.searchMin(list), 0);
        list.add((Integer) 0);
        assertEquals(c1.searchMin(list), 9);
    }

}
