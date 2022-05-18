// Hör ihop med ProductItem.fxml
package iMat;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class ProductItemController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final MainPageController mainPageController;

    private final Product product;

    @FXML ImageView image;
    @FXML ImageView favoriteUnselected;
    @FXML ImageView inkopslistaUnselected;
    @FXML Text nameofProduct;
    @FXML Text priceofItem;
    @FXML Text numberOfProductsText;
    @FXML ImageView addtocartButton;
    @FXML Button addtocart;

    public ProductItemController(Product product, MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fmxl/ProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
        this.mainPageController = mainPageController;
        this.image.setImage(dataHandler.getFXImage(product));
        this.nameofProduct.setText(product.getName());
        this.priceofItem.setText(String.valueOf(product.getPrice()));
        this.numberOfProductsText.setText(String.valueOf(IMat.getNumberOfAProductInCart(this.product)));
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void plusButton(Event event){
        event.consume();
        IMat.addOneToCart(this.product);
    }

    @FXML
    private void removeFromCart(Event event){
        event.consume();
        IMat.removeOneFromCart(this.product);
    }

    @FXML
    private void openDetailView() {
        this.mainPageController.openProductDetailView(this.product);
    }

    private void updateNumberOfProductsText() {

    }
}