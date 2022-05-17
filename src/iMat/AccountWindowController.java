// Hör ihop med AccountWindow.fxml
package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class AccountWindowController extends AnchorPane {

    @FXML private AnchorPane accountWindowAnchorPane;
    @FXML private Button accountWindowCloseButton;
    @FXML private TextField accountFNameTextField;
    @FXML private TextField accountLNameTextField;
    @FXML private TextField accountAddressTextField;
    @FXML private TextField accountSSNumberTextField;
    @FXML private TextField accountPNumberTextField;
    @FXML private TextField accountTNumberTextField;

    private MainPageController mainPageController;
    private iMatAccount account;

    public AccountWindowController(MainPageController mainPageController, iMatAccount account) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fmxl/AccountWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.mainPageController = mainPageController;
        this.account = account;
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void closeAccountWindow() {
        this.accountWindowAnchorPane.toBack();
    }

    @FXML
    public void saveFName() {
        mainPageController.changeAccount(account.changeFirstName(accountFNameTextField.getText()));
    }


    @FXML
    public void fillTextFields() {
        accountFNameTextField.setText(account.getFirstName());
    }


}