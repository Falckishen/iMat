/* Hör ihop med ProductItem.fxml */
package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class AccountWindowController extends AnchorPane {

    @FXML private AnchorPane accountWindowAnchorPane;
    @FXML private Button accountWindowCloseButton;

    public AccountWindowController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AccountWindow.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    public void openAccountWindow(){
        accountWindowAnchorPane.toFront();
    }

    @FXML
    public void closeAccountWindow() {
        accountWindowAnchorPane.toBack();
    }
}