// Hör ihop med IMatMainPage.fxml
package iMat;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class MainPageController implements Initializable {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();

    private final ShoppingCart cart = IMat.getShoppingCart();

    @FXML private AnchorPane detailViewAnchorPane;
    @FXML private ImageView detailViewImage;
    @FXML private Label detailViewProductNameLabel;
    @FXML private Label detailViewPriceLabel;
    @FXML private Label detailViewUnitLabel;
    @FXML private Label detailViewNumOfItems;

    // Körs när MainPage.fxml läses in
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateProductDetailView(dataHandler.getProduct(38)); // Temporär
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
    public void mouseTrap(Event event){
        event.consume();
    }

    private void populateProductDetailView(Product product) {
        detailViewImage.setImage(dataHandler.getFXImage(product));
        detailViewProductNameLabel.setText(product.getName());
        detailViewPriceLabel.setText(String.format("%.2f", product.getPrice()));
        detailViewUnitLabel.setText(product.getUnit());
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

    // Testfunktion för ShoppingCart, ignorera
    private void printShoppingCart() {
        List<ShoppingItem> listOfShoppingItems = cart.getItems();
        for (ShoppingItem shoppingItem : listOfShoppingItems) {
            System.out.println(shoppingItem.getProduct() + " " + shoppingItem.getAmount());
        }
    }
}