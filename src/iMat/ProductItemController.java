// Hör ihop med ProductItem.fxml
package iMat;

import java.io.IOException;
import java.util.Objects;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class ProductItemController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final MainPageController mainPageController;
    private final ClassLoader classLoader = getClass().getClassLoader();

    private final Product product;

    @FXML private ImageView image;
    @FXML private Button favoriteButton;
    @FXML private ImageView favoriteImage;
    @FXML private Text nameOfProduct;
    @FXML private Text priceOfItem;
    @FXML private Text numberOfProductsText;

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
        System.out.println("Klickad");
        if (dataHandler.isFavorite(this.product)) {
            dataHandler.removeFavorite(this.product);
        }
        else {
            dataHandler.addFavorite(this.product);
        }
        updateFavoriteButton();
    }

    @FXML
    private void favoriteButtonMouseEntered() {
        this.favoriteButton.setStyle("-fx-background-color: #d0d0d0;");
    }

    @FXML
    private void favoriteButtonMousePressed() {
        this.favoriteButton.setStyle("-fx-background-color: #c0c0c0;");
    }

    @FXML
    private void favoriteButtonMouseExitedOrReleased() {
        this.favoriteButton.setStyle("-fx-background-color: #e0e0e0;");
    }

    private void populateProductItem(Product product) {
        this.image.setImage(dataHandler.getFXImage(product));
        this.nameOfProduct.setText(product.getName());
        this.priceOfItem.setText(String.format("Pris: %.2f %s", this.product.getPrice(), this.product.getUnit()));
        this.numberOfProductsText.setText(String.valueOf(IMat.getNumberOfAProductInCart(this.product)));
        updateFavoriteButton();
    }
}