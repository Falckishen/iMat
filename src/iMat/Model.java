package iMat;

import se.chalmers.cse.dat216.project.IMatDataHandler;

public class Model {

    private static Model instance = null;
    private IMatDataHandler iMatDataHandler;

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
            instance.init();
        }
        return instance;
    }

    private void init() {
        iMatDataHandler = IMatDataHandler.getInstance();
    }

    public void shutDown() {
        iMatDataHandler.shutDown();
    }
}
