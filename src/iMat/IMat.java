package iMat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import java.util.Objects;

public class IMat extends Application {

    private static final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("iMatMainPage.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                iMatDataHandler.shutDown();
            }
        }));
    }

    public IMatDataHandler getIMatDataHandler() {
        return iMatDataHandler;
    }
}
