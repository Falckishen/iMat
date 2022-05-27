// Hör ihop med ProductItem.fxml
package iMat;

import java.io.IOException;
import java.util.Objects;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartItemController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final MainPageController mainPageController;
    private final ClassLoader classLoader = getClass().getClassLoader();

    private final Product product;

    @FXML private ImageView image;
    @FXML private Label nameText;
    @FXML private Label priceText;
    @FXML private Label numberOfProducts;
    @FXML private ImageView closeImageView;

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
        product = item.getProduct();
        image.setImage(Util.getSquareImage(dataHandler.getFXImage(product)));
        nameText.setText(item.getProduct().getName());
        priceText.setText(String.format("%.2f", item.getTotal()));
        numberOfProducts.setText(String.valueOf(IMat.getNumberOfAProductInCart(product)));
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    /**/

    @FXML
    private void openDetailView() {
        mainPageController.openProductDetailView(product);
    }

    @FXML
    private void plusButtonClicked(Event event) {
        event.consume();
        IMat.addOneToCart(product);
    }

    @FXML
    private void minusButtonClicked(Event event) {
        event.consume();
        IMat.removeOneFromCart(product);
    }

    @FXML
    private void closeButtonMouseEnter() {
        closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close_white.png"))));
    }

    @FXML
    private void closeButtonMouseExited() {
        closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close_hover.png"))));
    }

    @FXML
    private void removeAllItems(Event event) {
        event.consume();
        IMat.writeInNumOfProductAmount(product, "0");
    }
}
