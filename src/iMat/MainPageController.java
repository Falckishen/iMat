// Hör ihop med IMatMainPage.fxml
package iMat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import se.chalmers.cse.dat216.project.*;

public class MainPageController implements Initializable {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ShoppingCart cart = IMat.getShoppingCart();

    private final ClassLoader classLoader = getClass().getClassLoader();

    @FXML private AnchorPane detailViewAnchorPane;
    @FXML private ImageView detailViewImage;
    @FXML private Label detailViewProductNameLabel;
    @FXML private Label detailViewPriceLabel;
    @FXML private Label detailViewNumOfItems;
    @FXML private Label isEco;
    @FXML private FlowPane flowpane;
    @FXML private ImageView closeImageView;
    @FXML private Button brodKnapp;

    // Körs när MainPage.fxml läses in
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    // Fyller upp detalj vyn med rätt produkt
    private void populateProductDetailView(Product product) {
        detailViewImage.setImage(dataHandler.getFXImage(product));
        detailViewProductNameLabel.setText(product.getName());
        detailViewPriceLabel.setText(String.format("Pris: %.2f %s", product.getPrice(), product.getUnit()));
        if (product.isEcological()) {
            isEco.setText("Produkten är ekologisk");
        }
        else {
            isEco.setText("Produkten är ej ekologisk");
        }
        detailViewNumOfItems.setText(String.format("%.1f", getNumberOfProductInCart(product)));
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
        flowpane.getChildren().clear();
        ArrayList<Product> produktlista = (ArrayList<Product>) dataHandler.getProducts();
        for(Product produkt: produktlista){
            ProductItemController produkten = new ProductItemController(produkt);
            flowpane.getChildren().add(produkten);
        }
    }

    /* Knappar below*/
    @FXML protected void searchForBread (ActionEvent event){
        flowpane.getChildren().clear();
        ArrayList<Product> list = (ArrayList<Product>) dataHandler.getProducts(ProductCategory.valueOf("BREAD"));
        for(Product produkt: list){
            ProductItemController produkten = new ProductItemController(produkt);
            flowpane.getChildren().add(produkten);
        }

    }

}