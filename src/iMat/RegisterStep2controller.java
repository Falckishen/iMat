package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

import java.io.IOException;

public class RegisterStep2controller extends AnchorPane implements ShoppingCartListener {

    private final ShoppingCart cart = IMat.getShoppingCart();
    private final MainPageController mainPageController;
    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();

    @FXML private TextField fName;
    @FXML private TextField lName;
    @FXML private TextField pNumber;
    @FXML private TextField adress;
    @FXML private TextField postalcode;
    @FXML private TextField phonenumber;
    @FXML private Label totalPriceCart2;
    @FXML private ComboBox combobox;

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

        fName.setText(dataHandler.getCustomer().getFirstName());
        lName.setText(dataHandler.getCustomer().getLastName());
        pNumber.setText(dataHandler.getCustomer().getAddress());
        adress.setText(dataHandler.getCustomer().getAddress());
        postalcode.setText(dataHandler.getCustomer().getPostCode());
        phonenumber.setText(dataHandler.getCustomer().getPhoneNumber());
        totalPriceCart2.setText(String.valueOf(dataHandler.getShoppingCart().getTotal() + " kr"));
        combobox.getItems().addAll("Visa", "Mastercard");
    }


    @FXML
    private void updateCostumer() throws IOException {
        fName.setText(dataHandler.getCustomer().getFirstName());
        lName.setText(dataHandler.getCustomer().getLastName());
        pNumber.setText(dataHandler.getCustomer().getPhoneNumber());
        adress.setText(dataHandler.getCustomer().getAddress());
        postalcode.setText(dataHandler.getCustomer().getPostCode());
        phonenumber.setText(dataHandler.getCustomer().getPhoneNumber());
        totalPriceCart2.setText(dataHandler.getShoppingCart().getTotal() + " kr");
    }

    @FXML
    private void openPurchaseView() throws IOException {
        mainPageController.openRegisterView();
    }

    @FXML
    private void openFinalStep() throws IOException {
        registerBuy();
        mainPageController.openRegisterfinalstep();
    }

    @FXML
    private void registerBuy() {
        dataHandler.placeOrder(false);
    }

    @FXML
    private void openMainPageView() throws IOException {
        mainPageController.openMainPageView();
    }


    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {

    }
}
