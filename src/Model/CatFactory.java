package Model;

import Exceptions.RestartException;

public abstract class CatFactory {
    private String breedID;

    public CatFactory(String breedID) {
        this.breedID = breedID;
    }

    public abstract Cat initializeCat() throws RestartException;

    protected String getBreedID() {
        return breedID;
    }
}
