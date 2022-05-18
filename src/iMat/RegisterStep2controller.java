package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

import java.io.IOException;

public class RegisterStep2controller extends AnchorPane implements ShoppingCartListener {

    private final ShoppingCart cart = IMat.getShoppingCart();
    private final MainPageController mainPageController;


    public RegisterStep2controller(MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/RegisterPageStep2.fxml"));
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

    @FXML
    private void openPurchaseView() throws IOException{
        this.mainPageController.openRegisterView();
    }

    @FXML
    private void openFinalStep() throws IOException{
        this.mainPageController.openRegisterfinalstep();
    }











    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {

    }
}
