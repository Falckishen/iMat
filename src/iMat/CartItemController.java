// Hör ihop med ProductItem.fxml
package iMat;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartItemController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();

    private final Product product;
    private double price;

    @FXML private ImageView image;
    @FXML private Text nameText;
    @FXML private Text priceText;
    @FXML private Text numberOfProducts;

    public CartItemController(ShoppingItem item) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/cartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = item.getProduct();
        this.price = this.product.getPrice()*item.getAmount();
        this.image.setImage(dataHandler.getFXImage(this.product));
        this.nameText.setText(item.getProduct().getName());
        this.priceText.setText(String.format("Pris totalt: %.2f kr", this.price));
        this.numberOfProducts.setText(String.valueOf(IMat.getNumberOfAProductInCart(this.product)));
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void minusButtonClicked(){
        IMat.removeOneFromCart(this.product);
    }

    @FXML
    private void plusButtonClicked(){
        IMat.addOneToCart(this.product);
    }
}