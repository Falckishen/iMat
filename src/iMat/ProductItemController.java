// Hör ihop med ProductItem.fxml
package iMat;

import java.io.IOException;
import java.util.Objects;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class ProductItemController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ClassLoader classLoader = getClass().getClassLoader();
    private final MainPageController mainPageController;
    private final Product product;

    @FXML private ImageView image;
    @FXML private Button favoriteButton;
    @FXML private ImageView favoriteImage;
    @FXML private Text nameOfProduct;
    @FXML private Text priceOfItem;
    @FXML private Label numberOfProductsText;
    @FXML private Label unitLabel;
    @FXML private StackPane ekologiskPane;

    public ProductItemController(Product product, MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/ProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
        this.mainPageController = mainPageController;
        populateProductItem(product);
    }

    /*-------------------------------------------------------------------------------------------------------------------*/

    public void updateNumberOfProductsText() {
        numberOfProductsText.setText(String.valueOf(IMat.getNumberOfAProductInCart(product)));
    }

    public void updateFavoriteButton() {
        if (dataHandler.isFavorite(product)) {
            favoriteImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/starSelected.png"))));
        }
        else {
            favoriteImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/starUnselected.png"))));
        }
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void openDetailView() {
        mainPageController.openProductDetailView(product);
    }

    @FXML
    private void plusButton(Event event){
        event.consume();
        IMat.addOneToCart(product);
    }

    @FXML
    private void removeFromCart(Event event){
        event.consume();
        IMat.removeOneFromCart(product);
    }

    @FXML
    private void favoriteButtonClicked() {
        if (dataHandler.isFavorite(product)) {
            dataHandler.removeFavorite(product);
        }
        else {
            dataHandler.addFavorite(product);
        }
        updateFavoriteButton();
    }

    private void populateProductItem(Product product) {
        image.setImage(dataHandler.getFXImage(product));
        nameOfProduct.setText(product.getName());
        priceOfItem.setText(String.format("%.2f", product.getPrice()));
        unitLabel.setText(product.getUnit());
        numberOfProductsText.setText(String.valueOf(IMat.getNumberOfAProductInCart(product)));
        if(!product.isEcological())
            ekologiskPane.setVisible(false);
        updateFavoriteButton();
    }
}