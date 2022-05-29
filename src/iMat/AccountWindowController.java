// Hör ihop med AccountWindow.fxml
package iMat;

import java.io.IOException;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

public class AccountWindowController extends AnchorPane {

    private ToggleGroup paymentToggleGroup;
    private final ClassLoader classLoader = getClass().getClassLoader();
    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final Customer customer = dataHandler.getCustomer();

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
    @FXML
    private ImageView closeImageView;
    @FXML
    private FlowPane orderHistoryFlowPane;
    @FXML
    private Label savedLabel;
    @FXML
    private Label resetLabel;
    @FXML
    private RadioButton checkBoxFaktura;
    @FXML
    private VBox kortBox;
    @FXML
    private ComboBox cardComboBox;
    @FXML
    private TextField cardNumberTextField;
    @FXML
    private RadioButton checkBoxKort;

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
        resetText();
        addOrderHistory();
        setupRadioButtons();
        setupCardBox();
        setRadioButtons();


    }

    /*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void closeButtonMouseEntered() {
        closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close_white.png"))));
    }

    @FXML
    private void closeButtonMouseExited() {
        closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close_hover.png"))));
    }

    /*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void closeAccountWindow() {
        toBack();
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

    private void setupCardBox() {
        cardComboBox.getItems().addAll(CardType.MASTER_CARD.toString(), CardType.VISA.toString());
        cardNumberTextField.focusedProperty().addListener(new TextFieldListener(cardNumberTextField));

        if (dataHandler.getCreditCard().getCardType().equals("Faktura")) {
            checkBoxFaktura.setSelected(true);
            checkBoxKort.setSelected(false);
            kortBox.setVisible(false);
        } else
            cardComboBox.getSelectionModel().select(dataHandler.getCreditCard().getCardType());

        cardComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                dataHandler.getCreditCard().setCardType(newValue);
                saveFields();
            }
        });

        if (dataHandler.getCreditCard().equals("")) {
            checkBoxFaktura.setSelected(true);
            checkBoxKort.setSelected(false);
        } else
            checkBoxKort.setSelected(true);
    }

    private void setupRadioButtons() {
        paymentToggleGroup = new ToggleGroup();
        checkBoxFaktura.setToggleGroup(paymentToggleGroup);
        checkBoxKort.setToggleGroup(paymentToggleGroup);


        paymentToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (paymentToggleGroup.getSelectedToggle() != null && paymentToggleGroup.getSelectedToggle().equals(checkBoxFaktura)) {
                    RadioButton selected = (RadioButton) paymentToggleGroup.getSelectedToggle();
                    dataHandler.getCreditCard().setCardType("Faktura");
                }
                toggleBehavior();
                saveFields();
            }
        });
    }

    private void setRadioButtons(){
        if (dataHandler.getCreditCard().getCardType().equals("Faktura")) {
            checkBoxKort.setSelected(false);
            checkBoxFaktura.setSelected(true);
        } else if (dataHandler.getCreditCard().getCardType().equals("")) {
            checkBoxKort.setSelected(false);
            checkBoxFaktura.setSelected(false);
        } else {
            checkBoxKort.setSelected(true);
            checkBoxFaktura.setSelected(false);
        }
    }

    private void toggleBehavior() {
        if (paymentToggleGroup.getSelectedToggle() != null) {
            kortBox.setVisible(paymentToggleGroup.getSelectedToggle().equals(checkBoxKort));
        }
    }

    private void addOrderHistory() {
        orderHistoryFlowPane.getChildren().clear();
        for (Order order : dataHandler.getOrders()) {
            orderHistoryFlowPane.getChildren().add(new OrderHistoryItemController(order));
        }
    }

    private void showSavedText() {
        savedLabel.setVisible(true);
    }

    private void showResetText() {
        resetLabel.setVisible(true);
    }

    private void resetText() {
        savedLabel.setVisible(false);
        resetLabel.setVisible(false);
    }

    @FXML
    private void resetAccount() {
        dataHandler.getOrders().clear();
        customer.setPhoneNumber("");
        customer.setAddress("");
        customer.setPostCode("");
        customer.setPostAddress("");
        customer.setFirstName("");
        customer.setLastName("");
        showResetText();
        orderHistoryFlowPane.getChildren().clear();
        fillTextFields();

        dataHandler.getCreditCard().setCardType("");
        dataHandler.getCreditCard().setCardNumber("");
        dataHandler.getCreditCard().setHoldersName("");
        dataHandler.getCreditCard().setValidMonth(2);
        dataHandler.getCreditCard().setValidYear(2011);
        dataHandler.getCreditCard().setVerificationCode(0);

        checkBoxKort.setSelected(false);
        checkBoxFaktura.setSelected(false);
        cardComboBox.getSelectionModel().select("Välj korttyp");
        cardNumberTextField.setText("");
    }


    private void fillTextFields() {
        accountFNameTextField.setText(dataHandler.getCustomer().getFirstName());
        accountLNameTextField.setText(dataHandler.getCustomer().getLastName());
        accountAddressTextField.setText(dataHandler.getCustomer().getAddress());
        accountPAddressTextField.setText(dataHandler.getCustomer().getPostAddress());
        accountPNumberTextField.setText(dataHandler.getCustomer().getPostCode());
        accountTNumberTextField.setText(dataHandler.getCustomer().getPhoneNumber());

        cardNumberTextField.setText(dataHandler.getCreditCard().getCardNumber());
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

        if (checkBoxFaktura.isSelected())
            dataHandler.getCreditCard().setCardType("Faktura");
        else {
            dataHandler.getCreditCard().setCardNumber(cardNumberTextField.getText());
        }

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
                resetText();
            }
        }
    }
}