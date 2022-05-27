// Hör ihop med RegisterPage.fxml
package iMat;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.*;

/**
 * Class for the register (the first step of the payment-wizard). Is a ShoppingCartListener and will update its cart
 * list whenever the cart updates.
 */
public class RegisterController extends AnchorPane implements ShoppingCartListener {

    private final ShoppingCart cart = IMat.getShoppingCart();
    private final MainPageController mainPageController;

    @FXML GridPane registerGridPane;
    @FXML Label totalPriceB;


    public RegisterController(MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/RegisterPage.fxml"));
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

    //TODO: Add cart value to the total.
    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        registerGridPane.getChildren().clear();
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) cart.getItems();
        int x = 0;
        int y = 0;
        for (ShoppingItem item : list) {
            if (item.getAmount() > 0) {
                CartItemController cartFlow = new CartItemController(item, mainPageController);
                registerGridPane.add(cartFlow, x, y);
                if(x == 0) {
                    x++;
                }
                else {
                    y++;
                    x=0;
                }
            }
        }
        totalPriceB.setText(cart.getTotal() +" kr");
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void openMainPageView() throws IOException{
        mainPageController.openMainPageView();
    }

    @FXML
    private void openRegisterStep2() throws IOException{
        mainPageController.openRegisterstep2View();
    }
}