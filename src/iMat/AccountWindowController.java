// Hör ihop med AccountWindow.fxml
package iMat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.Objects;

public class AccountWindowController extends AnchorPane {

    @FXML
    private AnchorPane accountWindowAnchorPane;
    @FXML
    private Button accountWindowCloseButton;
    @FXML
    private TextField accountFNameTextField;
    @FXML
    private TextField accountLNameTextField;
    @FXML
    private TextField accountAddressTextField;
    @FXML
    private TextField accountPAddressTextField;
    @FXML
    private TextField accountPNumberTextField;
    @FXML
    private TextField accountTNumberTextField;
    @FXML private ImageView closeImageView;
    @FXML
    private Label savedLabel;

    private final ClassLoader classLoader = getClass().getClassLoader();

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final Customer customer = dataHandler.getCustomer();

    public AccountWindowController(MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AccountWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        addListeners();
        fillTextFields();
        resetSavedText();
    }

    /*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void closeButtonMouseEntered() {
        this.closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close_white.png"))));
    }

    @FXML
    private void closeButtonMouseExited() {
        this.closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close_hover.png"))));
    }

    /*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void closeAccountWindow() {
        this.toBack();
    }

    @FXML
    private void mouseTrap(Event event) {
        event.consume();
    }

    @FXML
    private void saveButton() {
        saveFields();
        showSavedText();
    }

    private void showSavedText() {
        savedLabel.setVisible(true);
    }

    private void resetSavedText() {
        savedLabel.setVisible(false);
    }


    private void fillTextFields() {
        accountFNameTextField.setText(dataHandler.getCustomer().getFirstName());
        accountLNameTextField.setText(dataHandler.getCustomer().getLastName());
        accountAddressTextField.setText(dataHandler.getCustomer().getAddress());
        accountPAddressTextField.setText(dataHandler.getCustomer().getPostAddress());
        accountPNumberTextField.setText(dataHandler.getCustomer().getPostCode());
        accountTNumberTextField.setText(dataHandler.getCustomer().getPhoneNumber());
    }

    private void addListeners() {
        accountFNameTextField.focusedProperty().addListener(new TextFieldListener(accountFNameTextField));
        accountLNameTextField.focusedProperty().addListener(new TextFieldListener(accountLNameTextField));
        accountAddressTextField.focusedProperty().addListener(new TextFieldListener(accountAddressTextField));
        accountPAddressTextField.focusedProperty().addListener(new TextFieldListener(accountPAddressTextField));
        accountPNumberTextField.focusedProperty().addListener(new TextFieldListener(accountPNumberTextField));
        accountTNumberTextField.focusedProperty().addListener(new TextFieldListener(accountTNumberTextField));
    }

    private void saveFields() {
        customer.setFirstName(accountFNameTextField.getText());
        customer.setLastName(accountLNameTextField.getText());
        customer.setAddress(accountAddressTextField.getText());
        customer.setPostAddress(accountPAddressTextField.getText());
        customer.setPostCode(accountPNumberTextField.getText());
        customer.setPhoneNumber(accountTNumberTextField.getText());
    }

    private class TextFieldListener implements ChangeListener<Boolean> {

        private TextField textField;

        public TextFieldListener(TextField textField) {
            this.textField = textField;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (!newValue) {
                saveFields();
                resetSavedText();
            }
        }
    }
}