package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

import java.io.IOException;

public class RegisterFinalStepController extends AnchorPane implements ShoppingCartListener {


        private final ShoppingCart cart = IMat.getShoppingCart();
        private final MainPageController mainPageController;


        public RegisterFinalStepController(MainPageController mainPageController) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/RegisterPagefinalstep.fxml"));
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
    private void openMainPageView() throws IOException{
        this.mainPageController.openMainPageView();
    }







        @Override
        public void shoppingCartChanged(CartEvent cartEvent) {

        }


}
