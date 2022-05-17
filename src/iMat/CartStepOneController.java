package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class CartStepOneController extends AnchorPane {


        private final ClassLoader classLoader = getClass().getClassLoader();
        private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
        private final ShoppingCart cart = dataHandler.getShoppingCart();
        private final MainPageController mainPageController;
        private final ShoppingItem item;
        @FXML
        ImageView image;
        private Image bild;
        @FXML Text nameofProduct;
        @FXML Text priceofProducts;
        @FXML Text numberofProducts;
        @FXML ImageView plus;
        @FXML ImageView minus;


        public CartStepOneController(ShoppingItem item, MainPageController mainPageController) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fmxl/kassaitem.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            this.mainPageController = mainPageController;
            this.item = item;
            this.bild = dataHandler.getFXImage(item.getProduct());
            this.image.setImage(bild);
            this.nameofProduct.setText(item.getProduct().getName());
            this.priceofProducts.setText(String.valueOf((item.getProduct().getPrice())*item.getAmount()));
            this.numberofProducts.setText(String.valueOf(item.getAmount()));

        }

        @FXML public void addtoCart(){
            IMat.addOneToCart(item.getProduct());
        }

        @FXML public void removefromCart(){
            IMat.removeOneFromCart(item.getProduct());
        }

        //@FXML
       // public void closeButtonMousePressed() {
       //     this.addedProduct.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/plus.png"))));
      //  }
}