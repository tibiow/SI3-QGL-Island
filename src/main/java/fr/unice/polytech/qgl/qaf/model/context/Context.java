package fr.unice.polytech.qgl.qaf.model.context;

import org.json.*;

import java.util.ArrayList;
import java.util.logging.*;
import java.util.logging.Level;
import java.io.IOException;

import fr.unice.polytech.qgl.qaf.util.*;

/**
 * Context Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week47
 * @since 15/11/2015
 **/
public class Context {
    private ArrayList<Contract> contracts;
    private int men;
    private int budget;
    private int budgetRemaining;
    private Heading heading;
    private int numberOfContractsDone;
    private Logger logger;
    
    public Context(String context) {
	initLogger();

        contracts = new ArrayList<>();
        parseObject(new JSONObject(context));
	
        numberOfContractsDone = 0;
    }

    private void parseObject(JSONObject json) {
        try {
            men = json.getInt("men");
        } catch (JSONException e) {
            men = 0;
        }

        try {
            budget = json.getInt("budget");
            budgetRemaining = json.getInt("budget");
        } catch (JSONException e) {
            budget = 0;
            budgetRemaining = 0;
        }

        try {
            String headingStr = json.getString("heading");
            switch (headingStr) {
                case "N":
                    heading = Heading.N;
                    break;
                case "E":
                    heading = Heading.E;
                    break;
                case "S":
                    heading = Heading.S;
                    break;
                case "W":
                    heading = Heading.W;
                    break;
                default:
                    heading = null;
            }
        } catch (JSONException e) {
            heading = null;
        }

        try {
            JSONArray contractsJSON = new JSONArray(json.get("contracts").toString());
	    logger.info("k " + contractsJSON.length());
            for (int i = 0; i < contractsJSON.length(); ++i) {
                contracts.add(new Contract(contractsJSON.get(i).toString()));
            }
	    
        } catch (JSONException e) {
	    logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     *  Method to switch two contracts
     *  @param i : an int
     *  @param j : an int 
     *  @param list : an arrayList of integer
     */
    public void contractsSwitch(int i, int j, ArrayList<Integer> list) {
        Contract c;
        c = contracts.get(i);
        contracts.set(i, contracts.get(j));
        contracts.set(j, c);

        Integer n;
        n = list.get(i);
        list.set(i, list.get(j));
        list.set(j, n);
    }

    /**
     *  Method to ordonne list
     *  @param list : an arrayList
     */
    public void organizeContracts(ArrayList<Integer> list) {
        int n = list.size();
        int i;
        while (n > 1) {
            i = searchMin(list);
            contractsSwitch(i, n - 1, list);
            list.remove(n-1);
            n--;
        } 
    }

    /**
     *  Method to find the minimum of list
     *  @param list : an arrayList
     */
    public int searchMin(ArrayList<Integer> list) {
        int min = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(min) > list.get(i)) 
                min = i;
        }
        return min;
    }

    public int getMen() {
        return men;
    }

    public int getBudget() {
        return budget;
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public Heading getHeading() {
        return heading;
    }

    public void setMen(int men) {
        this.men = men;
    }

    public int getBudgetRemaining() {
        return budgetRemaining;
    }

    public int getNumberOfContractsDone(){
        return numberOfContractsDone;
    }

    public void updateBudgetRemaining(int costOfAction) {
        if (costOfAction > 0) {
            budgetRemaining -= costOfAction;
        }
    }

    public boolean enoughBudget() {
        return (budgetRemaining > 70);
    }

    public void setNumberOfContractsDone(int incr){
        numberOfContractsDone = incr;
    }

    private void initLogger() {
	logger = Logger.getLogger("fr.unice.polytech.qgl.qaf.model.context");
	try {
	    FileHandler fh = new FileHandler("log/ContextLog.log");
	    fh.setFormatter(new SimpleFormatter());
	    logger.addHandler(fh);
	} catch (IOException e) {
	}
    }
}

