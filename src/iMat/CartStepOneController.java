// Hör ihop med checkoutItem.fxml
package iMat;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartStepOneController extends AnchorPane {

        private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
        private final ShoppingCart cart = dataHandler.getShoppingCart();
        private final MainPageController mainPageController;

        private final ShoppingItem item;

        @FXML private ImageView image;
        @FXML private Text nameofProduct;
        @FXML private Text priceofProducts;
        @FXML private Text numberofProducts;
        @FXML private ImageView plus;
        @FXML private ImageView minus;

        public CartStepOneController(ShoppingItem item, MainPageController mainPageController) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fmxl/checkoutItem.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            this.mainPageController = mainPageController;
            this.item = item;
            this.image.setImage(dataHandler.getFXImage(item.getProduct()));
            this.nameofProduct.setText(item.getProduct().getName());
            this.priceofProducts.setText(String.valueOf((item.getProduct().getPrice())*item.getAmount()));
            this.numberofProducts.setText(String.valueOf(item.getAmount()));
        }

/*-------------------------------------------------------------------------------------------------------------------*/

        @FXML
        private void addToCart(){
            IMat.addOneToCart(item.getProduct());
        }

        @FXML
        private void removeFromCart(){
            IMat.removeOneFromCart(item.getProduct());
        }

        /*
        @FXML
        private void closeButtonMousePressed() {
            this.addedProduct.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/plus.png"))));
        }
        */
}