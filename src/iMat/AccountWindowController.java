// Hör ihop med AccountWindow.fxml
package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class AccountWindowController extends AnchorPane {

    @FXML private AnchorPane accountWindowAnchorPane;
    @FXML private Button accountWindowCloseButton;

    public AccountWindowController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AccountWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void closeAccountWindow() {
        this.accountWindowAnchorPane.toBack();
    }
}
