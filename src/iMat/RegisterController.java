// Hör ihop med RegisterPage.fxml
package iMat;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.*;

/**
 * Class for the register (the first step of the payment-wizard). Is a ShoppingCartListener and will update its cart
 * list whenever the cart updates.
 */
public class RegisterController extends AnchorPane implements ShoppingCartListener {

    private final ShoppingCart cart = IMat.getShoppingCart();
    private final MainPageController mainPageController;


    @FXML Button toPurchaseButton;
    @FXML Button toBackButton;
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
        this.cart.addShoppingCartListener(this);
        this.mainPageController = mainPageController;

    }

    //TODO: Add cart value to the total.
    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        this.registerGridPane.getChildren().clear();
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) cart.getItems();
        int x = 0;
        int y = 0;
        for (ShoppingItem item : list) {
            if (item.getAmount() > 0) {
                CartItemController cartFlow = new CartItemController(item, this.mainPageController);
                this.registerGridPane.add(cartFlow, x, y);
                if(x == 0) {
                    x++;
                }
                else {
                    y++;
                    x=0;
                }
            }
        }
        this.totalPriceB.setText(String.valueOf(cart.getTotal())+" kr");
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void openPurchaseView() throws IOException{
        this.mainPageController.openPurchaseView();
    }

    @FXML
    private void openMainPageView() throws IOException{
        this.mainPageController.openMainPageView();
    }

    @FXML
    private void openRegisterstep2() throws IOException{
        this.mainPageController.openRegisterstep2View();
    }


}