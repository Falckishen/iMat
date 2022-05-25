// Hör ihop med CheckoutItem.fxml
package iMat;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartStepOneController extends AnchorPane {

        private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
        private final ShoppingCart cart = dataHandler.getShoppingCart();
        private final MainPageController mainPageController;

        private final ShoppingItem shoppingItem;

        @FXML private ImageView image;
        @FXML private Text nameofProduct;
        @FXML private Text priceofProducts;
        @FXML private Text numberofProducts;
        @FXML private ImageView plus;
        @FXML private ImageView minus;

        public CartStepOneController(ShoppingItem shoppingItem, MainPageController mainPageController) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/CheckoutItem.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            this.mainPageController = mainPageController;
            this.shoppingItem = shoppingItem;
            image.setImage(dataHandler.getFXImage(shoppingItem.getProduct()));
            nameofProduct.setText(shoppingItem.getProduct().getName());
            priceofProducts.setText(String.format("Pris: %.2f", shoppingItem.getTotal()));
            numberofProducts.setText(String.valueOf(shoppingItem.getAmount()));
        }

/*-------------------------------------------------------------------------------------------------------------------*/

        @FXML
        private void addToCart(){
            IMat.addOneToCart(shoppingItem.getProduct());
        }



        @FXML
        private void removeFromCart(){
            IMat.removeOneFromCart(shoppingItem.getProduct());
        }

        /*
        @FXML
        private void closeButtonMousePressed() {
            addedProduct.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/plus.png"))));
        }
        */
}