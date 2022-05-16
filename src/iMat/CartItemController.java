// Hör ihop med ProductItem.fxml
package iMat;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartItemController extends AnchorPane {

    private final ClassLoader classLoader = getClass().getClassLoader();
    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ShoppingItem item;
    @FXML ImageView image;
    @FXML ImageView favoriteUnselected;
    @FXML ImageView inkopslistaUnselected;
    @FXML Text priceofItem;
    @FXML Text nameofProducts;
    @FXML Text numberofProducts;
    @FXML Text priceofProducts;
    @FXML ImageView addProduct;
    @FXML ImageView removeProduct;
    @FXML ImageView addedProduct;


    private Image bild;

    public CartItemController(ShoppingItem item) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cartitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.item = item;
        this.bild = dataHandler.getFXImage(item.getProduct());
        this.image.setImage(bild);
        this.nameofProducts.setText(item.getProduct().getName());
        this.priceofProducts.setText(String.valueOf((item.getProduct().getPrice())*item.getAmount()));
        this.numberofProducts.setText(String.valueOf(item.getAmount()));

    }

    @FXML public void addtoCart(){
        IMat.addOneToCart(item.getProduct());
    }

    @FXML public void removefromCart(){
        IMat.removeOneFromCart(item.getProduct());
    }

    @FXML
    public void closeButtonMousePressed() {
        this.addedProduct.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/plus.png"))));
    }



}
