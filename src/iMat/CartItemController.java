// Hör ihop med ProductItem.fxml
package iMat;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartItemController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final MainPageController mainPageController;
    private final Product product;

    @FXML private ImageView image;
    @FXML private Label nameText;
    @FXML private Label priceText;
    @FXML private Label numberOfProducts;

    public CartItemController(ShoppingItem item, MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/CartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainPageController = mainPageController;
        this.product = item.getProduct();
        this.image.setImage(dataHandler.getFXImage(this.product));
        this.nameText.setText(item.getProduct().getName());
        this.priceText.setText(String.format("%.2f", item.getTotal()));
        this.numberOfProducts.setText(String.valueOf(IMat.getNumberOfAProductInCart(this.product)));
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void openDetailView() {
        this.mainPageController.openProductDetailView(this.product);
    }

    @FXML
    private void plusButtonClicked(Event event) {
        event.consume();
        IMat.addOneToCart(this.product);
    }

    @FXML
    private void minusButtonClicked(Event event) {
        event.consume();
        IMat.removeOneFromCart(this.product);
    }
}