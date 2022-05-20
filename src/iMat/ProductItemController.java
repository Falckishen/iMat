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
    @FXML private ImageView plusImage;
    @FXML private ImageView minusImage;
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
        this.numberOfProductsText.setText(String.valueOf(IMat.getNumberOfAProductInCart(this.product)));
    }

    public void updateFavoriteButton() {
        if (dataHandler.isFavorite(this.product)) {
            this.favoriteImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/starSelected.png"))));
        }
        else {
            this.favoriteImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/starUnselected.png"))));
        }
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void openDetailView() {
        this.mainPageController.openProductDetailView(this.product);
    }

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
    private void favoriteButtonClicked() {
        if (dataHandler.isFavorite(this.product)) {
            dataHandler.removeFavorite(this.product);
        }
        else {
            dataHandler.addFavorite(this.product);
        }
        updateFavoriteButton();
    }

    private void populateProductItem(Product product) {
        this.image.setImage(dataHandler.getFXImage(product));
        this.nameOfProduct.setText(product.getName());
        this.priceOfItem.setText(String.format("%.2f", this.product.getPrice()));
        this.unitLabel.setText(this.product.getUnit());
        this.numberOfProductsText.setText(String.valueOf(IMat.getNumberOfAProductInCart(this.product)));
        if(!this.product.isEcological())
            this.ekologiskPane.setVisible(false);
        updateFavoriteButton();
    }
}