package iMat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

import java.io.IOException;

public class RegisterStep2controller extends AnchorPane implements ShoppingCartListener {

    private final ShoppingCart cart = IMat.getShoppingCart();
    private final MainPageController mainPageController;
    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();

    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private TextField adress;
    @FXML
    private TextField postalcode;
    @FXML
    private TextField phonenumber;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField postAdress;
    @FXML
    private Label totalPriceCart2;
    @FXML
    private ComboBox combobox;
    @FXML
    RadioButton checkboxfaktura;

    @FXML
    RadioButton checkboxkort;

    @FXML
    VBox kortbox;

    ToggleGroup paymentToggleGroup;


    public RegisterStep2controller(MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/RegisterPageStep2.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        cart.addShoppingCartListener(this);
        this.mainPageController = mainPageController;

        addListeners();

        fName.setText(dataHandler.getCustomer().getFirstName());
        lName.setText(dataHandler.getCustomer().getLastName());
        adress.setText(dataHandler.getCustomer().getAddress());
        postAdress.setText(dataHandler.getCustomer().getPostAddress());
        postalcode.setText(dataHandler.getCustomer().getPostCode());
        phonenumber.setText(dataHandler.getCustomer().getPhoneNumber());
        totalPriceCart2.setText(String.valueOf(dataHandler.getShoppingCart().getTotal() + " kr"));
        cardNumber.setText(dataHandler.getCreditCard().getCardNumber());
        combobox.getItems().addAll(CardType.MASTER_CARD.toString(), CardType.VISA.toString());
        combobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                dataHandler.getCreditCard().setCardType(newValue);
            }
        });

        checkboxkort.setSelected(true);
        setupRadioButtons();

    }

    private void addListeners() {
        fName.focusedProperty().addListener(new TextFieldListener(fName));
        lName.focusedProperty().addListener(new TextFieldListener(lName));
        adress.focusedProperty().addListener(new TextFieldListener(adress));
        postAdress.focusedProperty().addListener(new TextFieldListener(postAdress));
        postalcode.focusedProperty().addListener(new TextFieldListener(postalcode));
        phonenumber.focusedProperty().addListener(new TextFieldListener(phonenumber));
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
            }
        }
    }

    private void saveFields() {
        dataHandler.getCustomer().setFirstName(fName.getText());
        dataHandler.getCustomer().setLastName(lName.getText());
        dataHandler.getCustomer().setAddress(adress.getText());
        dataHandler.getCustomer().setPostAddress(postAdress.getText());
        dataHandler.getCustomer().setPostCode(postalcode.getText());
        dataHandler.getCustomer().setPhoneNumber(phonenumber.getText());

        dataHandler.getCreditCard().setCardNumber(cardNumber.getText());
    }

    @FXML
    private void updateCostumer() throws IOException {
        fName.setText(dataHandler.getCustomer().getFirstName());
        lName.setText(dataHandler.getCustomer().getLastName());
        adress.setText(dataHandler.getCustomer().getAddress());
        postalcode.setText(dataHandler.getCustomer().getPostCode());
        phonenumber.setText(dataHandler.getCustomer().getPhoneNumber());
        totalPriceCart2.setText(dataHandler.getShoppingCart().getTotal() + " kr");
        cardNumber.setText(dataHandler.getCreditCard().getCardNumber());
        combobox.getSelectionModel().select(dataHandler.getCreditCard().getCardType());
    }

    @FXML
    private void openPurchaseView() throws IOException {
        mainPageController.openRegisterView();
    }

    @FXML
    private void openFinalStep() throws IOException {
        registerBuy();
        saveAccount();
        mainPageController.openRegisterfinalstep();
    }

    private void saveAccount() {
        dataHandler.getCustomer().setFirstName(fName.getText());
        dataHandler.getCustomer().setLastName(lName.getText());
        dataHandler.getCustomer().setAddress(postAdress.getText());
        dataHandler.getCustomer().setPostAddress(adress.getText());
        dataHandler.getCustomer().setPostCode(postalcode.getText());
        dataHandler.getCustomer().setPhoneNumber(phonenumber.getText());

        if (paymentToggleGroup.getSelectedToggle().equals(checkboxkort)) {
            dataHandler.getCreditCard().setCardNumber(cardNumber.getText());
        } else
            dataHandler.getCreditCard().setCardNumber("");
    }

    @FXML
    private void registerBuy() {
        dataHandler.placeOrder(false);
    }

    @FXML
    private void openMainPageView() throws IOException {
        mainPageController.openMainPageView();
    }

    private void setupRadioButtons() {
        paymentToggleGroup = new ToggleGroup();
        checkboxfaktura.setToggleGroup(paymentToggleGroup);
        checkboxkort.setToggleGroup(paymentToggleGroup);
        paymentToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                toggleBehavior();
            }
        });
    }

    private void toggleBehavior() {
        if (paymentToggleGroup.getSelectedToggle() != null)
            kortbox.setVisible(paymentToggleGroup.getSelectedToggle().equals(checkboxkort));
        else
            kortbox.setVisible(false);
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {

    }
}
