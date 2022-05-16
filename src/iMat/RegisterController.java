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

/**
 * Class for the register (the first step of the payment-wizard). Is a ShoppingCartListener and will update its cart
 * list whenever the cart updates.
 */
public class RegisterController extends AnchorPane implements ShoppingCartListener {
    private final ClassLoader classLoader = getClass().getClassLoader();
    private final ShoppingCart cart = IMat.getShoppingCart();

    private final MainPageController mainPageController;

    @FXML Button toPurchaseButton;
    @FXML Button toBackButton;
    @FXML GridPane registerGridPane;

    public RegisterController(MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fmxl/RegisterPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        cart.addShoppingCartListener(this);
        this.mainPageController = mainPageController;
    }

    @FXML public void openPurchaseView() throws IOException{
        mainPageController.openPurchaseView();
    }

    @FXML public void openMainPageView() throws IOException{
        mainPageController.openMainPageView();
    }

    //TODO: Add cart value to the total.
    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        registerGridPane.getChildren().clear();
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) cart.getItems();
        int x = 0;
        int y = 0;
        for (ShoppingItem item : list) {
            if (item.getAmount() > 0) {
                CartStepOneController cartFlow = new CartStepOneController(item, mainPageController);
                registerGridPane.add(cartFlow, x, y);
                if(x == 0)
                {
                    x++;
                } else
                {
                    y++;
                    x=0;
                }

            }
        }
    }
}
