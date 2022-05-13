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
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;

public class DetailViewController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ShoppingCart cart = IMat.getShoppingCart();

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
    @FXML private Button shoppingListButton;
    @FXML private ImageView shoppingListImage;

    public DetailViewController(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
        populateProductDetailView();
    }

    @FXML
    public void closeProductDetailView() {
        this.toBack();
    }

    @FXML
    public void closeButtonMouseEntered() {
        this.closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close_hover.png"))));
    }

    @FXML
    public void closeButtonMousePressed() {
        this.closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close_pressed.png"))));
    }

    @FXML
    public void closeButtonMouseExited(){
        this.closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/cross_close.png"))));
    }

    @FXML
    public void plusButtonMouseEntered(){
        this.plusImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/plus_hover.png"))));
    }

    @FXML
    public void plusButtonMousePressed(){
        this.plusImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/plus.png"))));
    }

    @FXML
    public void plusButtonMouseExited(){
        this.plusImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/plus.png"))));
    }

    @FXML
    public void minusButtonMouseEntered(){
        this.minusImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/minus_hover.png"))));
    }

    @FXML
    public void minusButtonMousePressed(){
        this.minusImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/minus.png"))));
    }

    @FXML
    public void minusButtonMouseExited(){
        this.minusImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/minus.png"))));
    }

    @FXML
    public void favoriteButtonMouseEntered() {
        this.favoriteButton.setStyle("-fx-background-color: #f0f0f0;");
    }

    @FXML
    public void favoriteButtonMousePressed() {
        this.favoriteButton.setStyle("-fx-background-color: #d0d0d0;");
    }

    @FXML
    public void favoriteButtonMouseExitedOrReleased() {
        this.favoriteButton.setStyle("-fx-background-color: #e0e0e0;");
    }

    @FXML
    public void shoppingListButtonMouseEntered() {
        this.shoppingListButton.setStyle("-fx-background-color: #f0f0f0;");
    }

    @FXML
    public void shoppingListButtonMousePressed() {
        this.shoppingListButton.setStyle("-fx-background-color: #d0d0d0;");
    }

    @FXML
    public void shoppingListButtonMouseExitedOrReleased() {
        this.shoppingListButton.setStyle("-fx-background-color: #e0e0e0;");
    }

    @FXML
    public void mouseTrap(Event event) {
        event.consume();
    }

    @FXML
    public void addOneToCart() {
        cart.addProduct(this.product);
        this.numOfItems.setText(String.format("%.1f", IMat.getNumberOfAProductInCart(this.product)));
    }

    @FXML
    public void minusButtonClicked() {
        IMat.removeOneFromCart(this.product);
        this.numOfItems.setText(String.format("%.1f", IMat.getNumberOfAProductInCart(this.product)));
    }

    @FXML
    public void favoriteButtonClicked() {
        if (dataHandler.isFavorite(this.product)) {
            dataHandler.removeFavorite(this.product);
        }
        else {
            dataHandler.addFavorite(this.product);
        }
        updateFavoriteButton();
    }

    @FXML
    public void numOfItemsWriteIn() throws InputMismatchException {
        String strAmount = this.numOfItems.getText();
        IMat.writeInNumOfProductAmount(this.product, strAmount);
        this.numOfItems.setText(String.format("%.1f", IMat.getNumberOfAProductInCart(this.product)));
    }

    private void populateProductDetailView() {
        this.productImage.setImage(dataHandler.getFXImage(this.product));
        this.productNameLabel.setText(this.product.getName());
        this.priceLabel.setText(String.format("Pris: %.2f %s", this.product.getPrice(), this.product.getUnit()));
        if (this.product.isEcological()) {
            this.isEcoLabel.setText("Produkten är ekologisk");
        }
        else {
            this.isEcoLabel.setText("Produkten är ej ekologisk");
        }
        this.numOfItems.setText(String.format("%.1f", IMat.getNumberOfAProductInCart(this.product)));
        updateFavoriteButton();
    }

    private void updateFavoriteButton() {
        if (dataHandler.isFavorite(this.product)) {
            this.favoriteImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/starSelected.png"))));
            this.favoriteButton.setText("Ta bort från favoritvaror");
        }
        else {
            this.favoriteImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/starUnselected.png"))));
            this.favoriteButton.setText("Gör till favoritvara");
        }
    }
}
