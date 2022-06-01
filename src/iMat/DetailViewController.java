// Hör ihop med DetailView.fxml
package iMat;

import java.util.InputMismatchException;
import java.util.Objects;
import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class DetailViewController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final MainPageController mainPageController;

    private final ClassLoader classLoader = getClass().getClassLoader();
    private final Product product;

    @FXML private ImageView closeImageView;
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private Label isEcoLabel;
    @FXML private TextField numOfItems;
    @FXML private Button plusButton;
    @FXML private ImageView plusImage;
    @FXML private Button minusButton;
    @FXML private ImageView minusImage;
    @FXML private Button favoriteButton;
    @FXML private ImageView favoriteImage;
    @FXML private StackPane ekoStackPane;

    public DetailViewController(Product product, MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/DetailView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
        this.mainPageController = mainPageController;
        populateProductDetailView();
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void closeButtonMouseEnter() {
        closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close_white.png"))));
    }

    @FXML
    private void closeButtonMouseExited() {
        closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close_hover.png"))));
    }

    @FXML
    private void mouseTrap(Event event) {
        event.consume();
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void closeProductDetailView() {
        toBack();
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
        mainPageController.updateProductItemFavoriteButtons();
    }

    @FXML
    private void plusButtonClicked() {
        IMat.addOneToCart(product);
        numOfItems.setText(String.valueOf(IMat.getNumberOfAProductInCart(product)));
    }

    @FXML
    private void minusButtonClicked() {
        IMat.removeOneFromCart(product);
        numOfItems.setText(String.valueOf(IMat.getNumberOfAProductInCart(product)));
    }

    @FXML
    private void numOfItemsWriteIn() throws InputMismatchException {
        String strAmount = numOfItems.getText();
        IMat.writeInNumOfProductAmount(product, strAmount);
        numOfItems.setText(String.valueOf(IMat.getNumberOfAProductInCart(product)));
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    private void populateProductDetailView() {
        productImage.setImage(dataHandler.getFXImage(product));
        productNameLabel.setText(product.getName());
        priceLabel.setText(String.format("Pris: %.2f %s", product.getPrice(), product.getUnit()));
        if (product.isEcological()) {
            isEcoLabel.setText("Produkten är ekologisk");
            ekoStackPane.setVisible(true);
        }
        else {
            isEcoLabel.setText("Produkten är ej ekologisk");
            ekoStackPane.setVisible(false);
        }
        numOfItems.setText(String.valueOf(IMat.getNumberOfAProductInCart(product)));
        updateFavoriteButton();
    }

    private void updateFavoriteButton() {
        if (dataHandler.isFavorite(product)) {
            favoriteImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/starSelected.png"))));
            favoriteButton.setText("Ta bort från favoritvaror");
        }
        else {
            favoriteImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/starUnselected.png"))));
            favoriteButton.setText("Gör till favoritvara");
        }
    }
}