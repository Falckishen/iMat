// Hör ihop med AccountWindow.fxml
package iMat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;


public class AccountWindowController extends AnchorPane {

    @FXML private AnchorPane accountWindowAnchorPane;
    @FXML private Button accountWindowCloseButton;

    @FXML
    public void closeAccountWindow() {
        accountWindowAnchorPane.toBack();
    }
}