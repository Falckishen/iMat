package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterController extends AnchorPane implements ShoppingCartListener {
    private final ClassLoader classLoader = getClass().getClassLoader();
    private final ShoppingCart cart = IMat.getShoppingCart();

    private final MainPageController mainPageController;

    @FXML Button toPurchaseButton;
    @FXML Button toBackButton;
    @FXML
    FlowPane registerGridPane;

    public RegisterController(MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fmxl/RegisterPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.mainPageController = mainPageController;
    }

    @FXML public void openPurchaseView() throws IOException{
        mainPageController.openPurchaseView();
    }

    @FXML public void openMainPageView() throws IOException{
        mainPageController.openMainPageView();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        registerGridPane.getChildren().clear();
        //totalPrice.setText(String.valueOf(dataHandler.getShoppingCart().getTotal()));
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) cart.getItems();
        for (ShoppingItem item : list) {
            if (item.getAmount() > 0) {
                //CartItemController cartitem = new CartItemController(item);
                CartStepOneController cartFlow = new CartStepOneController(item);
                registerGridPane.getChildren().add(cartFlow);
                //currentpriceinCartOne.setText(String.valueOf(dataHandler.getShoppingCart().getTotal()));
            }
        }
    }
}
