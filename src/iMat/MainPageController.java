// Hör ihop med IMatMainPage.fxml
package iMat;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import se.chalmers.cse.dat216.project.*;

public class MainPageController implements Initializable {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ShoppingCart cart = IMat.getShoppingCart();

    private final ClassLoader classLoader = getClass().getClassLoader();
    private final AccountWindowController accountWindowController = new AccountWindowController();

    @FXML private AnchorPane accountWindowPane;
    @FXML private AnchorPane detailViewAnchorPane;
    @FXML private ImageView detailViewImage;
    @FXML private Label detailViewProductNameLabel;
    @FXML private Label detailViewPriceLabel;
    @FXML private Label detailViewNumOfItems;
    @FXML private Label detailViewIsEcoLabel;
    @FXML private FlowPane productItemsFlowpane;
    @FXML private ImageView closeImageView;
    @FXML private Button brodKnapp;
    @FXML private TextField searchBar;

    @FXML private Button btnTestKonto;

    // Körs när MainPage.fxml läses in
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accountWindowPane.getChildren().add(accountWindowController);
        populateProductDetailView(dataHandler.getProduct(75)); // Temporär, används för test
        fillFood();
    }

    @FXML
    public void openProductDetailView(Product product) {
        populateProductDetailView(product);
        detailViewAnchorPane.toFront();

    }

    @FXML
    public void closeProductDetailView() {
        detailViewAnchorPane.toBack();
    }

    @FXML
    public void openAccountWindow(){
        accountWindowPane.toFront();
    }

    @FXML
    public void closeAccountWindow() {
        accountWindowPane.toBack();
    }

    @FXML
    public void closeButtonMouseEntered(){
        closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/icon_close_hover.png"))));
    }

    @FXML
    public void closeButtonMousePressed(){
        closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/icon_close_pressed.png"))));
    }

    @FXML
    public void closeButtonMouseExited(){
        closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/icon_close.png"))));
    }

    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }

    /* Knappar below tänker att den fungerar som den ska, fortsätter på allt annat och återvänder till det här när mera
    är färdigt, hoppas det är okej.*/
    @FXML protected void searchForBread (ActionEvent event){
        productItemsFlowpane.getChildren().clear();
        ArrayList<Product> list = (ArrayList<Product>) dataHandler.getProducts(ProductCategory.valueOf("BREAD"));
        for(Product produkt: list){
            ProductItemController produkten = new ProductItemController(produkt);
            productItemsFlowpane.getChildren().add(produkten);
        }
    }

    @FXML protected void searchBar (ActionEvent event){
        productItemsFlowpane.getChildren().clear();
        String text = searchBar.getText();
        ArrayList<Product> list = (ArrayList<Product>) dataHandler.findProducts(text);
        for(Product produkt: list){
            ProductItemController produkten = new ProductItemController(produkt);
            productItemsFlowpane.getChildren().add(produkten);

        }
    }

    // Fyller upp detalj vyn med rätt produkt
    private void populateProductDetailView(Product product) {
        detailViewImage.setImage(dataHandler.getFXImage(product));
        detailViewProductNameLabel.setText(product.getName());
        detailViewPriceLabel.setText(String.format("Pris: %.2f %s", product.getPrice(), product.getUnit()));
        if (product.isEcological()) {
            detailViewIsEcoLabel.setText("Produkten är ekologisk");
        }
        else {
            detailViewIsEcoLabel.setText("Produkten är ej ekologisk");
        }
        detailViewNumOfItems.setText(String.format("%.1f", IMat.getNumberOfProductInCart(product)));
    }

    private void populateAccountWindow(Product product) { //TODO: Populera med rätt grejor tex. historiken.
    }

    // Tar en produkt som argument, retunerar antalet av denna product som finns i varukorgen
    private double getNumberOfProductInCart(Product product) {
        double numOfProductInCart = 0;
        List<ShoppingItem> listOfShoppingItems = cart.getItems();
        Product productInCart;
        for (ShoppingItem shoppingItem : listOfShoppingItems) {
            productInCart = shoppingItem.getProduct();
            if (productInCart.equals(product)) {
                numOfProductInCart += shoppingItem.getAmount();
            }
        }
        return numOfProductInCart;
    }

    //Fyller på flowpanen för att bygga all funktionalitet runt
    private void fillFood(){
        productItemsFlowpane.getChildren().clear();
        ArrayList<Product> produktlista = (ArrayList<Product>) dataHandler.getProducts();
        for(Product produkt: produktlista){
            ProductItemController produkten = new ProductItemController(produkt);
            productItemsFlowpane.getChildren().add(produkten);
        }
    }
}