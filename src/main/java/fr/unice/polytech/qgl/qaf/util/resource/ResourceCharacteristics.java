package fr.unice.polytech.qgl.qaf.util.resource;

/**
 * ResourceCharacteristics Class for the Island Game
 * SI3 - 2015-2016
 *
 * @author Thibaut Gonnin, Axel Aiello, Basil Dalié, Antoine Steyer
 * @version week08
 * @since 25/02/2016
 **/
public class ResourceCharacteristics {
    private ResourceType name;
    private Amount amount;
    private DifficultyToExploit difficultyToExploit;

    public ResourceCharacteristics(ResourceType name, Amount amount, DifficultyToExploit difficultyToExploit) {
        this.name = name;
        this.amount = amount;
        this.difficultyToExploit = difficultyToExploit;
    }

    public ResourceType getName() {
        return name;
    }

    public Amount getAmount() {
        return amount;
    }

    public DifficultyToExploit getDifficultyToExploit() {
        return difficultyToExploit;
    }
}
